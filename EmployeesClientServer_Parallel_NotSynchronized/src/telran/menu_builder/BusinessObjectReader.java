package telran.menu_builder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import telran.view.InputOutput;

/**
 * Checks and reads Business Object (BO) value
 * @author D.Zinchin
 */
public class BusinessObjectReader{
	
	/**
	 * Checks if BO value type is supported
	 */
	public void checkBoValue(Class<?> clazz, String valueName) {
		if (clazz == String.class ||
			clazz == Integer.class || clazz == Integer.TYPE ||
			clazz == Long.class || clazz == Long.TYPE ||
			clazz == Double.class || clazz == Double.TYPE ||
			clazz == Boolean.class || clazz == Boolean.TYPE ||
			clazz == LocalDate.class ||
			clazz.isEnum()) {
			return;
		}
		
		if (clazz.isArray()) {
			checkBoValue(clazz.getComponentType(), valueName + ".<element>");
			return;
		}
		// else
		checkBoCompoundValue(clazz, valueName);
	}

	private void checkBoCompoundValue(Class<?> clazz, String valueName){
		try {
			clazz.getDeclaredConstructor();			
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(valueName + ": data type " + clazz.getName() + " must have default constructor\n");
		}		
		StringBuilder errMsg = new StringBuilder();
		for (Field f : clazz.getDeclaredFields()) {
			if (!Modifier.isStatic(f.getModifiers())) {
				String fldName = valueName + "." + f.getName();
				Type t = f.getGenericType();
				if (t instanceof ParameterizedType) {
					errMsg.append(fldName + ": data type has unsupported generic type:" + t + '\n');
					continue;
				}
				try {
					checkBoValue(f.getType(), fldName);
				} catch (Exception e) {
					errMsg.append(e.getMessage() + '\n');
				}
			}
		}
		if (errMsg.length()>0) {
			throw new RuntimeException(errMsg.toString());
		}
	}

	/**
	 * Reads BO value of simple or compound type
	 */
	@SuppressWarnings("unchecked")
	public Object readBoValue(Class<?> clazz, String prompt, InputOutput io) {
		if (clazz == String.class) {
			return io.readString(prompt);
		}
		if (clazz == Integer.class || clazz == Integer.TYPE) {
			return io.readInt(prompt);
		}
		else if (clazz == Long.class || clazz == Long.TYPE) {
			return io.readLong(prompt);
		}
		else if (clazz == Double.class || clazz == Double.TYPE) {
			return io.readDouble(prompt);
		}
		else if (clazz == Boolean.class || clazz == Boolean.TYPE) {
			List<String> options = Arrays.asList("TRUE", "FALSE");
			String opt = io.readOption(prompt + options, options);
			return opt.equals("TRUE");
		}
		else if (clazz == LocalDate.class) {
			return io.readDate(prompt, "yyyy-MM-dd");			
		}
		else if (clazz.isEnum()) {
			List<String> enumNames = Stream.of(clazz.getEnumConstants())
                    .map(x->x.toString())
                    .collect(Collectors.toList());
			String opt = io.readOption(prompt + enumNames, enumNames);
			return Enum.valueOf(clazz.asSubclass(Enum.class), opt);
		}
		else if (clazz.isArray()) {
			return readArray(clazz, prompt, io);
		}
		// else
		return readBoCompoundValue(clazz, prompt, io);
	}
	
	private Object readArray(Class<?> clazz, String prompt, InputOutput io) {
		int size = io.readInt(prompt + " / [count]");
		Class<?> eltType = clazz.getComponentType();
		Object result = Array.newInstance(eltType, size);
		for (int i=0; i < size; i++) {
			Array.set(result, i, readBoValue(eltType, prompt + " ["+i+"]", io));
		}
		return result;
	}
	
	private Object readBoCompoundValue(Class<?> clazz, String prompt, InputOutput io) { 
		Object result;
		try{
			result = clazz.getDeclaredConstructor().newInstance(); 
			for (Field f : clazz.getDeclaredFields()) {
				if (! Modifier.isStatic(f.getModifiers())) {
					Object val = readBoValue(f.getType(), prompt + " / " + NameFormatter.nameToTitle(f.getName()), io);
					f.setAccessible(true);
					f.set(result, val);
				}
			}
		} catch (Exception e) {
			// type already checked unexpected exception here
			// e.printStackTrace();
			throw new RuntimeException("Unexpected error", e);
		}
		//System.out.println("===>" + clazz.getName() + ":" + result);
		return result;
	}
}
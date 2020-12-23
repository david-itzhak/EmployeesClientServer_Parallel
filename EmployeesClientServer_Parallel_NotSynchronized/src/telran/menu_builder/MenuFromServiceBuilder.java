package telran.menu_builder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

/**
 * Builds console menu from service. Supports automatic input of parameters.
 * Attention:
 *      - to enable usage of original methods parameters meaningful names, 
 *        the key -parameters must be provided to compiler
 * @author D.Zinchin
 */
public class MenuFromServiceBuilder {
	
	public static <T, U extends T> Menu of(String menuName, Class<T> serviceClass, T serviceObj) {
		StringBuilder errMsg = new StringBuilder();
		List<Item> items = new ArrayList<>();
		for (Method m: serviceClass.getDeclaredMethods()) {
			try {	
				MethodInvoker invoker = new MethodInvoker(m, serviceObj); // checks params supported, could throw
				items.add(
						Item.of( 
								NameFormatter.nameToTitle(m.getName()), 
								io -> print(io, invoker.invoke(io)))); 
			}catch (Exception e) {
				// e.printStackTrace();
				errMsg.append(e.getMessage());
			}			
		}
		Collections.sort(items, Comparator.comparing(Item::getName));
		items.add(Item.exit());
		if (errMsg.length()>0) {
			String err = new StringBuilder("\n===>Service: " + serviceClass.getName() + '\n')
					.append(errMsg).toString();
			throw new RuntimeException(err);
		}
		return new Menu(menuName, items.toArray(new Item[items.size()]));
	}	
	
	private static void print(InputOutput io, Object obj) {
		print(io, obj, 0);
	}
	
	private static void print(InputOutput io, Object obj, int level) {
		String offset =  (level>0) ? String.format("%" + level*4 + "s", "") : "";
		if (obj instanceof Iterable) {
			io.writeln(offset + "[");
			for (Object elt: (Iterable<?>) obj) {
				print(io, elt, level+1);
			}
			io.writeln(offset + "]");
		}
		else {
			io.write(offset);
			io.writeln(obj);
		}
	}
}

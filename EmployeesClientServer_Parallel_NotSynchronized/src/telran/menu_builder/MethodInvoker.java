package telran.menu_builder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import telran.view.InputOutput;

/**
 * This class provides interactive method invocation, accepting input parameters from dialog with user. 
 * @author D.Zinchin
 */
public class MethodInvoker extends BusinessObjectReader{
	private Method method;
	private Object targetObj;
	
	public MethodInvoker(Method method, Object obj){
		this.method = method;
		this.targetObj = obj;
		checkParamTypes();
	}
	
	private void checkParamTypes(){
		StringBuilder errMsg = new StringBuilder();
		for (Parameter prm: method.getParameters()) {
			Type t = prm.getParameterizedType();
			if (t instanceof ParameterizedType) {
				errMsg.append(prm.getName() + ": Unsupported request Parameter generic type:" + t + '\n');
				continue;
			}
			try {
				checkBoValue(prm.getType(), prm.getName());
			}catch(Exception e) {
				errMsg.append(e.getMessage()+ '\n');
			}
		}
		if (errMsg.length()>0) {
			throw new RuntimeException(new StringBuilder("--->Method: " + method.getName() + ":\n").append(errMsg).toString());
		}
	}
	

	public Object invoke(InputOutput io) {
		Object[] args = new Object[method.getParameters().length];
		int curArg=0;
		try {
			for (Parameter prm: method.getParameters()) {
				args[curArg++] = readBoValue(prm.getType(), NameFormatter.nameToTitle(prm.getName()), io);
			}
			method.setAccessible(true);
			return method.invoke(targetObj, args);
		} catch (InvocationTargetException e) {
			Throwable targetEx = e.getTargetException();
			throw new RuntimeException(targetEx.getClass().getName() + ":"+ targetEx.getMessage(), targetEx);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Access Error:" + e.getMessage(), e);
		} 
	}

}
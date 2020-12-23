package telran.menu_builder;

public class NameFormatter {
	/**
	 * Converts identifier name to human-readable title string.
	 * For example, 'userFirstName' is converted to 'User First Name' 
	 */
	public static String nameToTitle(String argName) {
		if (argName.isEmpty()) {
			return argName;
		}
		StringBuilder result = new StringBuilder().append(Character.toUpperCase(argName.charAt(0)));
		boolean lastIsUpper = true;
		for (int i = 1; i <argName.length(); i++) {
			char c = argName.charAt(i);
			if (!lastIsUpper && Character.isUpperCase(c)) {
				result.append(' ');				
			}
			result.append(c);
			lastIsUpper = Character.isUpperCase(c);
		}
		return result.toString();
	}	
}
package telran.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;

public interface InputOutput {

	String readString(String prompt);

	void write(Object obj);

	default void writeln(Object obj) {
		write(obj + "\n");
	}

	default <R> R readObject(String prompt, String errorPrompt, Function<String, R> mapper) {
		while (true) {
			String line = readString(prompt);
			try {
				return mapper.apply(line);
			} catch (RuntimeException e) {
				writeln(errorPrompt);
			}
		}
	}

	default int readInt(String prompt) {
		return readObject(prompt, "It's not integer", Integer::parseInt);
	}

	default int readInt(String prompt, int min, int max) {
		return readObject(prompt, String.format("It's not integer in range [%d, %d]", min, max), s -> {
			int val = Integer.parseInt(s);
			if (val < min || val > max) {
				throw new NumberFormatException();
			}
			return val;
		});
	}

	default long readLong(String prompt) {
		return readObject(prompt, "It's not (long) number", Long::parseLong);
	}

	default double readDouble(String prompt) {
		return readObject(prompt, "It's not (double) number", Double::parseDouble);
	}

	default LocalDate readDate(String prompt, String format) {
		return readObject(prompt + "[" + format + "]", "Wrong date format", s -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			return LocalDate.parse(s, formatter);
		});
	}

	default String readOption(String prompt, List<String> options) {
		return readObject(prompt, "Not in options", s -> {
			if (!options.contains(s)) {
				throw new IllegalArgumentException();
			}
			return s;
		});
	}
}

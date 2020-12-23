package telran.view;

import java.util.Arrays;
import java.util.List;

public class Menu implements Item {
	String name;
	List<Item> items;

	public Menu(String name, List<Item> items) {
		this.name = name;
		this.items = items;
	}

	public Menu(String name, Item... items) {
		this.name = name;
		this.items = Arrays.asList(items);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void perform(InputOutput io) {
		while (true) {
			displayMenu(io);
			int optionId = io.readInt(" Select ID", 1, items.size());
			Item item = items.get(optionId - 1);
			try {
				item.perform(io);
			} catch (Exception ex) {
				io.writeln(ex.getMessage());
			}
			if (item.isExit()) {
				break;
			}
		}
	}

	private void displayMenu(InputOutput io) {
		printDivider(io);
		io.writeln(" " + name);
		printDivider(io);
		int i = 1;
		for (Item item : items) {
			io.writeln(String.format(" %2d. %s", i++, item.getName()));
		}
		printDivider(io);
	}

	private void printDivider(InputOutput io) {
		io.writeln("=".repeat(50));
	}
}

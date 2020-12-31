package telran.employees.client;

import telran.employees.common.EmployeeService;
import telran.menu_builder.MenuFromServiceBuilder;
import telran.view.*;

import static telran.employees.common.EmployeesApiConstants.*;

import java.io.IOException;

public class EmployeesClientAppl {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		InputOutput io = new ConsoleInputOutput();
		try (EmployeesServiceProxy service = new EmployeesServiceProxy("localhost", PORT)) {
			// to see methods parameters names in menu builder, open Project Properties ->
			// Java Compiler
			// and switch on option "Store information about method parameters"
			 Thread assassin = new Thread(() -> {
				while (true) {
					try {
						service.size();
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// noop
					} catch (RuntimeException e) {
						System.out.println("Server closed conection");
						System.exit(0);
					}
				}
			});
			assassin.setDaemon(true);
			assassin.start();
			Menu employeesMenu = MenuFromServiceBuilder.of("Employees", EmployeeService.class, service);
			employeesMenu.perform(io);
		}
	}
}
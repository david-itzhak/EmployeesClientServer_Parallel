package telran.employees.client;

import telran.employees.common.EmployeeService;
import telran.menu_builder.MenuFromServiceBuilder;
import telran.view.*;

import static telran.employees.common.EmployeesApiConstants.*;

import java.io.IOException;

public class EmployeesClientAppl {
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		InputOutput io = new ConsoleInputOutput();
		try (EmployeesServiceProxy service = new EmployeesServiceProxy ("localhost", PORT)) {
			// to see methods parameters names in menu builder, open Project Properties ->
			// Java Compiler
			// and switch on option "Store information about method parameters"
			Menu employeesMenu = MenuFromServiceBuilder.of("Employees", EmployeeService.class, service);
			employeesMenu.perform(io);
		}
	}
}
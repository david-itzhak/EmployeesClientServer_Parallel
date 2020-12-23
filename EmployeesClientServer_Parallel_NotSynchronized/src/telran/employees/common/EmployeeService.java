package telran.employees.common;

import java.util.*;

import telran.employees.common.dto.*;

public interface EmployeeService {
	ReturnCodes addEmployee(Employee employee);
	ReturnCodes removeEmployee(long id);
	Employee updateEmployee(Employee updatedEmployee);
	ArrayList<Employee> getEmployeesByAges(int ageFrom, int ageTo);
	ArrayList<Employee> getEmployeesByDepartment(String dedartment);
	ArrayList<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);
	Employee getEmployee(long id);
	int size();
	ArrayList<Employee> getAllEmployees();
	MinMaxSalaryEmployees[] getEmployeesBySalariesInterval(int intervals);
	DepartmentSalary[] getDepartmentAvgSalaryDistribution();
}

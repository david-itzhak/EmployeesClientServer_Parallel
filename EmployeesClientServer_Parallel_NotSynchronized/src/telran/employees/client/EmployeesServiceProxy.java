package telran.employees.client;

import static telran.employees.common.EmployeesApiConstants.*;

import java.io.Serializable;
import java.util.ArrayList;

import telran.employees.common.EmployeeService;
import telran.employees.common.dto.*;
import telran.net.client.TcpClient;

public class EmployeesServiceProxy extends TcpClient implements EmployeeService {

	protected EmployeesServiceProxy(String hostname, int port) {
		super(hostname, port);
	}

	@Override
	public ReturnCodes addEmployee(Employee empl) {
		return sendRequest(ADD_EMPLOYEE, empl);
	}

	@Override
	public ReturnCodes removeEmployee(long id) { // Почему возвращаемое значение ReturnCodes, а не сам удаленный Employee? Может имеет смысл переделать?
		return sendRequest(REMOVE_EMPLOYEE, id);
	}

	@Override
	public Employee updateEmployee(Employee updatedEmployee) {
		return sendRequest(UPDATE_EMPLOYEE, updatedEmployee);
	}

	@Override
	public ArrayList<Employee> getEmployeesByAges(int ageFrom, int ageTo) {
		return sendRequest(GET_EMPLOYEE_BY_AGES, new Serializable[] { ageFrom, ageTo });

	}

	@Override
	public ArrayList<Employee> getEmployeesByDepartment(String department) {
		return sendRequest(GET_EMPLOYEE_BY_DEPARTMENT, department);
	}

	@Override
	public ArrayList<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		return sendRequest(GET_EMPLOYEE_BY_SALARY, new Serializable[] { salaryFrom, salaryTo });
	}

	@Override
	public Employee getEmployee(long id) {
		return sendRequest(GET_EMPLOYEE, id);
	}

	@Override
	public int size() {
		return sendRequest(SIZE, "");
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		return sendRequest(GET_ALL_EMPLOYEE, "");
	}

	@Override
	public MinMaxSalaryEmployees[] getEmployeesBySalariesInterval(int intervals) {
		return sendRequest(GET_EMPLOYEE_BY_SALARY_INTERVAL, intervals);
	}

	@Override
	public DepartmentSalary[] getDepartmentAvgSalaryDistribution() {
		return sendRequest(GET_DEPARTMENT_AVG_SALARY_DISTRIBUTION, "");
	}
}

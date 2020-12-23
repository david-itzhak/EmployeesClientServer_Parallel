package telran.employees.common;

// Почему константы помещены в интерфейс, а не класс?

public interface EmployeesApiConstants {
	String ADD_EMPLOYEE = "add_employee";
	String REMOVE_EMPLOYEE = "remove_employee";
	String UPDATE_EMPLOYEE = "update_employee";
	String GET_EMPLOYEE_BY_AGES = "get_employees_by_ages";
	String GET_EMPLOYEE_BY_DEPARTMENT = "get_employees_by_department";
	String GET_EMPLOYEE_BY_SALARY = "get_Employees_by_salary";
	String GET_EMPLOYEE = "get_employee";
	String SIZE = "size";
	String GET_ALL_EMPLOYEE = "get_all_employee";
	String GET_EMPLOYEE_BY_SALARY_INTERVAL = "get_employees_by_salary_interval";
	String GET_DEPARTMENT_AVG_SALARY_DISTRIBUTION = "get_employees_avg_salary_distribution";
	int PORT = 5000;
}

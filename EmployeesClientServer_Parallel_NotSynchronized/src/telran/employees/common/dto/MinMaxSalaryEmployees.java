package telran.employees.common.dto;

import java.io.Serializable;
import java.util.*;

public class MinMaxSalaryEmployees implements Serializable {
	private static final long serialVersionUID = 1L;
	int minSalary;
	int maxSalary;
	List<Employee> employees;

	public MinMaxSalaryEmployees(int minSalary, int maxSalary, List<Employee> employees) {
		super();
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.employees = employees;
	}

	public int getMinSalary() {
		return minSalary;
	}

	public int getMaxSalary() {
		return maxSalary;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employees == null) ? 0 : employees.hashCode());
		long temp;
		temp = Double.doubleToLongBits(maxSalary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(minSalary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MinMaxSalaryEmployees other = (MinMaxSalaryEmployees) obj;
		if (employees == null) {
			if (other.employees != null)
				return false;
		} else if (!employees.equals(other.employees))
			return false;
		if (Double.doubleToLongBits(maxSalary) != Double.doubleToLongBits(other.maxSalary))
			return false;
		if (Double.doubleToLongBits(minSalary) != Double.doubleToLongBits(other.minSalary))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MinMaxSalaryEmployees [minSalary=" + minSalary + ", maxSalary=" + maxSalary + "]";
	}
}

package telran.employees.common.dto;

import java.io.Serializable;

public class DepartmentSalary implements Serializable {
	private static final long serialVersionUID = 1L;
	String department;
	double avgSalary;

	public DepartmentSalary(String department, double avgSalary) {
		super();
		this.department = department;
		this.avgSalary = avgSalary;
	}

	public String getDepartment() {
		return department;
	}

	public double getAvgSalary() {
		return avgSalary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(avgSalary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((department == null) ? 0 : department.hashCode());
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
		DepartmentSalary other = (DepartmentSalary) obj;
		if (Double.doubleToLongBits(avgSalary) != Double.doubleToLongBits(other.avgSalary))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DepartmentSalary [department=" + department + ", avgSalary=" + avgSalary + "]";
	}
}

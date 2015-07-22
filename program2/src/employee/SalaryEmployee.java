package employee;

import java.util.*;

public class SalaryEmployee extends Employee {
	private float salary;

	public void setIncome(float salary) {
		this.salary = salary;
	}

	public float getIncome() {
		return this.salary;
	}

	public SalaryEmployee(String name, int age, float salary) {
		super(name, age);
		setIncome(salary);
	}
}

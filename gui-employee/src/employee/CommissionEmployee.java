package employee;

import java.util.*;

// Straight salary is sale * 20%
public class CommissionEmployee extends Employee {

	private float sales;

	public void setSales(float sales) {
		this.sales = sales;
	}

	public float getSales() {
		return this.sales;
	}

	public CommissionEmployee(String name, int age, float sales) {
		super(name, age);
		setSales(sales);
	}

	// weekly sales
	public float getIncome() {
		return getSales() * 0.20f * 52;
	}
}

package employee;

import java.util.*;

public class HourlyEmployee extends Employee {
	private float hourlyWage;
	private float hoursWorked;

	public HourlyEmployee(String name, int age, float hourlyWage, float hoursWorked) {
		super(name, age);
		setHourlyWage(hourlyWage);
		setHoursWorked(hoursWorked);
	}
	
	public void setHourlyWage(float hourlyWage) {
		this.hourlyWage = hourlyWage;
	}

	public float getHourlyWage() {
		return this.hourlyWage;
	}

	public void setHoursWorked(float hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

	public float getHoursWorked() {
		return this.hoursWorked;
	}

	public float getIncome() {
		return getHourlyWage() * getHoursWorked() * 52;
	}
}

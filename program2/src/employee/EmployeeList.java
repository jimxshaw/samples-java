package employee;

import java.util.*;

public class EmployeeList {

	ArrayList<Employee> employees = new ArrayList<Employee>();

	public int length() {
		return employees.size();
	}

	public Employee get(int index) {
		return employees.get(index);
	}

	public void getData(String input) {
		employees.clear();
		Scanner scan = new Scanner(input);
		for (; scan.hasNext();) {
			String name = scan.next();
			int age = scan.nextInt();
			float income = scan.nextFloat();
			if (scan.hasNextFloat()) {
				float hoursWorked = scan.nextFloat();
				employees.add(new HourlyEmployee(name, age, income, hoursWorked));
			} else {
				employees.add(new SalaryEmployee(name, age, income));
			}
		}
	}

	public void appendData(String input) {
		Scanner scan = new Scanner(input);
		for (; scan.hasNext();) {
			String name = scan.next();
			int age = scan.nextInt();
			float income = scan.nextFloat();
			if (scan.hasNextFloat()) {
				float hoursWorked = scan.nextFloat();
				employees.add(new HourlyEmployee(name, age, income, hoursWorked));
			} else {
				employees.add(new SalaryEmployee(name, age, income));
			}
		}
	}

	public void sortNames() {
		Collections.sort(employees);
	}

	public void sortAge() {
		Collections.sort(employees, new Comparator<Employee>() {
			public int compare(Employee e1, Employee e2) {
				return Integer.compare(e1.getAge(), e2.getAge());
			}
		});
	}

	public void sortIncome() {
		Collections.sort(employees, new Comparator<Employee>() {
			public int compare(Employee e1, Employee e2) {
				return Float.compare(e2.getIncome(), e1.getIncome());
			}
		});
	}

	public float getHighestIncome() {
		float highest = get(0).getIncome();
		for (int i = 0; i < length(); i++) {
			if (get(i).getIncome() > highest) {
				highest = get(i).getIncome();
			}
		}
		return highest;
	}

	public float getLowestIncome() {
		float lowest = get(0).getIncome();
		for (int i = 0; i < length(); i++) {
			if (get(i).getIncome() < lowest) {
				lowest = get(i).getIncome();
			}
		}
		return lowest;
	}

	public float getAverageIncome() {
		float total = 0;
		for (int i = 0; i < length(); i++) {
			total += get(i).getIncome();
		}
		return total / length();
	}
}

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
		Scanner scan = new Scanner(input);
		for (; scan.hasNext();) {
			String name = scan.next();
			int age = scan.nextInt();
			float sales = scan.nextFloat();
			employees.add(new CommissionEmployee(name, age, sales));
		}
	}

	public void sortNames() {
		for (int i = 0; i < length() - 1; i++) {
			for (int j = 0; j < length() - 1; j++) {
				if (get(j + 1).getName().compareTo(get(j).getName()) < 0) {
					Employee temp = employees.get(j);
					employees.set(j, employees.get(j + 1));
					employees.set(j + 1, temp);
				}
			}
		}
	}
}

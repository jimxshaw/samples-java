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
            employees.add(new Employee(name, age));
        }
    }
}


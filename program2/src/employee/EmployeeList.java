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
            employees.add(new SalaryEmployee(name, age, income));
        }
    }

    public void appendData(String input) {
        Scanner scan = new Scanner(input);
        for (; scan.hasNext();) {
            String name = scan.next();
            int age = scan.nextInt();
            float income = scan.nextFloat();
            employees.add(new SalaryEmployee(name, age, income));
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

    public void sortAge() {
        for (int i = 0; i < length() - 1; i++) {
            for (int j = 0; j < length() - 1; j++) {
                if (get(j + 1).getAge() < get(j).getAge()) {
                    Employee temp = employees.get(j);
                    employees.set(j, employees.get(j + 1));
                    employees.set(j + 1, temp);
                }
            }
        }
    }

    public void sortIncome() {
        for (int i = 0; i < length() - 1; i++) {
            for (int j = 0; j < length() - 1; j++) {
                if (get(j + 1).getIncome() > get(j).getIncome()) {
                    Employee temp = employees.get(j);
                    employees.set(j, employees.get(j + 1));
                    employees.set(j + 1, temp);
                }
            }
        }
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




package employee;

import java.util.*;

public class SalaryEmployee extends Employee {
    private float salary;
    
    public void setSalary(float salary) {
        this.salary = salary;
    }
    
    public float getSalary() {
        return this.salary;
    }
    
    public SalaryEmployee(String name, int age, float salary) {
        super(name, age);
        setSalary(salary);
    }
}



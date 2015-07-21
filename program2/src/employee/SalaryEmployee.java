package employee;

import java.util.*;

public class SalaryEmployee extends Employee {
    private float salary;
    
    public void setIncome(float income) {
        salary = income;
    }
    
    public float getIncome() {
        return this.salary;
    }
    
    public SalaryEmployee(String name, int age, float income) {
        super(name, age);
        setIncome(income);
    }
}




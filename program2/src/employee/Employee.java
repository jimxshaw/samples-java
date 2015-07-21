package employee;

// Abstract to a class means it can only be used as a base class.
// On abstracted, objects of this class cannot be created.
public abstract class Employee implements Comparable<Employee> {

    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public Employee(String name, int age) {
        setName(name);
        setAge(age);
    }

    public Employee() {
        this("", 0);
    }

    // Abstract a method means the derived classes will provide the implementation.
    public abstract float getIncome();
    
    // A base class can only have 1 compareTo method.
    public int compareTo(Employee e) {
    	return getName().compareTo(e.getName());
    }
    
    public String toString() {
        return String.format("Name:%4s Age:%4d", getName(), getAge());
    }
}





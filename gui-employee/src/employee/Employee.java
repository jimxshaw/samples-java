package employee;

public class Employee {

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

	public float getIncome() {
		return 0;
	}

	public String toString() {
		return String.format("Name:%4s Age:%4d", getName(), getAge());
	}
}

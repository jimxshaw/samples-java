import javax.swing.*;
import java.util.*;
import util.IO;

class Student {

	String name;
	int grade;

	public Student(String name, int grade) {
		this.name = name;
		this.grade = grade;
	}
}

public class StudentList {

	Student[] students = new Student[0];

	public int length() {
		return students.length;
	}

	public Student get(int n) {
		return students[n];
	}

	public void getData(String input) {
		StringTokenizer st = new StringTokenizer(input);
		students = new Student[st.countTokens() / 2];
		for (int i = 0; st.hasMoreTokens(); i++) {
			String name = st.nextToken();
			int grade = Integer.parseInt(st.nextToken());
			students[i] = new Student(name, grade);
		}
	}

	public void appendData(String input) {
		StringTokenizer st = new StringTokenizer(input);
		Student[] students2 = students;
		students = new Student[students2.length + (st.countTokens() / 2)];
		for (int i = 0; i < students2.length; i++) {
			students[i] = students2[i];
		}
		for (int i = 0; st.hasMoreTokens(); i++) {
			String name = st.nextToken();
			int grade = Integer.parseInt(st.nextToken());
			students[students2.length + i] = new Student(name, grade);
		}
	}

	public void sortNames() {
		for (int i = 0; i < length() - 1; i++) {
			for (int j = 0; j < length() - 1; j++) {
				if (get(j + 1).name.compareTo(get(j).name) < 0) {
					Student tmp = students[j + 1];
					students[j + 1] = students[j];
					students[j] = tmp;
				}
			}
		}
	}

	public void sortGrades() {
		for (int i = 0; i < length() - 1; i++) {
			for (int j = 0; j < length() - 1; j++) {
				if (get(j + 1).grade > get(j).grade) {
					Student tmp = students[j + 1];
					students[j + 1] = students[j];
					students[j] = tmp;
				}
			}
		}
	}

	public int getHighest() {
		int highest = get(0).grade;
		for (int i = 0; i < length(); i++) {
			if (get(i).grade > highest) {
				highest = get(i).grade;
			}
		}
		return highest;
	}

	public int getLowest() {
		int lowest = get(0).grade;
		for (int i = 0; i < length(); i++) {
			if (get(i).grade < lowest) {
				lowest = get(i).grade;
			}
		}
		return lowest;
	}

	public float getAverage() {
		float total = 0;
		for (int i = 0; i < length(); i++) {
			total += get(i).grade;
		}
		return total / length();
	}
}

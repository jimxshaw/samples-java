import javax.swing.*;
import java.util.Scanner;
import java.awt.Font;

public class ClassIO {
	public static Scanner getInput(String prompt) {
		return new Scanner(JOptionPane.showInputDialog(prompt));
	}
	public static Scanner getConsoleInput(String prompt) {
		return new Scanner(System.in);
	}
	public static void showMessage(String s) {
		System.out.println(s);
		JOptionPane.showMessageDialog(null, s);
	}
	public static void showMessage(String s1, String s2) {
		System.out.println(s1);
		JOptionPane.showMessageDialog(null, s1, s2, JOptionPane.PLAIN_MESSAGE);
	}
	public static void main(String[] args) {
		

	}

}

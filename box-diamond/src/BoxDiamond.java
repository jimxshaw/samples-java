import javax.swing.*;
import java.util.Scanner;
import java.awt.Font;

public class BoxDiamond {
	public static Scanner getInput(String prompt) {
		return new Scanner(JOptionPane.showInputDialog(prompt));
	}
	public static Scanner getConsoleInput(String prompt) {
		System.out.println(prompt);
		return new Scanner(System.in);
	}
	public static void showMessage(String s) {
		JTextArea jta = new JTextArea(s);
		jta.setFont(new Font("Lucida Console", Font.PLAIN, 35));
		System.out.println(s);
		JOptionPane.showMessageDialog(null, jta);
	}
	public static void showMessage(String s1, String s2) {
		JTextArea jta = new JTextArea(s1);
		jta.setFont(new Font("Lucida Console", Font.PLAIN, 35));
		System.out.println(s1);
		JOptionPane.showMessageDialog(null, jta, s2, JOptionPane.PLAIN_MESSAGE);
	}
	public static String drawBox(int n) {
		String result = "";
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (x == 0 || x == n - 1 || y == 0 || y == n - 1) result += " *";
				else result += "  ";
			}
			result += "\n";
		}
		return result;
	}
	public static String drawVLine(int n) {
		String result = "";
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (y == 0) result += "* ";
			}
			result += "\n";
		}
		return result;
	}
	public static String drawHLine(char ch, int n) {
		String result = "";
		int x = 0;
		while (x < n) {
			result += " " + ch;
			x++;
		}
		return result;
	}
	public static void main(String[] args) {
		showMessage(drawBox(9));
		showMessage(drawVLine(9));
		
		System.exit(0);
	}

}

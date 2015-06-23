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
		jta.setFont(new Font("Lucida Console", Font.PLAIN, 16));
		System.out.println(s);
		JOptionPane.showMessageDialog(null, jta);
	}
	public static void showMessage(String message, String title) {
		JTextArea jta = new JTextArea(message);
		jta.setFont(new Font("Lucida Console", Font.PLAIN, 35));
		System.out.println(message);
		JOptionPane.showMessageDialog(null, jta, title, JOptionPane.PLAIN_MESSAGE);
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
			result += "*\n";
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
	public static String drawDiamond(int n) {
		String result = "";
		int x = 1;
		int y = n - 2;
		int topSpaces = n / 2;
		int bottomSpaces = 2 / n + 1;
		while (x <= n) {
			result += drawHLine(' ', topSpaces) + drawHLine('*', x) + "\n";
			x += 2;
			topSpaces--;
		}
		while (y >= 0) {
			result += drawHLine(' ', bottomSpaces) + drawHLine('*', y) + "\n";
			y -= 2;
			bottomSpaces++;
		}
		return result;
	}
	public static int requestNumber() {
		String str = "Enter an odd positive integer less than 20: ";
		int x;
		do {
			x = Integer.parseInt(JOptionPane.showInputDialog(str));
			str = "Invalid number. Enter an odd positive integer less than 20: ";
		}
		while (x < 1 || x > 19 || x % 2 == 0);
		return x;
	}
	public static void main(String[] args) {
		int x = requestNumber();
		
		switch (x) {
		case 3:
			showMessage(drawBox(x), "Box");
			break;
		case 5: 
			showMessage(drawDiamond(x), "Diamond");
			break;
		case 9:
			showMessage(drawBox(x), "Box");
			break;
		case 11: 
			showMessage(drawDiamond(x), "Diamond");
			break;
		case 15:
			showMessage(drawDiamond(x), "Diamond");
			break;
		case 17:
			showMessage(drawBox(x), "Box");
			break;
		default:
			showMessage(drawVLine(x), "Vertical Line");
		}
		
		System.exit(0);
	}

}

package util;

import java.awt.Font;
import java.util.Scanner;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class IO {
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
}

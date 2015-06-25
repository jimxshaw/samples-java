import javax.swing.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Font;

public class ArraySamples {
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
	public static int[] getData(String input) {
		StringTokenizer st = new StringTokenizer(input);
		int x[] = new int[st.countTokens()];
		for (int i = 0; st.hasMoreTokens(); i++)
			x[i] = Integer.parseInt(st.nextToken());
		return x;
	}
	public static float getAverage(int x[]) {
		float total = 0;
		for (int i = 0; i < x.length; i++)
			total += x[i];
		return total / x.length;
	}
	public static String putArray(int x[]) {
		String result = "";
		for (int i = 0; i < x.length; i++)
			result += x[i] + " ";
		return result;
	}
	public static int[] sortBySelection(int x[]) {
		for (int i = 0; i < x.length; i++) {
			int largest = i;
			for (int j = largest + 1; j < x.length; j++) {
				if (x[j] > x[largest])
					largest = j;
			}
			int temp = x[i];
			x[i] = x[largest];
			x[largest] = temp;
		}
		return x;
	}
	public static int[] sortByBubble(int x[]) {
		for (int i = 0; i < x.length - 1; i++)
			for (int j = 0; j < x.length - 1; j++)
				if (x[j + 1] > x[j]) {
					int temp = x[j];
					x[j] = x[j + 1];
					x[j + 1] = temp;
				}
		return x;
	}
	public static void main(String[] args) {
		String input = JOptionPane.showInputDialog("Enter 1 or more grades");
		
		int x[] = getData(input);
		
		showMessage("Array: " + putArray(x) + 
				"\nSorted: " + putArray(sortByBubble(x)) + 
				String.format("\nAverage:%4.2f ", getAverage(x)));
		
		System.exit(0);
	}
}

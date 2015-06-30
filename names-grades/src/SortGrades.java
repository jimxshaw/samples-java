import javax.swing.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Font;
import util.IO;

public class SortGrades {

	public static int[] getData(String input) {
		StringTokenizer st = new StringTokenizer(input);
		int x[] = new int[st.countTokens()];
		for (int i = 0; st.hasMoreTokens(); i++) {
			x[i] = Integer.parseInt(st.nextToken());
		}
		return x;
	}

	public static int[] getIntData(String input) {
		String[] data = input.trim().split("\\s+");
		int x[] = new int[data.length / 2];
		for (int i = 1; i < data.length; i += 2) {
			if (data[i].matches("\\d+")) {
				x[i / 2] = Integer.parseInt(data[i]);
			} else {
				System.err.println("input error");
			}
		}
		return x;
	}
	
	public static String[] getStrData(String input) {
		String data[] = input.trim().split("\\s+");
		String x[] = new String[data.length / 2];
		for (int i = 0; i < data.length; i++) {
			if (data[i].matches("\\.+")) {
				x[i] = data[i];
			}
			else {
				System.err.println("input error");
			}
		}
		return x;
	}
	
	public static String[] getNames(String input) {
		StringTokenizer st = new StringTokenizer(input);
		String x[] = new String[st.countTokens()];
		for (int i = 0; st.hasMoreTokens(); i++) {
			x[i] = st.nextToken();
		}
		return x;
	}

	public static float getAverage(int x[]) {
		float total = 0;
		for (int i = 0; i < x.length; i++) {
			total += x[i];
		}
		return total / x.length;
	}

	public static int getHighest(int x[]) {
		int highest = x[0];
		for (int i = 0; i < x.length; i++) {
			if (x[i] > highest) {
				highest = x[i];
			}
		}
		return highest;
	}

	public static int getLowest(int x[]) {
		int lowest = x[0];
		for (int i = 0; i < x.length; i++) {
			if (x[i] < lowest) {
				lowest = x[i];
			}
		}
		return lowest;
	}

	public static String putIntArray(int x[]) {
		String result = "";
		for (int i = 0; i < x.length; i++) {
			result += x[i] + "\n";
		}
		return result;
	}

	// get rid of the \n if needed
	public static String putStrArray(String x[]) {
		String result = "";
		for (int i = 0; i < x.length; i++) {
			result += x[i] + "\n";
		}
		return result;
	}

	public static int[] sortIntBySelection(int x[]) {
		for (int i = 0; i < x.length; i++) {
			int largest = i;
			for (int j = largest + 1; j < x.length; j++) {
				if (x[j] > x[largest]) {
					largest = j;
				}
			}
			int temp = x[i];
			x[i] = x[largest];
			x[largest] = temp;
		}
		return x;
	}

	public static int[] sortIntByBubble(int x[]) {
		for (int i = 0; i < x.length - 1; i++) {
			for (int j = 0; j < x.length - 1; j++) {
				if (x[j + 1] > x[j]) {
					int temp = x[j];
					x[j] = x[j + 1];
					x[j + 1] = temp;
				}
			}
		}
		return x;
	}

	public static String[] sortStrByBubble(String x[]) {
		for (int i = 0; i < x.length - 1; i++) {
			for (int j = 0; j < x.length - 1; j++) {
				if (x[j + 1].compareTo(x[j]) < 0) {
					String temp = x[j];
					x[j] = x[j + 1];
					x[j + 1] = temp;
				}
			}
		}
		return x;
	}

	public static void main(String[] args) {
		/*
		 * \\d digit 
		 * \\s whitespace 
		 * . any character
		 * 
		 * Quantifiers 
		 * + 1 or more 
		 * * 0 or more 
		 * ? optional (0 or 1)
		 */

		// IO.showMessage("123-12345".matches("\\d+\\-\\d+") + "");
		// String [] data = " 555-123-4567 ".trim().split("\\-");
		// IO.showMessage(putStrArray(data));
		String input = JOptionPane
				.showInputDialog("Enter 1 or more names and grades");

		int x[] = getIntData(input);
		String y[] = getStrData(input);

		IO.showMessage(putStrArray(y) + putIntArray(x));

		// IO.showMessage("Array: " + putArray(x)
		// + "\nSorted: " + putArray(sortStrByBubble(x)));
		System.exit(0);
	}
}

import javax.swing.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Font;
import util.IO;

public class ArraySamples {
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
	public static int getHighest(int x[]) {
		int highest = x[0];
		for (int i = 0; i < x.length; i++) {
			if (x[i] > highest)
				highest = x[i];
		}
		return highest;
	}
	public static int getLowest(int x[]) {
		int lowest = x[0];
		for (int i = 0; i < x.length; i++) {
			if (x[i] < lowest)
				lowest = x[i];
		}
		return lowest;
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
		
		IO.showMessage("Array: " + putArray(x) + 
				"\nSorted: " + putArray(sortByBubble(x)) + 
				String.format("\nAverage:%6.2f \nHighest:%4d \nLowest:%4d", 
						getAverage(x), getHighest(x), getLowest(x)));
		
		System.exit(0);
	}
}

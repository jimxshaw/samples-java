import javax.swing.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Font;

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
	public static String putArray(int x[]) {
		String result = "";
		for (int i = 0; i < x.length; i++)
			result += x[i] + " ";
		return result;
	}
	public static void sortBySelection(int x[]) {
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
	}
	public static void main(String[] args) {
		
		System.exit(0);
	}
}

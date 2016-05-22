import javax.swing.JOptionPane;
import java.util.Scanner;

public class Output {

	public static float getFloat() {
		// Method to request input through the console
		System.out.printf("Enter a real number: ");
		Scanner input = new Scanner(System.in);
		return input.nextFloat();
	}
	public static void main(String[] args) {
		// GUI input to ask for real numbers
		String str1 = JOptionPane.showInputDialog("Enter 1st real number: ");
		float a = Float.parseFloat(str1);
		
		String str2 = JOptionPane.showInputDialog("Enter 2nd real number: ");
		float b = Float.parseFloat(str2);
		
		String str3 = JOptionPane.showInputDialog("Enter 3rd real number: ");
		float c = Float.parseFloat(str3);
		
		// If statements to test the values of the input
		if (a > 0 && b > 0 && c > 0) {
			System.out.printf("Sum:%10.2f", (a + b + c));
			JOptionPane.showMessageDialog(null, "Sum: " + (a + b + c));
		}
		else if (a > 0 && b > 0 && c < 0) {
			System.out.printf("Product:%10.2f", (a * b));
			JOptionPane.showMessageDialog(null, "Product: " + (a * b));
		}
		else if (a > 0 && b < 0 && c > 0) {
			System.out.printf("Product:%10.2f", (a * c));
			JOptionPane.showMessageDialog(null, "Product: " + (a * c));
		}
		else if (a < 0 && b > 0 && c > 0) {
			System.out.printf("Product:%10.2f", (b * c));
			JOptionPane.showMessageDialog(null, "Product: " + (b * c));
		}
		else {
			System.out.println("Invalid numbers");
			JOptionPane.showMessageDialog(null, "Invalid numbers");
		}
		
		System.out.println();
		
		// Call the getFloat() method twice to get two real numbers
		float x = getFloat();
		float y = getFloat();
		
		// If statements to test the values returned from the getFloat() methods
		if (x < 0 && y < 0) {
			System.out.printf("Quotient:%10.2f", (x / y));
			JOptionPane.showMessageDialog(null, "Quotient: " + (x / y));
		}
		else {
			System.out.println("Invalid numbers");
			JOptionPane.showMessageDialog(null, "Invalid numbers");
		}
		
		System.exit(0);
	}

}
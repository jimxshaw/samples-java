import javax.swing.JOptionPane;
import java.util.Scanner;

public class Input {

	public static void main(String[] args) {
		// Requesting inputs for 2 integers
		// Converting the string inputs to integer type
		String str1 = JOptionPane.showInputDialog("Enter the first integer: ");
		int x = Integer.parseInt(str1);
		
		String str2 = JOptionPane.showInputDialog("Enter the second integer: ");
		int y = Integer.parseInt(str2);
		
		// Display the output
		JOptionPane.showMessageDialog(null, "Sum: " + (x + y) + "\n" + "Difference: " + (x - y) + "\n" + "Product: " + (x * y) + "\n" + "Quotient: " + (x / y) + "\n" + "Modulus: " + (x % y));
		System.out.println("Sum: " + (x + y) + "\n" + "Difference: " + (x - y) + "\n" + "Product: " + (x * y) + "\n" + "Quotient: " + (x / y) + "\n" + "Modulus: " + (x % y) + "\n");
		
		
		System.out.println("Enter two real numbers: ");
		Scanner input = new Scanner (System.in);
		float j = input.nextFloat();
		float k = input.nextFloat();
		
		// %s means string
		// %d means integer
		// width of 7 
		// 2 digits after the decimal
		// f means fixed
		System.out.printf("Sum:%7.2f Difference:%7.2f Product:%7.2f Quotient:%7.2f", (j + k), (j - k), (j * k), (j / k));
		JOptionPane.showMessageDialog(null, "Sum: " + (j + k) + "\n" + "Difference: " + (j - k) + "\n" + "Product: " + (j * k) + "\n" + "Quotient: " + (j / k));
		
		System.exit(0);
	}

}
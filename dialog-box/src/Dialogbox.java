import javax.swing.JOptionPane;

public class Dialogbox {

	public static void main(String[] args) {
		// Print to screen
		System.out.println("James, COSC 1437, I'm taking this course in order to get transfer credit.");
		
		// Print to dialog box
		JOptionPane.showMessageDialog(null, "James, COSC 1437, I'm taking this course in order to get transfer credit.");
		
		System.exit(0);
	}

}

import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import java.awt.*;
import util.IO;

public class GuiSample extends JFrame {
	
	public GuiSample() {
		// App's title
		super("Super GUI App");
		
		// Menus need 3 things
		// 1) MenuBar, 2) Menu(s), 3) MenuItem(s)
		
		// Menu bar
		JMenuBar jmb = new JMenuBar();
		setJMenuBar(jmb);
		
		// File menu and file menu items
		JMenu jm = jmb.add(new JMenu("File"));
		jm.setMnemonic('F'); // Keyboard shortcut
		
		// Keyboard shortcuts vary from JMenu to JMenuItem
		JMenuItem jmi = jm.add(new JMenuItem("New...", 'N'));
		jmi = jm.add(new JMenuItem("Add...", 'A'));
		jm.addSeparator();
		jmi = jm.add(new JMenuItem("Exit", 'X'));
		jmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		// Sort menu
		jm = jmb.add(new JMenu("Sort"));
		jm.setMnemonic('S');
		jmi = jm.add(new JMenuItem("Name", 'N'));
		jmi = jm.add(new JMenuItem("Grade", 'G'));
		
		// Help menu
		jm = jmb.add(new JMenu("About"));
		jm.setMnemonic('H');
		jmi = jm.add(new JMenuItem("About", 'A'));
		
		jmi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GuiSample.this, 
                        new JLabel("<html><big><center>"
                                + "Super GUI App v0.1<br>"
                                + "Copyright &copy; 2015 Jim Shaw<br>"
                                + "All Rights Reserved"
                                + "</center></big></html>"), 
                        "About", 
                        JOptionPane.PLAIN_MESSAGE);
            }
        });
		
		// GUI configuration must be placed at the end
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 600, 500);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new GuiSample();
	}
}

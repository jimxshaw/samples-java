
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

public class Program3 extends JFrame {

    public Program3() {
        super("Super Media App");

        JMenuBar jmb = new JMenuBar();
        setJMenuBar(jmb);

        // File menu
        JMenu jm = jmb.add(new JMenu("File"));
        jm.setMnemonic('F');
        JMenuItem jmi = jm.add(new JMenuItem("Open...", 'O'));
        jmi = jm.add(new JMenuItem("List", 'L'));
        jm.addSeparator();
        jmi = jm.add(new JMenuItem("Exit", 'X'));
        jmi.addActionListener(e -> System.exit(0));

        // Zoom menu
        jm = jmb.add(new JMenu("Zoom"));
        jm.setMnemonic('Z');
        ButtonGroup bg = new ButtonGroup();
        jmi = jm.add(new JRadioButtonMenuItem("Fit to Window"));
        bg.add(jmi);
        jmi.addActionListener(e -> {
            
        });
        jmi.setMnemonic('F');
        jmi = jm.add(new JRadioButtonMenuItem("100%"));
        bg.add(jmi);
        jmi.addActionListener(e -> {
            
        });
        jmi.setMnemonic('1');
        jmi = jm.add(new JRadioButtonMenuItem("200%"));
        bg.add(jmi);
        jmi.addActionListener(e -> {
            
        });
        jmi.setMnemonic('2');

        // Help menu
        jm = jmb.add(new JMenu("Help"));
        jm.setMnemonic('H');
        jmi = jm.add(new JMenuItem("About", 'A'));
        jmi.addActionListener(e -> JOptionPane.showMessageDialog(Program3.this,
                new JLabel("<html><center><big>"
                        + "Super Media App v0.1<br>"
                        + "Copyright \u00a9 2015 James Shaw<br>"
                        + "All Rights Reserved"
                        + "</big></center></html>"),
                "About", JOptionPane.PLAIN_MESSAGE));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(1000, 250, 1200, 1000);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Program3();
    }

}


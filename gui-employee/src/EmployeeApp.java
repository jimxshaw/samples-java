
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EmployeeApp extends JFrame {

    class GuiPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING, 
                    RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, Color.white, getWidth(), getHeight(), new Color(175, 255, 175));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.blue);
            g.setFont(new Font("Calibri", Font.BOLD, 50));
            g.drawString("Name", 20, 80);
            g.drawString("Age", 250, 80);
            g.drawString("Income", 420, 80);
        }
    }

    // constructor
    public EmployeeApp() {
        super("Employee GUI App");

        JMenuBar jmb = new JMenuBar();
        setJMenuBar(jmb);

        // File menu
        JMenu jm = jmb.add(new JMenu("File"));
        jm.setMnemonic('F');
        JMenuItem jmi = jm.add(new JMenuItem("New...", 'N'));
        jmi = jm.add(new JMenuItem("Add...", 'A'));
        jm.addSeparator();
        jmi = jm.add(new JMenuItem("Exit", 'X'));
        jmi.addActionListener(e -> System.exit(0));

        // Sort menu
        jm = jmb.add(new JMenu("Sort"));
        jm.setMnemonic('S');
        ButtonGroup bg = new ButtonGroup();
        jmi = jm.add(new JRadioButtonMenuItem("Name"));
        bg.add(jmi);
        jmi.setMnemonic('N');
        jmi = jm.add(new JRadioButtonMenuItem("Age"));
        bg.add(jmi);
        jmi.setMnemonic('A');
        jmi = jm.add(new JRadioButtonMenuItem("Income"));
        bg.add(jmi);
        jmi.setMnemonic('I');

        // Help menu
        jm = jmb.add(new JMenu("Help"));
        jm.setMnemonic('H');
        jmi = jm.add(new JMenuItem("About", 'A'));
        jmi.addActionListener(e -> JOptionPane.showMessageDialog(EmployeeApp.this,
                new JLabel("<html><center><big>"
                        + "Gui App v0.1<br>"
                        + "Copyright \u00a9 2015 James Shaw<br>"
                        + "All Rights Reserved"
                        + "</big></center></html>"),
                "About",
                JOptionPane.PLAIN_MESSAGE
        ));

        this.add(new GuiPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(1000, 400, 800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EmployeeApp();
    }

}

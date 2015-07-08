
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GuiSample extends JFrame {
    StudentList list = new StudentList();
    
    class StudentPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Graphics2D g2d = (Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            GradientPaint gp = new java.awt.GradientPaint(0, 0, Color.white, getWidth(), getHeight(), new Color(75, 255, 75));
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            g.setFont(new Font("Lucida Console", Font.PLAIN, 50));
            g.setColor(Color.blue);
            g.drawString("Names", 20, 50);
            g.drawString("Grades", 240, 50);
            FontMetrics fm = g.getFontMetrics();
            for (int i = 0; i < list.length(); i++) {
                Student tmp = list.get(i);
                String s = tmp.grade + "";
                int w = fm.stringWidth(s);
                g.drawString(tmp.name, 20, 100 + i * 50);
                g.drawString(s, 400 - w, 100 + i * 50);
            }
        }
    }

    public GuiSample() {
        // gui title
        super("Super Gui App");

        // Menus need 3 things
        // 1. a MenuBar 2. Menu(s) 3. MenuItem(s)
        JMenuBar jmb = new JMenuBar();
        setJMenuBar(jmb);

        // File Menu
        JMenu jm = jmb.add(new JMenu("File"));
        jm.setMnemonic('F');

        // Shortcuts are different between a JMenu and a JMenuItem
        JMenuItem jmi = jm.add(new JMenuItem("New...", 'N'));
        jmi.addActionListener(e -> {
            String input = JOptionPane.showInputDialog("Enter 1 or more students");
            if (input != null) {
                list.getData(input);
                repaint();
            }
        });
        
        jmi = jm.add(new JMenuItem("Add...", 'A'));
        jm.addSeparator();
        jmi = jm.add(new JMenuItem("Exit", 'X'));

        // lambda expression
        jmi.addActionListener(e -> System.exit(0));

        // Sort Menu
        jm = jmb.add(new JMenu("Sort"));
        jm.setMnemonic('S');
        jmi = jm.add(new JMenuItem("Name", 'N'));
        jmi.addActionListener(e -> {
            list.sortNames();
            repaint();
        });
        
        jmi = jm.add(new JMenuItem("Grade", 'G'));

        // Help Menu
        jm = jmb.add(new JMenu("Help"));
        jm.setMnemonic('H');
        jmi = jm.add(new JMenuItem("About", 'A'));

        jmi.addActionListener(e -> JOptionPane.showMessageDialog(GuiSample.this,
                new JLabel("<html><big><center>"
                        + "Super Gui App v0.1.1<br>"
                        + "Copyright &copy; 2015 James Shaw<br>"
                        + "All Rights Reserved"
                        + "</center></big></html>"),
                "About",
                JOptionPane.PLAIN_MESSAGE));
        
        this.add(new StudentPanel());

        // Gui configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        setVisible(true);
    }

    public static void main(String[] args) {
        new GuiSample();

    }

}

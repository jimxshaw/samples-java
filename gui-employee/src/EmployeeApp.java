import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import employee.*;

public class EmployeeApp extends JFrame {

	EmployeeList list = new EmployeeList();

	class GuiPanel extends JPanel {

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			GradientPaint gp = new GradientPaint(0, 0, Color.white, 0,
					getHeight(), new Color(175, 255, 175));
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.blue);
			g.setFont(new Font("Calibri", Font.BOLD, 50));
			g.drawString("NAME", 20, 80);
			g.drawString("AGE", 250, 80);
			g.drawString("INCOME", 420, 80);
			FontMetrics fm = g.getFontMetrics();
			for (int i = 0; i < list.length(); i++) {
				Employee temp = list.get(i);
				g.drawString(temp.getName(), 20, 160 + i * 80);
				String s = String.format("%d", temp.getAge());
				int w = fm.stringWidth(s);
				g.drawString(s, 330 - w, 160 + i * 80);
				s = String.format("$%1.0f", temp.getIncome());
				w = fm.stringWidth(s);
				g.drawString(s, 590 - w, 160 + i * 80);
			}
		}
	}

	// constructor
	public EmployeeApp() {
		super("Super Gui App");

		JMenuBar jmb = new JMenuBar();
		setJMenuBar(jmb);

		// File menu
		JMenu jm = jmb.add(new JMenu("File"));
		jm.setMnemonic('F');
		JMenuItem jmi = jm.add(new JMenuItem("New...", 'N'));
		jmi.addActionListener(e -> {
			String input = JOptionPane.showInputDialog(EmployeeApp.this,
					"Enter 1 or more Commission employees");
			if (input != null) {
				list.getData(input);
				repaint();
			}
		});

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
		jmi.addActionListener(e -> {
			list.sortNames();
			repaint();
		});
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
		jmi.addActionListener(e -> JOptionPane.showMessageDialog(
				EmployeeApp.this, new JLabel("<html><center><big>"
						+ "Gui App v0.1<br>"
						+ "Copyright \u00a9 2015 James Shaw<br>"
						+ "All Rights Reserved" + "</big></center></html>"),
				"About", JOptionPane.PLAIN_MESSAGE));

		this.add(new GuiPanel());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1000, 400, 800, 600);
		setVisible(true);
	}

	public static void main(String[] args) {
		new EmployeeApp();
	}

}

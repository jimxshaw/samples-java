import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CalcApp extends JFrame {

	class GraphPanel extends JPanel {

		Tree tree = new Tree();
		javax.swing.Timer timer;
		float t;
		long lastTime;

		GraphPanel() {
			tree.parse("x*x*x");
			timer = new javax.swing.Timer(50, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					GraphPanel.this.repaint();
					long currTime = System.currentTimeMillis();
					t += (currTime - lastTime) * .001f;
					lastTime = currTime;
				}
			});
		}

		public void animate() {
			if (timer.isRunning()) {
				timer.stop();
				return;
			}
			lastTime = System.currentTimeMillis();
			timer.start();
		}

		float f(float x) {
			return tree.calc(x + t);
		}

		public void setEquation(String eqn) {
			tree.parse(eqn);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			int w = getWidth();
			int h = getHeight();
			g.setColor(Color.GRAY);
			g.drawLine(10, h / 2, w - 10, h / 2);
			g.drawLine(w / 2, 10, w / 2, h - 10);
			float scale = 40;
			float dx = 1 / scale;
			float bounds = w / scale / 2;
			float x = -bounds;
			float y = f(x);
			int grx = Math.round((scale * x) + (w / 2));
			int gry = Math.round((h / 2) - (scale * y));
			BasicStroke bs = new BasicStroke(2);
			g2d.setStroke(bs);
			g.setColor(Color.BLACK);

			while (x <= bounds) {
				int oldgrx = grx;
				int oldgry = gry;
				x += dx;
				y = f(x);
				grx = Math.round((scale * x) + (w / 2));
				gry = Math.round((h / 2) - (scale * y));

				g.drawLine(oldgrx, oldgry, grx, gry);
			}
		}
	}

	class DisplayPanel extends JPanel {

		DisplayPanel() {
			this.setBackground(Color.GRAY);
			setPreferredSize(new Dimension(200, 100));
		}
	}

	class CalcPanel extends JPanel {

		String ops = "789+456-123*0.±/";

		public CalcPanel() {
			Font fnt = new Font("Calibri", Font.PLAIN, 20);
			this.setLayout(new GridLayout(4, 4, -2, -2));
			for (int i = 0; i < ops.length(); i++) {
				add(new JButton(String.valueOf(ops.charAt(i)))).setFont(fnt);
			}
		}
	}

	public CalcApp() {
		GraphPanel gp = new GraphPanel();
		JDesktopPane desktop = new JDesktopPane();
		desktop.setBackground(new Color(192, 192, 255));

		JPopupMenu popup = new JPopupMenu();

		JMenuItem jmi = popup.add(new JMenuItem("Exit"));
		jmi.addActionListener(e -> System.exit(0));

		jmi = popup.add(new JMenuItem("Animate"));
		jmi.addActionListener(e -> gp.animate());

		desktop.setComponentPopupMenu(popup);

		JInternalFrame frame = new JInternalFrame();
		frame.setTitle("Calculator");

		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		JButton jb;
		jp.add(new DisplayPanel(), BorderLayout.NORTH);
		Font fnt = new Font("Calibri", Font.PLAIN, 20);
		jp.add(jb = new JButton("Graph")).setFont(fnt);
		jp.add(new CalcPanel(), BorderLayout.SOUTH);
		jp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		// GraphPanel gp = new GraphPanel();
		jb.addActionListener(e -> {
			String eqn = JOptionPane.showInputDialog(CalcApp.this,
					"Enter an expression");
			if (eqn != null) {
				gp.setEquation(eqn);
				repaint();
			}
		});
		frame.add(jp, BorderLayout.EAST);
		frame.add(gp, BorderLayout.CENTER);

		frame.setClosable(true);
		frame.setResizable(true);

		frame.setBounds(900, 400, 800, 600);
		frame.setVisible(true);

		desktop.add(frame);

		add(desktop);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		setVisible(true);
	}

	public static void main(String[] args) {
		try {
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e) {
			// handle exception
		} catch (ClassNotFoundException e) {
			// handle exception
		} catch (InstantiationException e) {
			// handle exception
		} catch (IllegalAccessException e) {
			// handle exception
		}
		new CalcApp();
	}
}

/*
 * E -> P + E E -> P p -> T * P p -> T T -> <x> T -> <numbers>
 */

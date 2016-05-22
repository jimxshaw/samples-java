import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import java.io.*;

public class Program3 extends JFrame {

	class ImageFilter extends javax.swing.filechooser.FileFilter {

		public boolean accept(File f) {
			return f.isDirectory() || f.getName().endsWith(".gif")
					|| f.getName().endsWith(".jpg")
					|| f.getName().endsWith(".png");
		}

		public String getDescription() {
			return "Image files (*.gif, *.jpg, *.png)";
		}
	}

	class AudioFilter extends javax.swing.filechooser.FileFilter {

		public boolean accept(File f) {
			return f.isDirectory() || f.getName().endsWith(".wav");
		}

		public String getDescription() {
			return "Wave files (*.wav)";
		}
	}

	class SoundPanel extends JPanel {

		AudioClip audio;

		public void setClip(File f) {
			try {
				audio = Applet.newAudioClip(new URL("file:"
						+ f.getAbsolutePath()));
			} catch (MalformedURLException exc) {
				System.err.println("Bad URL");
			}
		}

		public SoundPanel() {
			Font fnt = new Font("Calibri", Font.BOLD, 50);
			JButton jb;
			add(jb = new JButton("Play"));
			jb.addActionListener(e -> audio.play());
			// jb.setPreferredSize(new Dimension(300, 100));
			jb.setFont(fnt);
			add(jb = new JButton("Loop"));
			jb.addActionListener(e -> audio.loop());
			jb.setFont(fnt);
			add(jb = new JButton("Stop"));
			jb.addActionListener(e -> audio.stop());
			jb.setFont(fnt);

			try {
				audio = Applet.newAudioClip(new URL(
						"file:/System/Library/Sounds/Frog.aiff"));
			} catch (MalformedURLException exc) {
				System.err.println("Bad URL");
			}
		}
	}

	class ImagePanel extends JPanel {

		Image image;
		// 0 fit to window
		// 1 means 100%
		// 2 means 200%
		int imageSize = 0;

		public ImagePanel() {
			image = Toolkit
					.getDefaultToolkit()
					.getImage(
							"/Users/jamesshaw/Documents/udemy/samples-java/program3/wallpaper3.jpg");
		}

		public void setImage(File f) {
			image = Toolkit.getDefaultToolkit().getImage(f.getAbsolutePath());
		}

		public void setImageSize(int n) {
			if (imageSize != n) {
				imageSize = n;
				if (imageSize > 0) {
					this.setPreferredSize(new Dimension(imageSize
							* image.getWidth(this), imageSize
							* image.getHeight(this)));
				} else {
					this.setPreferredSize(null);
				}
				revalidate();
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (imageSize == 0) {
				g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
			} else {
				g.drawImage(image, 0, 0, imageSize * image.getWidth(this),
						imageSize * image.getHeight(this), this);
			}

			g.setFont(new Font("Impact", Font.PLAIN, 70));
			g.setColor(Color.CYAN);
			g.drawString("My Home", 100, 100);
			g.setColor(Color.blue);
			g.drawString("My Home", 102, 102);

		}
	}

	LinkList list = new LinkList();

	public Program3() {
		super("Super Media App");
		ImagePanel ip = new ImagePanel();
		SoundPanel sp = new SoundPanel();
		JTabbedPane jtb = new JTabbedPane();
		JMenuBar jmb = new JMenuBar();
		setJMenuBar(jmb);

		// File menu
		JMenu jm = jmb.add(new JMenu("File"));
		jm.setMnemonic('F');
		JMenuItem jmi = jm.add(new JMenuItem("Open...", 'O'));
		JFileChooser jfc = new JFileChooser();
		ImageFilter imageFilter = new ImageFilter();
		AudioFilter audioFilter = new AudioFilter();
		jfc.setFileFilter(imageFilter);
		jfc.setFileFilter(audioFilter);
		jfc.setCurrentDirectory(new File("."));
		jmi.addActionListener(e -> {
			if (jfc.showOpenDialog(Program3.this) == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				list.add(f);
				if (imageFilter.accept(f)) {
					jtb.setSelectedIndex(0);
					ip.setImage(f);
					repaint();
				} else if (audioFilter.accept(f)) {
					sp.setClip(f);
					jtb.setSelectedIndex(1);
				}
			}
		});
		jmi = jm.add(new JMenuItem("List", 'L'));
		jmi.addActionListener(e -> {
			JOptionPane.showMessageDialog(Program3.this, list.toString(),
					"Opened Files", JOptionPane.PLAIN_MESSAGE);
		});
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
			ip.setImageSize(0);
			repaint();
		});
		jmi.setMnemonic('F');
		jmi = jm.add(new JRadioButtonMenuItem("100%"));
		bg.add(jmi);
		jmi.addActionListener(e -> {
			ip.setImageSize(1);
			repaint();
		});
		jmi.setMnemonic('1');
		jmi = jm.add(new JRadioButtonMenuItem("200%"));
		bg.add(jmi);
		jmi.addActionListener(e -> {
			ip.setImageSize(2);
			repaint();
		});
		jmi.setMnemonic('2');

		// Help menu
		jm = jmb.add(new JMenu("Help"));
		jm.setMnemonic('H');
		jmi = jm.add(new JMenuItem("About", 'A'));
		jmi.addActionListener(e -> JOptionPane.showMessageDialog(Program3.this,
				new JLabel("<html><center><big>" + "Super Media App v0.1<br>"
						+ "Copyright \u00a9 2015 James Shaw<br>"
						+ "All Rights Reserved" + "</big></center></html>"),
				"About", JOptionPane.PLAIN_MESSAGE));

		JScrollPane jsp = new JScrollPane(ip);

		jtb.addTab("Image", jsp);
		jtb.addTab("Audio", sp);
		add(jtb);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(1000, 250, 800, 600);
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
		new Program3();
	}

}


import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;

public class Program3 extends JFrame {

    class SoundPanel extends JPanel {

        AudioClip audio;

        public SoundPanel() {
            Font fnt = new Font("Calibri", Font.BOLD, 50);
            JButton jb;
            add(jb = new JButton("Play"));
            jb.addActionListener(e -> audio.play());
            jb.setFont(fnt);
            add(jb = new JButton("Loop"));
            jb.addActionListener(e -> audio.loop());
            jb.setFont(fnt);
            add(jb = new JButton("Stop"));
            jb.addActionListener(e -> audio.stop());
            jb.setFont(fnt);

            try {
                audio = Applet.newAudioClip(new URL("file:/System/Library/Sounds/Purr.aiff"));
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
            image = Toolkit.getDefaultToolkit().getImage("/Users/jamesshaw/Documents/udemy/samples-java/program3/wallpaper3.jpg");
        }

        public void setImageSize(int n) {
            if (imageSize != n) {
                imageSize = n;
                if (imageSize > 0) {
                    this.setPreferredSize(new Dimension(imageSize * image.getWidth(this), imageSize * image.getHeight(this)));
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
                g.drawImage(image, 0, 0, imageSize * image.getWidth(this), imageSize * image.getHeight(this), this);
            }
        }
    }

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

        ImagePanel ip = new ImagePanel();

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
                new JLabel("<html><center><big>"
                        + "Super Media App v0.1<br>"
                        + "Copyright \u00a9 2015 James Shaw<br>"
                        + "All Rights Reserved"
                        + "</big></center></html>"),
                "About", JOptionPane.PLAIN_MESSAGE));

        JScrollPane jsp = new JScrollPane(ip);

        JTabbedPane jtb = new JTabbedPane();
        jtb.addTab("Image", jsp);
        jtb.addTab("Audio", new SoundPanel());
        add(jtb);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(1000, 250, 800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Program3();
    }

}

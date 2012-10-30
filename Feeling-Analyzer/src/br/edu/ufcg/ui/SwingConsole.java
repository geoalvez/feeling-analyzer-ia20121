package br.edu.ufcg.ui;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;

public class SwingConsole {
	public static void run(final JFrame f, final int width, final int height) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				f.setTitle(" Feeling Analyzer IA2012.1");
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(width, height);
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
				f.setVisible(true);
				f.setResizable(false);
			}
		});
	}
}

package br.edu.ufcg.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import static br.edu.ufcg.ui.SwingConsole.*;

@SuppressWarnings("serial")
public class FeelingAnalyzer extends JFrame {
	private JTextArea txt = new JTextArea("Por favor, digite um frase no local abaixo");
	private JButton b1 = new JButton("Analisar Sentimento");
	private JTextField input = new JTextField(30);
	private JTextArea output = new JTextArea(1, 10);
	private ActionListener bl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Random rn = new Random();
			if (rn.nextBoolean()) {
				output.setForeground(Color.green);
				output.setText("Feliz =)");
			} else {
				output.setForeground(Color.red);
				output.setText("Triste =(");
			}
		}
	};

	public FeelingAnalyzer() {
		b1.addActionListener(bl);
		setLayout(new FlowLayout());
		txt.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		b1.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		input.setFont(new Font("Comic Sans MS", Font.PLAIN, 22));
		output.setFont(new Font("Comic Sans MS", Font.PLAIN, 32));
		
		txt.setBackground(getForeground());
		txt.setEditable(false);
		output.setBackground(getForeground());
		output.setEditable(false);
		add(txt);
		add(input);
		add(b1);
		add(output);
	}

	public static void main(String[] args) {
		run(new FeelingAnalyzer(), 640, 170);
	}
} // /:~ 
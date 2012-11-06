package br.edu.ufcg.ui;

import javax.swing.*;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.SerializationHelper;
import br.edu.ufcg.analisador.FraseClassifier;
import br.edu.ufcg.exception.AnalyzeException;
import br.edu.ufcg.treinamento.ExtratorDeDadosFrases;
import br.edu.ufcg.treinamento.classifier.ClassifierUtils;
import br.edu.ufcg.treinamento.entidade.FraseDados;
import br.edu.ufcg.util.Constantes;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;

import static br.edu.ufcg.ui.SwingConsole.*;

@SuppressWarnings("serial")
public class FeelingAnalyzer extends JFrame {
	private static JTextArea txt = new JTextArea("Aguarde...                                ");
	private static JButton b1 = new JButton("Analisar Sentimento");
	private static JTextField input = new JTextField(30);
	private static JTextArea output = new JTextArea(1, 10);
	
	private static FraseClassifier classificador;
		
	private ActionListener bl = new ActionListener() {
		public void actionPerformed(ActionEvent e) {			
			analisa();
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
		
		input.setEnabled(false);
		b1.setEnabled(false);
		
		add(txt);
		add(input);
		add(b1);
		add(output);
	}

	public static void main(String[] args) throws Exception {
		run(new FeelingAnalyzer(), 640, 170);

		
		String model = Constantes.MODELS_DIR + Constantes.FILE_SEPARATOR
				+ Constantes.MODELO_MULTILAYER_PERCEPTRON;
		
		try { // tenta ler o modelo salvo em arquivo
			classificador = (FraseClassifier) SerializationHelper
					.read(model);
		} catch (Exception e) {
			classificador = new FraseClassifier(new MultilayerPerceptron(), model);
		}
				
		try {
			txt.setText("Aguarde... Carregando classificador       ");
			classificador.load();
			txt.setText("           Carregamento concluido!        ");
			
		} catch (AnalyzeException e1) {
			txt.setText("Erro durante o treinamento                ");
			System.err.println("Erro ao treinar classificador: " + e1.getMessage());
			TimeUnit.SECONDS.sleep(1); 
			//System.exit(1);
		}
		TimeUnit.SECONDS.sleep(2); 
		txt.setText("Por favor digite uma frase no local abaixo");
		input.setEnabled(true);
		b1.setEnabled(true);
	}
	
	public void analisa(){
		String linha = input.getText();
		String classe = "desconhecido";
		try {
			String confFile = Constantes.RESOURCE_DIR + Constantes.FILE_SEPARATOR + Constantes.CONF_FILE;
			ClassifierUtils.inicializaPalavrasConf(confFile);
			FraseDados fraseDados = ExtratorDeDadosFrases.obtemDadosFrases(linha);
			classe = classificador.classify(fraseDados);
		} catch (AnalyzeException e) {
			System.err.println(e.getMessage());
		}
		classe = classe.toUpperCase();
		if (classe.equals("FELIZ")) {
			output.setForeground(Color.yellow);
			output.setText("FELIZ =)");
		} else if (classe.equals("TRISTE")){
			output.setForeground(Color.blue);
			output.setText("TRISTE =(");
		} 
	}	
	
} 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.SerializationHelper;
import br.edu.ufcg.analisador.FraseClassifier;
import br.edu.ufcg.exception.AnaliseException;
import br.edu.ufcg.treinamento.ExtratorDeDadosFrases;
import br.edu.ufcg.treinamento.classifier.ClassifierUtils;
import br.edu.ufcg.treinamento.entidade.FraseDados;
import br.edu.ufcg.util.Constants;


public class CommandLine {
	public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));


		FraseClassifier classificador;
		
		String model = Constants.MODELS_DIR + Constants.FILE_SEPARATOR
				+ Constants.MODELO_MULTILAYER_PERCEPTRON;
		
		try { // tenta ler o modelo salvo em arquivo
			classificador = (FraseClassifier) SerializationHelper
					.read(model);
		} catch (Exception e) {
			classificador = new FraseClassifier(new MultilayerPerceptron(), model);
		}
		
		
		try {
			System.out.println("==== Carregando classificador ====");
			classificador.load();
			System.out.println("==== Fim do carregamento ====");
		} catch (AnaliseException e1) {
			System.err.println("Erro ao treinar classificador: " + e1.getMessage());
			System.exit(1);
		}

		System.out
				.println("==== Bem-vindo ao analisador de sentimentos em frases! ===");
		System.out.println("==== Digite uma frase para se analisada. ");

		while (true) {
			System.out.print("Frase a ser analisada: ");
			String linha = bf.readLine();
			if (linha == null || linha.trim().equals(".")) {
				break;
			}
			String classe = "desconhecido";
			try {
				String confFile = Constants.RESOURCE_DIR + Constants.FILE_SEPARATOR + Constants.CONF_FILE;
				ClassifierUtils.inicializaPalavrasConf(confFile);
				FraseDados fraseDados = ExtratorDeDadosFrases.obtemDadosFrases(linha);
				classe = classificador.classify(fraseDados);
			} catch (AnaliseException e) {
				System.err.println(e.getMessage());
			}
			classe = classe.toUpperCase();
			System.out.println("A frase: '" + linha
					+ "' foi classificada como uma frase do tipo " + classe);
		}


	}
}

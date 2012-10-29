package br.edu.ufcg.util;

import br.edu.ufcg.analisador.FraseClassifier;
import br.edu.ufcg.exception.AnaliseException;
import weka.classifiers.bayes.BayesNet;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.trees.J48;
import weka.core.SerializationHelper;

public class CommandLineParser {
	public static final String BAYES_NET = "net";
	public static final String NAIVE_BAYES = "naive";
	public static final String J48 = "j48";
	

	public static FraseClassifier getClassificadorFromCommand(String tipoAnalisador) throws AnaliseException {
		FraseClassifier classificador = null;
		
//		if (tipoAnalisador.equals(BAYES_NET)) {
//			String model = Constants.MODELS_DIR + Constants.FILE_SEPARATOR
//					+ Constants.MODELO_BAYES_NET;
//			try { // tenta ler o modelo salvo em arquivo
//				classificador = (FraseClassifier) SerializationHelper
//						.read(model);
//			} catch (Exception e) {
//				classificador = new FraseClassifier(new BayesNet(), model);
//			}
//		} else if (tipoAnalisador.equals(NAIVE_BAYES)) {
//			String model = Constants.MODELS_DIR + Constants.FILE_SEPARATOR
//					+ Constants.MODELO_NAIVE_BAYES;
//			try {
//				classificador = (FraseClassifier) SerializationHelper
//						.read(model);
//			} catch (Exception e) {
//				classificador = new FraseClassifier(new NaiveBayesUpdateable(),
//						model);
//			}
//		} else if (tipoAnalisador.equals(J48)) {
//			String model = Constants.MODELS_DIR + Constants.FILE_SEPARATOR
//					+ Constants.MODELO_J48;
//			try {
//				classificador = (FraseClassifier) SerializationHelper
//						.read(model);
//			} catch (Exception e) {
//				classificador = new FraseClassifier(new J48(), model);
//			}
//		} else {
//			throw new AnaliseException("Classificador desconhecido!");
//		}

		return classificador;
	}
}

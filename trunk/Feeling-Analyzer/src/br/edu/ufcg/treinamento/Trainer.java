package br.edu.ufcg.treinamento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import br.edu.ufcg.analisador.FraseClassifier;
import br.edu.ufcg.exception.AnalyzeException;
import br.edu.ufcg.treinamento.classifier.ClassifierUtils;
import br.edu.ufcg.treinamento.entidade.FraseDados;
import br.edu.ufcg.util.Constantes;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.SerializationHelper;

public class Trainer {
	public static void main(String[] args) throws IOException, AnalyzeException {
		if (args.length != 2) {
			System.err.println("Erro na passagem de parametros ex:<c:\\...> <feliz ou triste>");
			return;
		}

		String dirArquivos = args[0];
		String classe = args[1];
		String model = Constantes.MODELS_DIR + Constantes.FILE_SEPARATOR
				+ Constantes.MODELO_MULTILAYER_PERCEPTRON;

		FraseClassifier classificador = null;

		try { // tenta ler o modelo salvo em arquivo
			classificador = (FraseClassifier) SerializationHelper.read(model);
		} catch (Exception e) {
			classificador = new FraseClassifier(new MultilayerPerceptron(),
					model);
		}

		File diretorio = new File(dirArquivos);

		for (File f : diretorio.listFiles()) {
			if (f.isHidden() || !f.getName().endsWith(".in")) {
				continue;
			}
			@SuppressWarnings("resource")//TODO remover esse supress 
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "UTF-8"));
			String confFile = Constantes.RESOURCE_DIR + Constantes.FILE_SEPARATOR
					+ Constantes.CONF_FILE;
			ClassifierUtils.inicializaPalavrasConf(confFile);
			while (true) {
				String frase = bf.readLine();
				if (frase == null) {
					break;
				}
				FraseDados fraseDados = ExtratorDeDadosFrases.obtemDadosFrases(
						frase, classe);
				classificador.train(fraseDados, classe);
			}
		}

		try {
			SerializationHelper.write(classificador.getModelLocation(),
					classificador);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

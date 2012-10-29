package br.edu.ufcg.treinamento;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import br.edu.ufcg.analisador.FraseClassifier;
import br.edu.ufcg.exception.AnaliseException;
import br.edu.ufcg.treinamento.classifier.ClassifierUtils;
import br.edu.ufcg.treinamento.entidade.FraseDados;
import br.edu.ufcg.util.Constants;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.SerializationHelper;

public class Trainer {
	public static void main(String[] args) throws IOException, AnaliseException {
		if (args.length != 2) {
			System.err.println("Erro. Os parametros corretos sao: "
					+ "<dirArquivos> <classe> <tipoAnalisador>. "
					+ "Onde classe=feliz|triste");
			return;
		}

		String diretorioArquivos = args[0];
		String classe = args[1];
		
		String model = Constants.MODELS_DIR + Constants.FILE_SEPARATOR
				+ Constants.MODELO_MULTILAYER_PERCEPTRON;

		
		FraseClassifier classificador = null;

		try { // tenta ler o modelo salvo em arquivo
			classificador = (FraseClassifier) SerializationHelper
					.read(model);
		} catch (Exception e) {
			classificador = new FraseClassifier(new MultilayerPerceptron(), model);
		}

		File diretorio = new File(diretorioArquivos);
		System.out.println("Comecar a treinar o classificador<"
				+ " com os arquivos do diretorio <"
				+ diretorioArquivos + ">. Os frases sao do tipo <" + classe
				+ ">.");

		for (File f : diretorio.listFiles()) {
			if (f.isHidden() || !f.getName().endsWith(".in")) {
				continue;
			}
			System.out.println("Treinando o arquivo atual " + f.getName());
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "UTF-8"));
			String confFile = Constants.RESOURCE_DIR + Constants.FILE_SEPARATOR + Constants.CONF_FILE;
			ClassifierUtils.inicializaPalavrasConf(confFile);
			while (true) {
				String frase = bf.readLine();
				
				if (frase == null) {
					break;
				}

				FraseDados fraseDados = ExtratorDeDadosFrases.obtemDadosFrases(frase, classe);
				
				classificador.train(fraseDados, classe);
			}
			System.out.println("Fim do treino com o arquivo " + f.getName());
		}

		try {
			System.out.println("Salvando o modelo treinado (" + classificador.getModelLocation() + ")");
			SerializationHelper.write(classificador.getModelLocation(),
					classificador);
			System.out.println("Modelo salvo com sucesso!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

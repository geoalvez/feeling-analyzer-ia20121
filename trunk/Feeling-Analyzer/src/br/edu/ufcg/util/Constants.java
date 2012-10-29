package br.edu.ufcg.util;

public class Constants {
	public static final String FILE_SEPARATOR = System
			.getProperty("file.separator");
	public static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	public static final String MODELS_DIR = "models";
	public static final String RESOURCE_DIR = "resources";
	
	

	public static final String MODELO_MULTILAYER_PERCEPTRON="mutiLayerPerceptron.model";
	

	public static final String STOPWORDS_FILE = RESOURCE_DIR + FILE_SEPARATOR
			+ "stopwords.dic";
	public static final String DICTIONARY_FILE = RESOURCE_DIR + FILE_SEPARATOR
			+ "ptbr.dic";
	
	
	public static final String CONF_FILE = "conf.txt";
}

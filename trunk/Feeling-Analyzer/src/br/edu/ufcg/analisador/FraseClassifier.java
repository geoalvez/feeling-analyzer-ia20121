package br.edu.ufcg.analisador;

import java.io.File;
import java.io.Serializable;

import br.edu.ufcg.exception.AnaliseException;
import br.edu.ufcg.treinamento.entidade.FraseDados;
import br.edu.ufcg.util.Constants;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class FraseClassifier implements Serializable {
	private static final long serialVersionUID = -123455813150452885L;

	private static final int TOTAL_CAPACITY = 1000000;

	private static final String NOME_CONJUNTO_TREINAMENTO = "TreinamentoClassificadorFrases";
	private static final String NOME_CONJUNTO_TESTES = "TestesClassificadorFrases";

	private static final String ATTRIBUTE_AGRADECIMENTO = "Agradecimento";
	private static final String ATTRIBUTE_PRAZER = "Prazer";
	private static final String ATTRIBUTE_PERDA = "Perda";
	private static final String ATTRIBUTE_EUFORIA = "Euforia";
	private static final String ATTRIBUTE_SAD_WORDS = "Sad Words";
	private static final String ATTRIBUTE_BAD_WORDS = "Bad Words";
	private static final String ATTRIBUTE_ARREPENDIMENTO = "Arrependimento";
	private static final String ATTRIBUTE_DOR = "Dor";
	private static final String ATTRIBUTE_NEGATIVE_WORDS = "Negative Words";
	private static final String ATTRIBUTE_AFIRMATIVE_WORDS = "Afirmative Words";
	
	
	
	private static final String ATTRIBUTE_CLASS = "Class";

	private static final int TOTAL_ATRIBUTOS = 11;
	private static final int TOTAL_CLASSES = 2;

	private Instances trainningData;
	private Instances testingData;

	private StringToWordVector filter;

	private Classifier classifier;

	private String modelLocation;
	private boolean updated;

	public FraseClassifier(Classifier classifier, String modelLocation) {
		this.modelLocation = modelLocation;
		this.classifier = classifier;
		this.filter = initFilter();

		trainningData = initInstances(NOME_CONJUNTO_TREINAMENTO);
		testingData = initInstances(NOME_CONJUNTO_TESTES);
	}

	public void train(FraseDados fraseDados, String fraseClasse) {
		Instance instance = createInstance(fraseDados, trainningData);
		instance.setClassValue(fraseClasse);

		trainningData.add(instance);
		updated = false;
	}

	public void test(FraseDados fraseDados, String fraseClass) {
		Instance instance = createInstance(fraseDados, testingData);
		instance.setClassValue(fraseClass);

		testingData.add(instance);
	}

	public Evaluation evaluate() throws AnaliseException {
		try {
			load();

			Instances filteredData = Filter.useFilter(testingData, filter);

			Evaluation evaluation = new Evaluation(filteredData);
			evaluation.evaluateModel(classifier, filteredData);
			return evaluation;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AnaliseException(e.getMessage());
		}
	}

	public String classify(FraseDados fraseDados) throws AnaliseException {
		if (trainningData.numInstances() == 0) {
			throw new AnaliseException(
					"Classificador nao treinado. Nao foi possivel classificar a frase!");
		}

		load();

		Instances testset = trainningData.stringFreeStructure();
		Instance instance = createInstance(fraseDados, testset);
		return classify(instance);
	}

	private String classify(Instance instance) throws AnaliseException {
		try {
			filter.input(instance);
			double predicted = classifier.classifyInstance(filter.output());
			return trainningData.classAttribute().value((int) predicted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AnaliseException(e.getMessage());
		}
	}

	public void load() throws AnaliseException {
		if (!updated) {
			try {
				filter.setInputFormat(trainningData);
				Instances filteredData = Filter.useFilter(trainningData,
						filter);
				classifier.buildClassifier(filteredData);
			} catch (Exception e) {
				throw new AnaliseException(e.getMessage());
			}
			updated = true;
		}
	}

	public String getModelLocation() {
		return modelLocation;
	}

	public String getClassifierInfo() {
		return classifier.getClass().getName();
	}

	public String toString() {
		return classifier.toString();
	}

	private Instance createInstance(FraseDados fraseDados, Instances data) {
		Instance instance = new Instance(TOTAL_ATRIBUTOS);

		Attribute messageAtt;
		
		messageAtt = data.attribute(ATTRIBUTE_AGRADECIMENTO);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasAgradecimento())));

		messageAtt = data.attribute(ATTRIBUTE_PRAZER);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasPrazer())));
		
		messageAtt = data.attribute(ATTRIBUTE_PERDA);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasPerda())));

		messageAtt = data.attribute(ATTRIBUTE_EUFORIA);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasEuforia())));

		messageAtt = data.attribute(ATTRIBUTE_SAD_WORDS);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasSadWords())));

		messageAtt = data.attribute(ATTRIBUTE_BAD_WORDS);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasBadWords())));
		messageAtt = data.attribute(ATTRIBUTE_ARREPENDIMENTO);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasArrependimento())));

		messageAtt = data.attribute(ATTRIBUTE_DOR);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasDor())));

		messageAtt = data.attribute(ATTRIBUTE_NEGATIVE_WORDS);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasNegativas())));

		messageAtt = data.attribute(ATTRIBUTE_AFIRMATIVE_WORDS);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer.toString(fraseDados.getNumPalavrasAfirmativas())));
		
		instance.setDataset(data);
		return instance;
	}

	private StringToWordVector initFilter() {
		StringToWordVector filter = new StringToWordVector();
		filter.setTokenizer(new SpellingTokenizer());
		filter.setLowerCaseTokens(true);
		filter.setStopwords(new File(Constants.STOPWORDS_FILE));
		return filter;
	}

	private Instances initInstances(String name) {
		FastVector classValues = new FastVector(TOTAL_CLASSES);
		classValues.addElement("feliz");
		classValues.addElement("triste");

		FastVector attributes = new FastVector(TOTAL_ATRIBUTOS);
		attributes
				.addElement(new Attribute(ATTRIBUTE_AGRADECIMENTO, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_PRAZER, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_PERDA, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_EUFORIA, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_SAD_WORDS, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_BAD_WORDS, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_ARREPENDIMENTO, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_DOR, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_NEGATIVE_WORDS, (FastVector) null));
		attributes
		.addElement(new Attribute(ATTRIBUTE_AFIRMATIVE_WORDS, (FastVector) null));
		
		attributes.addElement(new Attribute(ATTRIBUTE_CLASS, classValues));

		Instances instances = new Instances(name, attributes, TOTAL_CAPACITY);
		instances.setClassIndex(instances.numAttributes() - 1);
		return instances;
	}
}
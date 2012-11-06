package br.edu.ufcg.analisador;

import java.io.Serializable;
import br.edu.ufcg.exception.AnalyzeException;
import br.edu.ufcg.treinamento.entidade.FraseDados;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class FraseClassifier implements Serializable {

	private static final long serialVersionUID = -3115937641790684154L;


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
	private static final int TOTAL_CAPACITY = 1000000;
	private static final String NOME_CONJUNTO_TREINAMENTO = "TreinamentoClassifierFrases";
	private static final String ATTRIBUTE_CLASS = "Class";
	private static final int TOTAL_ATRIBUTOS = 11;
	private static final int TOTAL_CLASSES = 2;

	private Instances trainingData;
	private StringToWordVector filter;
	private Classifier classifier;
	private String modelLoc;
	private boolean updated;

	public FraseClassifier(Classifier classifier, String modelLoc) {
		this.modelLoc = modelLoc;
		this.classifier = classifier;
		this.filter = initFilter();
		trainingData = initInstances(NOME_CONJUNTO_TREINAMENTO);
	}

	public void train(FraseDados fraseDados, String fraseClasse) {
		Instance instance = createInstance(fraseDados, trainingData);
		instance.setClassValue(fraseClasse);
		trainingData.add(instance);
		updated = false;
	}
	
	public void load() throws AnalyzeException {
		if (!updated) {
			try {
				filter.setInputFormat(trainingData);
				Instances filteredData = Filter
						.useFilter(trainingData, filter);
				classifier.buildClassifier(filteredData);
			} catch (Exception e) {
				throw new AnalyzeException(e.getMessage());
			}
			updated = true;
		}
	}

	public String classify(FraseDados fraseDados) throws AnalyzeException {
		if (trainingData.numInstances() == 0) {
			throw new AnalyzeException("classificador não treinado");
		}
		load();
		Instances testset = trainingData.stringFreeStructure();
		Instance instance = createInstance(fraseDados, testset);
		return classify(instance);
	}

	private String classify(Instance instance) throws AnalyzeException {
		try {
			filter.input(instance);
			double predicted = classifier.classifyInstance(filter.output());
			return trainingData.classAttribute().value((int) predicted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AnalyzeException(e.getMessage());
		}
	}

	public String getModelLocation() {
		return modelLoc;
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
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasAgradecimento())));
		messageAtt = data.attribute(ATTRIBUTE_PRAZER);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasPrazer())));
		messageAtt = data.attribute(ATTRIBUTE_PERDA);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasPerda())));
		messageAtt = data.attribute(ATTRIBUTE_EUFORIA);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasEuforia())));
		messageAtt = data.attribute(ATTRIBUTE_SAD_WORDS);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasSadWords())));
		messageAtt = data.attribute(ATTRIBUTE_BAD_WORDS);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasBadWords())));
		messageAtt = data.attribute(ATTRIBUTE_ARREPENDIMENTO);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasArrependimento())));
		messageAtt = data.attribute(ATTRIBUTE_DOR);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasDor())));
		messageAtt = data.attribute(ATTRIBUTE_NEGATIVE_WORDS);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasNegativas())));
		messageAtt = data.attribute(ATTRIBUTE_AFIRMATIVE_WORDS);
		instance.setValue(messageAtt, messageAtt.addStringValue(Integer
				.toString(fraseDados.getNumPalavrasAfirmativas())));
		instance.setDataset(data);
		return instance;
	}

	private Instances initInstances(String name) {
		FastVector classValues = new FastVector(TOTAL_CLASSES);
		classValues.addElement("feliz");
		classValues.addElement("triste");
		FastVector attributes = new FastVector(TOTAL_ATRIBUTOS);
		attributes.addElement(new Attribute(ATTRIBUTE_AGRADECIMENTO,
				(FastVector) null));
		attributes
				.addElement(new Attribute(ATTRIBUTE_PRAZER, (FastVector) null));
		attributes
				.addElement(new Attribute(ATTRIBUTE_PERDA, (FastVector) null));
		attributes.addElement(new Attribute(ATTRIBUTE_EUFORIA,
				(FastVector) null));
		attributes.addElement(new Attribute(ATTRIBUTE_SAD_WORDS,
				(FastVector) null));
		attributes.addElement(new Attribute(ATTRIBUTE_BAD_WORDS,
				(FastVector) null));
		attributes.addElement(new Attribute(ATTRIBUTE_ARREPENDIMENTO,
				(FastVector) null));
		attributes.addElement(new Attribute(ATTRIBUTE_DOR, (FastVector) null));
		attributes.addElement(new Attribute(ATTRIBUTE_NEGATIVE_WORDS,
				(FastVector) null));
		attributes.addElement(new Attribute(ATTRIBUTE_AFIRMATIVE_WORDS,
				(FastVector) null));
		attributes.addElement(new Attribute(ATTRIBUTE_CLASS, classValues));
		Instances instances = new Instances(name, attributes, TOTAL_CAPACITY);
		instances.setClassIndex(instances.numAttributes() - 1);
		return instances;
	}
	
	private StringToWordVector initFilter() {
		StringToWordVector filter = new StringToWordVector();
		filter.setLowerCaseTokens(true);
		return filter;
	}
}
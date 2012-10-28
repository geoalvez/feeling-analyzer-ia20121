package br.edu.ufcg.treinamento.classifier;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassifierUtils {
	
	public static List<String> AGRADECIMENTO_LIST = new ArrayList<String>();
	public static List<String> PRAZER_LIST = new ArrayList<String>();
	public static List<String> PERDA_LIST = new ArrayList<String>();
	public static List<String> EUFORIA_LIST = new ArrayList<String>();
	public static List<String> SAD_WORDS_LIST = new ArrayList<String>();
	public static List<String> BAD_WORDS_LIST = new ArrayList<String>();
	public static List<String> GOOD_EMOTICONS_LIST = new ArrayList<String>();
	public static List<String> BAD_EMOTICONS_LIST = new ArrayList<String>();
	public static List<String> ARREPENDIMENTO_LIST = new ArrayList<String>();
	public static List<String> DOR_LIST = new ArrayList<String>();	
	public static List<String> NEGATIVE_WORDS_LIST = new ArrayList<String>();
	public static List<String> AFIRMATIVE_WORDS_LIST = new ArrayList<String>();
	
	private static final String AGRADECIMENTO = "AGRADECIMENTO=";
	private static final String PRAZER = "PRAZER=";
	private static final String PERDA = "PERDA=";
	private static final String EURORIA = "EURORIA=";
	private static final String SAD_WORDS = "SAD_WORDS=";
	private static final String BAD_WORDS = "BAD_WORDS=";
	private static final String GOOD_EMOTICONS = "GOOD_EMOTICONS=";
	private static final String BAD_EMOTICONS = "BAD_EMOTICONS=";
	private static final String ARREPENDIMENTO = "ARREPENDIMENTO=";
	private static final String DOR = "DOR=";
	private static final String NEGATIVE_WORDS = "NEGATIVE_WORDS=";
	private static final String AFIRMATIVE_WORDS = "AFIRMATIVE_WORDS=";
	private static final String EMPTY_WORD = "";
	private static final String REGEX_SPIT_ARQ_CONF = ",";
	private static final String REGEX_SPIT_FRASE = " ";
	
	
	
	
	public static void inicializaPalavrasConf(String file){
		
		
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			String str;
			while (in.ready()) {
				str = in.readLine();
				if (str.startsWith(AGRADECIMENTO)){
					str = str.replace(AGRADECIMENTO, EMPTY_WORD);
					
					AGRADECIMENTO_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					
	 			}
				else if (str.startsWith(PRAZER)){
					str = str.replace(PRAZER, EMPTY_WORD);
					
					PRAZER_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));

	 			}
				else if (str.startsWith(PERDA)){
					str = str.replace(PERDA, EMPTY_WORD);
					
					PERDA_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					
	 			}				
				else if (str.startsWith(EURORIA)){
					str = str.replace(EURORIA, EMPTY_WORD);
					
					EUFORIA_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					
	 			}		
				else if (str.startsWith(SAD_WORDS)){
					str = str.replace(SAD_WORDS, EMPTY_WORD);
					
					SAD_WORDS_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));

	 			}
				
				else if (str.startsWith(BAD_WORDS)){
					str = str.replace(BAD_WORDS, EMPTY_WORD);
					
					List<String> lista = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					BAD_WORDS_LIST = (ArrayList<String>) lista;
	 			}				
				else if (str.startsWith(GOOD_EMOTICONS)){
					str = str.replace(GOOD_EMOTICONS, EMPTY_WORD);
					
					GOOD_EMOTICONS_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
	 			}				
				else if (str.startsWith(BAD_EMOTICONS)){
					str = str.replace(BAD_EMOTICONS, EMPTY_WORD);
					
					BAD_EMOTICONS_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					
	 			}		
				else if (str.startsWith(ARREPENDIMENTO)){
					str = str.replace(ARREPENDIMENTO, EMPTY_WORD);
					
					ARREPENDIMENTO_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					
	 			}
				
				else if (str.startsWith(DOR)){
					str = str.replace(DOR, EMPTY_WORD);
					
					DOR_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					
				}
				
				
				else if (str.startsWith(NEGATIVE_WORDS)){
					str = str.replace(NEGATIVE_WORDS, EMPTY_WORD);
					
					NEGATIVE_WORDS_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					
	 			}
				else if (str.startsWith(AFIRMATIVE_WORDS)){
					str = str.replace(AFIRMATIVE_WORDS, EMPTY_WORD);
					
					AFIRMATIVE_WORDS_LIST = Arrays.asList(str.split(REGEX_SPIT_ARQ_CONF));
					
	 			}
				
				
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo ["+file+"] não foi encontrado.");
		} catch (Exception e) {
			System.out.println("Falha ao tentar ler o arquivo informado ["+file+"].");
		}

		
	}
	
	public static int contaNumAgradecimento(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);
		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (AGRADECIMENTO_LIST.contains(palavra)){
				count++;
			}
		}
		
		System.out.println(count+" palavras que caracterizam agradecimento foram encontradas na frase ["+frase+"]. ");
		
		return count;
	}

	public static int contaNumPrazer(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (PRAZER_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam prazer foram encontradas na frase ["+frase+"]. ");

		return count;
	}	
	
	public static int contaNumPerda(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (PERDA_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam perda foram encontradas na frase ["+frase+"]. ");

		return count;
	}	
	
	
	public static int contaNumEuforia(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (EUFORIA_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam euforia foram encontradas na frase ["+frase+"]. ");

		return count;
	}	
	
	public static int contaNumSadWord(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (SAD_WORDS_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam 'sad words' foram encontradas na frase ["+frase+"]. ");

		return count;
	}		
	
	
	public static int contaNumBadWord(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (BAD_WORDS_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam 'bad words' foram encontradas na frase ["+frase+"]. ");

		return count;
	}	
	
	
	public static int contaNumGoodEmoticons(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (GOOD_EMOTICONS_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam 'good emoticons' foram encontradas na frase ["+frase+"]. ");

		return count;
	}
	
	
	public static int contaNumBadEmoticons(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (BAD_EMOTICONS_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam 'bad emoticons' foram encontradas na frase ["+frase+"]. ");

		return count;
	}		
	
	public static int contaNumArrependimento(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (ARREPENDIMENTO_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam arrependimento foram encontradas na frase ["+frase+"]. ");

		return count;
	}		
	
	public static int contaNumDor(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (DOR_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam dor foram encontradas na frase ["+frase+"]. ");

		return count;
	}		
	
	public static int contaNumNegativeWords(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (NEGATIVE_WORDS_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam 'negative words' foram encontradas na frase ["+frase+"]. ");

		return count;
	}
	
	public static int contaNumAfirmativeWords(String frase){
		String[] palavras = frase.split(REGEX_SPIT_FRASE);
		List<String> listaPalavras = new ArrayList<String>();
		
		listaPalavras = Arrays.asList(palavras);

		int count = 0;
		
		for (int i = 0; i < listaPalavras.size(); i++){
			String palavra = listaPalavras.get(i);
			if (AFIRMATIVE_WORDS_LIST.contains(palavra)){
				count++;
			}
		}

		System.out.println(count+" palavras que caracterizam 'afirmative words' foram encontradas na frase ["+frase+"]. ");

		return count;
	}	
}

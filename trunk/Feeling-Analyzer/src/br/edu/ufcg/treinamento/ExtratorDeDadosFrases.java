package br.edu.ufcg.treinamento;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.edu.ufcg.treinamento.classifier.ClassifierUtils;
import br.edu.ufcg.treinamento.entidade.FraseDados;


public class ExtratorDeDadosFrases {

	public static String tipoDaFrase;
	
	public static ArrayList<FraseDados> listaFraseDados = new ArrayList<FraseDados>();	
	
	private static final String FELIZ = "feliz";
	
	private static final String TRISTE = "triste";
	
	public static Pattern[] PATTERNS;
	public static String[] REPLACES = { "a", "e", "i", "o", "u", "c", "n" };

	
	private static String removeConsecutivelyChars(String text) {
		int end = text.length() - 1;
		while (end >= 1 && text.charAt(end - 1) == text.charAt(end)) {
			text = text.substring(0, end);
			end--;
		}
		return text;
	}

	private static void compilePatterns() {
		PATTERNS = new Pattern[REPLACES.length];
		PATTERNS[0] = Pattern.compile("[‚„·‡‰]", Pattern.CASE_INSENSITIVE);
		PATTERNS[1] = Pattern.compile("[ÈËÍÎ]", Pattern.CASE_INSENSITIVE);
		PATTERNS[2] = Pattern.compile("[ÌÏÓÔ]", Pattern.CASE_INSENSITIVE);
		PATTERNS[3] = Pattern.compile("[ÛÚÙıˆ]", Pattern.CASE_INSENSITIVE);
		PATTERNS[4] = Pattern.compile("[˙˘˚¸]", Pattern.CASE_INSENSITIVE);
		PATTERNS[5] = Pattern.compile("[Á]", Pattern.CASE_INSENSITIVE);
		PATTERNS[6] = Pattern.compile("[Ò]", Pattern.CASE_INSENSITIVE);
	}	
	
	private static String unAccent(String text) {
		if(PATTERNS == null){
			compilePatterns();
		}
		String result = text;
		for (int i = 0; i < PATTERNS.length; i++) {
			Matcher matcher = PATTERNS[i].matcher(result);
			result = matcher.replaceAll(REPLACES[i]);
		}
		return result;
	}	
	
	
	private static String trataEmoticons(String frase){
		
		Iterator<String> it = ClassifierUtils.GOOD_EMOTICONS_LIST.iterator();
		
		while (it.hasNext()){
			String emoticon = it.next();
			frase = frase.replaceAll(emoticon, FELIZ);
		}
		

		it = ClassifierUtils.BAD_EMOTICONS_LIST.iterator();
		
		while (it.hasNext()){
			String emoticon = it.next();
			frase = frase.replaceAll(emoticon, TRISTE);
		}
		
		return frase;
	}
	
	private static String cleanText(String text) {
		return text.replace(":", "").replace(";", "").replace(")", "")
				.replace("(", "").replace("<", "").replace(">", "")
				.replace("{", "").replace("}", "").replace("[", "")
				.replace("]", "".replace(".", "").replace(",", "").replace("!", "").replace("?", ""));
	}	

	private static String realizaTratamentoFrase(String str){
		str = trataEmoticons(str);
		str = unAccent(str);
		str = removeConsecutivelyChars(str);
		str = cleanText(str);
		str = toLowerCase(str);

		
		return str;
	}
	
	
	private static String toLowerCase(String str){
		str = str.toLowerCase();
		return str;
	}	
	public static void obtemDadosFrases(String fileIn, String fileConf, String tipoDaFrase){
		

			ClassifierUtils.inicializaPalavrasConf(fileConf);
			
			
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(fileIn));
				String str;
				
				while (in.ready()) {
					
					FraseDados fraseDados = new FraseDados();
					
					str = in.readLine();
					str = realizaTratamentoFrase(str);
					fraseDados.setNumPalavrasAgradecimento(ClassifierUtils.contaNumAgradecimento(str));
					fraseDados.setNumPalavrasPrazer(ClassifierUtils.contaNumPrazer(str));
					fraseDados.setNumPalavrasPerda(ClassifierUtils.contaNumPerda(str));
					fraseDados.setNumPalavrasEuforia(ClassifierUtils.contaNumEuforia(str));
					fraseDados.setNumPalavrasSadWords(ClassifierUtils.contaNumSadWord(str));
					fraseDados.setNumPalavrasBadWords(ClassifierUtils.contaNumBadWord(str));
					fraseDados.setNumPalavrasArrependimento(ClassifierUtils.contaNumArrependimento(str));
					fraseDados.setNumPalavrasDor(ClassifierUtils.contaNumDor(str));
					fraseDados.setNumPalavrasNegativas(ClassifierUtils.contaNumNegativeWords(str));
					fraseDados.setNumPalavrasAfirmativas(ClassifierUtils.contaNumAfirmativeWords(str));
					fraseDados.setTipoDaFrase(tipoDaFrase);
					
					listaFraseDados.add(fraseDados);
					
					
				}
				in.close();
				
				
				
			} catch (FileNotFoundException e) {
				System.out.println("Arquivo ["+fileIn+"] n„o foi encontrado.");
			} catch (Exception e) {
				System.out.println("Falha ao tentar ler o arquivo informado ["+fileIn+"].");
			}
	}		


}

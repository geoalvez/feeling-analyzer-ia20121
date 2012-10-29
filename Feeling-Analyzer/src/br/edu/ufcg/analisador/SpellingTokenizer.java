package br.edu.ufcg.analisador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.core.tokenizers.WordTokenizer;

public class SpellingTokenizer extends WordTokenizer {
	private static final long serialVersionUID = 5347259299872662145L;

	private static final String[] SMILE_LIST = { ">:]", ":-)", ":)", ":o)",
			":]", ":3", ":c)", ":>", "=]", "8)", "=)", ":}", ":^", ">=]",
			"=-)", "=P", ":P", ":p", "=3", ":*)", "=*", "=X", ":X", "=B", "=F",
			":F", ">:D", ":-D", ":D", "8-D", "8D", "x-D", "xD", "X-D", "XD",
			"=-D", "=D", "=-3", "=3", ":'(", ";*(", "T.T", "T_T", "Y.Y", "Y_Y",
			">:[", ":-(", ":(", ":-c", ":c", ":-<", ":<", ":-[", ":[", ":{",
			">.>", "<.<", ">.<", ">;]", ";-)", ";)", "*-)", "*)", ";-]", ";]",
			";D", ">:P", ":-P", ":P", "X-P", "x-p", "xp", "XP", ":-p", ":p",
			"=p", ":-*", ":*", ":-b", ":b", ">:\\", ">:/", ":-/", ":-", ":/",
			":\\", "=/", "=\\", ":S", ":|", "=|", "=(", "*-*", ":O", ";o",
			";*", "=*", ";/", ";\\", "=S", "ç.ç", "\\o/", "/o\\", "/o/",
			"\\o\\", "<o>", "o/", "\\o", ":T", "=T", "=t", ":t" };

	private static final String IGNORED = "<IGNORADO421847824721>";

	public static String[] REPLACES = { "a", "e", "i", "o", "u", "c", "n" };

	public static Pattern[] PATTERNS;

	public SpellingTokenizer() {
		setDelimiters(" \r\n\t.,'\"?!#");
	}

	public Object nextElement() {
		Object next = super.nextElement();
		if (next instanceof String) {
			return process(next);
		}
		return next;
	}

	private Object process(Object next) {
		String text = (String) next;
		text = unAccent(text);
		text = removeConsecutivelyChars(text);
		if (isSmile(text)) {
			return text;
		}
		String cleanText = cleanText(text);
		if (cleanText.startsWith("@") || cleanText.startsWith("http")
				|| cleanText.equals("")) {
			return IGNORED;
		}
		return cleanText;
	}

	private String removeConsecutivelyChars(String text) {
		int end = text.length() - 1;
		while (end >= 1 && text.charAt(end - 1) == text.charAt(end)) {
			text = text.substring(0, end);
			end--;
		}
		return text;
	}

	public void compilePatterns() {
		PATTERNS = new Pattern[REPLACES.length];
		PATTERNS[0] = Pattern.compile("[âãáàä]", Pattern.CASE_INSENSITIVE);
		PATTERNS[1] = Pattern.compile("[éèêë]", Pattern.CASE_INSENSITIVE);
		PATTERNS[2] = Pattern.compile("[íìîï]", Pattern.CASE_INSENSITIVE);
		PATTERNS[3] = Pattern.compile("[óòôõö]", Pattern.CASE_INSENSITIVE);
		PATTERNS[4] = Pattern.compile("[úùûü]", Pattern.CASE_INSENSITIVE);
		PATTERNS[5] = Pattern.compile("[ç]", Pattern.CASE_INSENSITIVE);
		PATTERNS[6] = Pattern.compile("[ñ]", Pattern.CASE_INSENSITIVE);
	}

	public String unAccent(String text) {
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

	private boolean isSmile(String text) {
		String invertido = reverse(text);
		for (String smile : SMILE_LIST) {
			if (text.equalsIgnoreCase(smile)
					|| invertido.equalsIgnoreCase(smile)) {
				return true;
			}
		}
		return false;
	}

	private String reverse(String text) {
		StringBuilder sb = new StringBuilder(text);
		return sb.reverse().toString();
	}

	private String cleanText(String text) {
		return text.replace(":", "").replace(";", "").replace(")", "")
				.replace("(", "").replace("<", "").replace(">", "")
				.replace("{", "").replace("}", "").replace("[", "")
				.replace("]", "");
	}
}

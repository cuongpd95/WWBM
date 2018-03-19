/**
 * 
 */
package vn.uet.wwbm.question_answering.helpers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vn.corenlp.tokenizer.StringUtils;
import vn.pipeline.Word;

/**
 * @author "PhanDoanCuong"
 *
 */
public class DataCleaner {
	private List<String> stopWords;

	public DataCleaner() {
		stopWords = FileHelper.getStopWords();
	}

	public List<Word> cleanStopWordsAndPunctuation(List<Word> tokens) {
		List<Word> result = new ArrayList<Word>();
		for (Iterator iterator = tokens.iterator(); iterator.hasNext();) {
			Word word = (Word) iterator.next();
			if (!word.getPosTag().equalsIgnoreCase("ch")) {
				if (!StringUtils.isPunctuation(word.getForm())
						& !StringUtils.isBrace(word.getForm())) {
//					if (!isStopWord(word.getForm())) {
//						result.add(word);
//					}
					result.add(word);
				}
			}
		}
		return result;
	}

	/**
	 * @param form
	 * @return
	 */
	private boolean isStopWord(String form) {
		boolean result = false;
		for (Iterator iterator = stopWords.iterator(); iterator.hasNext();) {
			String stopword = (String) iterator.next();
			if (stopword.replace(" ", "_").equalsIgnoreCase(form)) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * @param tokens
	 * @return
	 */
	public List<Word> nerFilter(List<Word> tokens) {
		List<Word> result = new ArrayList<Word>();
		for (Iterator iterator = tokens.iterator(); iterator.hasNext();) {
			Word word = (Word) iterator.next();
			if (vn.uet.wwbm.question_answering.helpers.StringUtils.isEntity(word.getNerLabel())) {
				result.add(word);
			}
		}
		return result;
	}

	/**
	 * @param tokens
	 * @return
	 */
	public List<Word> filtNoun(List<Word> tokens) {
		List<Word> nouns = new ArrayList<Word>();
		for (Iterator iterator = tokens.iterator(); iterator.hasNext();) {
			Word word = (Word) iterator.next();
			if (word.getPosTag().startsWith("N") || word.getPosTag().startsWith("M")) {
				nouns.add(word);
			}
		}
		
		return nouns;
	}

}

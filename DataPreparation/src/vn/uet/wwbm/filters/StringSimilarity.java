/**
 * 
 */
package vn.uet.wwbm.filters;

import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.LongestCommonSubsequence;
import info.debatty.java.stringsimilarity.NGram;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author "PhanDoanCuong"
 *
 */
public class StringSimilarity {

	private static final String DELIMILATOR = "!|\\?|\\.|,|\\s|\"|'";
	private static StringSimilarity instance;

	private NormalizedLevenshtein normalizedLevenshtein;

	private LongestCommonSubsequence lcs;

	private NGram ngram;

	public static StringSimilarity getInstance() {
		if (null == instance) {
			instance = new StringSimilarity();
		}
		return instance;
	}

	private StringSimilarity() {
		normalizedLevenshtein = new NormalizedLevenshtein();
		lcs = new LongestCommonSubsequence();
		ngram = new NGram(4);
	}

	public double calculateLevenshteinSimilarity(String candidateAnswer,
			String title) {
		System.out.println(" x"
				+ normalizedLevenshtein.distance(candidateAnswer, title));
		Levenshtein levenshtein = new Levenshtein();
		System.out.println("y" + levenshtein.distance(candidateAnswer, title));
		return normalizedLevenshtein.similarity(candidateAnswer, title);
	}

	public double calculateExactSubsequence(String candidateAnswer,
			String passage) {
		int distance = lcs.length(candidateAnswer, passage);
		return ((double) distance) / candidateAnswer.length();
	}

	public double calculateOverlap(String candidateAnswer, String passage) {
		List<String> cAnswers = new ArrayList<String>(
				Arrays.asList(candidateAnswer.toLowerCase().split(DELIMILATOR)));
		int originLenth = cAnswers.size();
		List<String> intersection = new ArrayList<String>(Arrays.asList(passage
				.toLowerCase().split(DELIMILATOR)));
//		List<String> union = new ArrayList<String>(Arrays.asList(passage
//				.toLowerCase().split(DELIMILATOR)));

//		intersection.retainAll(cAnswers);
//		for (int i = 0; i < intersection.size(); i++) {
//			if (intersection.get(i).isEmpty()) {
//				intersection.remove(i);
//				i--;
//			}
//		}
//
//		union.addAll(cAnswers);
//		for (int i = 0; i < union.size(); i++) {
//			if (union.get(i).isEmpty()) {
//				union.remove(i);
//				i--;
//			}
//		}
//
//		if (intersection == null || intersection.isEmpty()) {
//			return 0;
//		} else {
//			System.out.println(intersection.size() + " " + cAnswers.size());
////			return ((double) intersection.size()) / union.size();
//			return ((double) intersection.size()) / cAnswers.size();
//		}
		
		cAnswers.retainAll(intersection);
		
		return ((double)cAnswers.size())/originLenth;
		
	}

	public int calculateLCS(String candidateAnswer, String passage) {
		return lcs.length(candidateAnswer, passage);
	}

	public double calculateNGram(String question, String passage) {
		return 1 - ngram.distance(question, passage);
	}

	public static void main(String[] args) {
		StringSimilarity s = new StringSimilarity();
		String question = "Khu vực nào được gọi là thủ đô kháng chiến?";
		String passage = "Việt Bắc được gọi một cách văn hoa là Thủ đô kháng chiến, bởi đây là nơi trú đóng của đầu não Đảng Cộng sản Việt Nam thời trước khi khởi nghĩa năm 1945, và là nơi trú đóng của đầu não chính phủ Việt Minh trong thời kỳ kháng chiến chống Pháp (1945 - 1954). Nó cũng được gọi là Thủ đô gió ngàn, tên gọi này được bắt nguồn từ bài thơ Sáng tháng năm của nhà thơ Tố Hữu";
//		String title = "Việt Bắc";
		System.out.println(s.calculateLCS(question, passage));
		System.out.println(s.calculateExactSubsequence(question, passage));
		// System.out.println(s.calculateLevenshteinSimilarity("Việt Bắc được",
		// title));
		// System.out.println(s.calculateNGram(question, passage));
		System.out.println(s.calculateOverlap(question, passage));
	}

}

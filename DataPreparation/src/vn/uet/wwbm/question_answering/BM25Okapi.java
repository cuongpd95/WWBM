/**
 * 
 */
package vn.uet.wwbm.question_answering;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;
import vn.pipeline.Word;
import vn.uet.wwbm.question_answering.db.FasterDBHelper;
import vn.uet.wwbm.question_answering.helpers.DataCleaner;

/**
 * The implementation of Okapi BM25 model is based on
 * https://nlp.stanford.edu/IR
 * -book/html/htmledition/okapi-bm25-a-non-binary-model-1.html This
 * implementation for long query
 * 
 * @author "PhanDoanCuong"
 *
 */
public class BM25Okapi {

	/** The constant k_1. */
	private double k_1 = 1.2d;

	/** The constant k_3. */
	private double k_3 = 8d;

	/** The parameter b. */
	private double b;

	private VnCoreNLP pipeline;

	private DataCleaner cleaner;

	private FasterDBHelper fasterDBHelper;

	int numberOfDocumnet;

	int totalWordInDataset;

	public BM25Okapi(double b) throws IOException, SQLException, InstantiationException, IllegalAccessException {
		String[] annotators = { "wseg", "pos", "ner" };
		pipeline = new VnCoreNLP(annotators);
		cleaner = new DataCleaner();
		this.b = b;
		fasterDBHelper = FasterDBHelper.getInstance();

		fasterDBHelper.connect();
		numberOfDocumnet = fasterDBHelper.getNumberOfDocument();
		// totalWordInDataset = fasterDBHelper.getDatasetLength();
		totalWordInDataset = fasterDBHelper.getDatasetLengthAll();
		fasterDBHelper.disconnect();
	}

	// public List<BM25Score> search(String query) throws IOException,
	// SQLException {
	// if (query == null || query.isEmpty()) {
	// throw new IOException("The query can not be empty!");
	// } else {
	// fasterDBHelper.connect();
	// List<BM25Score> scores = new ArrayList<BM25Score>();
	//
	// Annotation annotation = new Annotation(query);
	// pipeline.annotate(annotation);
	//
	// // Get word list
	// List<Word> tokens = annotation.getWords();
	// // Clean stop word and punction, brace
	// // tokens = cleaner.cleanStopWordsAndPunctuation(tokens);
	// tokens = cleaner.nerFilter(tokens);
	// System.out.println("get doc...");
	// List<Integer> relevantDoc = fasterDBHelper.getRelevantDocs(tokens);
	//
	// // Calculate bm25 score for each document
	// System.out.println("****************BM25 Scoring***********"
	// + relevantDoc.size());
	// int docLength;
	// if (!relevantDoc.isEmpty()) {
	// int length = relevantDoc.size();
	// for (int i = 0; i < length; i++) {
	// int termFrequencyInQuery;
	// docLength = fasterDBHelper.getDocLength(relevantDoc.get(i));
	// double score = 0;
	// int tf;
	//
	// int documentFrequency;
	// for (int j = 0; j < tokens.size(); j++) {
	// tf = fasterDBHelper.getTermFrequency(
	// relevantDoc.get(i), tokens.get(j).getForm());
	// if (tf > 0) {
	// documentFrequency = fasterDBHelper
	// .getDocumentFrequency(tokens.get(j)
	// .getForm());
	// // documentFrequency = 1;
	// termFrequencyInQuery = getTermFrequencyInQuery(
	// tokens.get(j).getForm(), tokens);
	// score += score(tf, numberOfDocumnet, docLength,
	// (double) (totalWordInDataset)
	// / numberOfDocumnet,
	// termFrequencyInQuery, documentFrequency);
	//
	// }
	//
	// }
	//
	// if (score > 0d) {
	// System.out.println(i + "-" + score);
	// scores.add(new BM25Score(relevantDoc.get(i).intValue(),
	// score));
	// }
	// }
	// }
	//
	// fasterDBHelper.disconnect();
	// return scores;
	// }

	// }

	public List<BM25Score> searchAllTerm(String query, String a, String b, String c, String d) throws IOException,
			SQLException, InstantiationException, IllegalAccessException {
		if (query == null || query.isEmpty()) {
			throw new IOException("The query can not be empty!");
		} else {
			fasterDBHelper.connect();
			List<BM25Score> scores = new ArrayList<BM25Score>();

			Annotation annotation = new Annotation(query);
			pipeline.annotate(annotation);
			
//			Annotation includeAnswer = new Annotation(query + " " + a + " " + b + " " + c + " " + d);
//			pipeline.annotate(includeAnswer);
//			List<Word> includeAnswerList = includeAnswer.getWords();
			
//			List<Word> includeAnswerList = annotation.getWords();
//			includeAnswerList = cleaner.cleanStopWordsAndPunctuation(includeAnswerList);

			// Get word list
			List<Word> tokens = annotation.getWords();
			// System.out.println(tokens.size());
			// Clean stop word and punction, brace
			tokens = cleaner.cleanStopWordsAndPunctuation(tokens);
			List<Word> ners = cleaner.nerFilter(tokens);
			List<Word> nouns = cleaner.filtNoun(tokens);

			List<Integer> tokensId = fasterDBHelper.getTermId(tokens);
			List<Integer> nounsId = fasterDBHelper.getTermId(nouns);
			List<Integer> nersId = fasterDBHelper.getTermId(ners);

//			List<Integer> relevantDoc = fasterDBHelper.getRelevantDocs(nersId);
//			if (relevantDoc.isEmpty()) {
//				relevantDoc = fasterDBHelper.getRelevantDocs(nounsId);
//			}
			
			List<Integer> relevantDoc = fasterDBHelper.getRelevantDocs(nounsId);

			// Calculate bm25 score for each document
			System.out.println("****************BM25 Scoring***********"
					+ relevantDoc.size());
			int docLength;
			if (!relevantDoc.isEmpty()) {
				int length = relevantDoc.size();
				for (int i = 0; i < length; i++) {
					if (i % 10000 == 0) {
						System.out.println(i);
					}
					int termFrequencyInQuery;
					// docLength =
					// fasterDBHelper.getDocLength(relevantDoc.get(i));
					docLength = fasterDBHelper.getDocLengthAll(relevantDoc
							.get(i));
					// docLength = 1;
					double score = 0;
					int tf;

					int documentFrequency;
					double scorei = 0;
					for (int j = 0; j < tokensId.size(); j++) {
						// tf = fasterDBHelper.getTermFrequency(
						// relevantDoc.get(i), tokens.get(j).getForm());

						tf = fasterDBHelper.getTermFrequencyAll(
								relevantDoc.get(i), tokensId.get(j).intValue());
						// tf = 1;

						if (tf > 0) {
							// documentFrequency = fasterDBHelper
							// .getDocumentFrequency(tokens.get(j)
							// .getForm());
							documentFrequency = fasterDBHelper
									.getDocumentFrequencyAll(tokensId.get(j)
											.intValue());
							// documentFrequency = 1;
							termFrequencyInQuery = getTermFrequencyInQuery(
									tokensId.get(j).intValue(), tokensId);
							scorei = score(tf, numberOfDocumnet, docLength,
									(double) (totalWordInDataset)
											/ numberOfDocumnet,
									termFrequencyInQuery, documentFrequency);
							// threshold = 0
							if (scorei > 0) {
								score += scorei;
							}
						}

					}

					if (score > 0d) {
						// System.out.println(i + "-" + score);
						scores.add(new BM25Score(relevantDoc.get(i).intValue(),
								score));
					}
				}
			}

			fasterDBHelper.disconnect();
			return scores;
		}

	}

	/**
	 * @param form
	 * @param tokens
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private int getTermFrequencyInQuery(int term_id, List<Integer> tokens) {
		int count = 0;
		for (Iterator iterator = tokens.iterator(); iterator.hasNext();) {
			Integer word = (Integer) iterator.next();
			if (word.intValue() == term_id) {
				count++;
			}
		}
		return count;
	}

	private final double score(int tf, int numberOfDocuments, int docLength,
			double averageDocumentLength, int termFrequencyInQuery,
			int documentFrequency) {

		double K = k_1 * ((1 - b) + ((b * docLength) / averageDocumentLength));
		double weight = (((k_1 + 1d) * tf) / (K + tf)); // first part
		weight = weight
				* (((k_3 + 1) * termFrequencyInQuery) / (k_3 + termFrequencyInQuery)); // second
																						// part

		// multiply the weight with idf
		double idf = weight
				* Math.log((numberOfDocuments - documentFrequency + 0.5d)
						/ (documentFrequency + 0.5d));
		return idf;
	}

}

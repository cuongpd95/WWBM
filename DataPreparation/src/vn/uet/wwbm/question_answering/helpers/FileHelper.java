/**
 * 
 */
package vn.uet.wwbm.question_answering.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vn.uet.wwbm.answer_scoring.containts.CandidateAnswerCode;
import vn.uet.wwbm.main.entities.Question;
import vn.uet.wwbm.models.PassageFeature;
import vn.uet.wwbm.question_answering.BM25Score;
import vn.uet.wwbm.question_answering.entities.Passage;

/**
 * @author "PhanDoanCuong"
 *
 */
public class FileHelper {

	public static List<String> getStopWords() {
		List<String> stopwords = new ArrayList<String>();
		try {
			String line;
			BufferedReader bufferreader = new BufferedReader(new FileReader(
					"src/vietnamese-stopwords.txt"));
			line = bufferreader.readLine();
			while (line != null) {
				// do whatever here
				if (line == "") {
					System.out.println("Null");
				}
				stopwords.add(line.trim());
				line = bufferreader.readLine();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return stopwords;
	}

	public static void writeBM25Score(String query, List<BM25Score> scores,
			String filePath) throws IOException {
		StringBuilder data = new StringBuilder(query + "\n");
		for (Iterator iterator = scores.iterator(); iterator.hasNext();) {
			BM25Score bm25Score = (BM25Score) iterator.next();
			data.append(bm25Score.docId + "," + bm25Score.getScore() + "\n");
		}

		File file = new File(filePath);

		// creates the file
		file.createNewFile();

		// creates a FileWriter Object
		FileWriter writer = new FileWriter(file);

		// Writes the content to the file
		writer.write(data.toString());
		writer.flush();
		writer.close();

	}

	/**
	 * @param choices
	 * @throws IOException
	 */
	public static void writeAnswer(List<Integer> choices,List<Question> questions, String filePath)
			throws IOException {
		StringBuilder data = new StringBuilder();
		int flag;
		for (int i = 0; i < choices.size(); i++) {
			if (choices.get(i).intValue() == questions.get(i).getRightAnswer()) {
				flag = 1;
			}
			else {
				flag = 0;
			}
			data.append(decodeChoice(choices.get(i).intValue()) + "," + decodeChoice(questions.get(i).getRightAnswer()) + "," + flag +"\n");
		}

		File file = new File(filePath);

		// creates the file
		file.createNewFile();

		// creates a FileWriter Object
		FileWriter writer = new FileWriter(file);

		// Writes the content to the file
		writer.write(data.toString());
//		writer.flush();
		writer.close();
	}

	/**
	 * @param intValue
	 * @return
	 */
	private static String decodeChoice(int intValue) {
		String ans = null;
		switch (intValue) {

		case CandidateAnswerCode.A:
			ans = "A";
			break;
		case CandidateAnswerCode.B:
			ans = "B";
			break;
		case CandidateAnswerCode.C:
			ans = "C";
			break;
		case CandidateAnswerCode.D:
			ans = "D";
			break;

		default:
			break;
		}
		return ans;
	}

	/**
	 * @param string
	 * @return
	 */
	public static List<Question> readData(String filePath) {
		List<Question> questions = new ArrayList<Question>();
		try {
			String line;
			BufferedReader bufferreader = new BufferedReader(new FileReader(
					filePath));
			line = bufferreader.readLine();
			String[] q = new String[6];
			while (line != null) {
				q = line.trim().split("_");
				questions.add(new Question(q[0], q[1], q[2], q[3], q[4], Integer.parseInt(q[5])));
				line = bufferreader.readLine();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return questions;
	}

	/**
	 * @param filePath
	 * @param data
	 * @throws IOException 
	 */
	public static void writeFile(String filePath, String data) throws IOException {

		File file = new File(filePath);

		// creates the file
		file.createNewFile();

		// creates a FileWriter Object
		FileWriter writer = new FileWriter(file);

		// Writes the content to the file
		writer.write(data);
//		writer.flush();
		writer.close();
	}

	/**
	 * @param question
	 * @param passages
	 * @throws IOException 
	 */
	public static void writePassageExtraction(String filePath, String question,
			List<PassageFeature> passages) throws IOException {
		StringBuilder data = new StringBuilder(question);
		data.append("\nTitle\tPassage\tbm25core\ttes\tlcs\tov");
		for (int i = 0; i < passages.size(); i++) {
			data.append("\n" + passages.get(i).getTitle() + "\t" + passages.get(i).getPassage()+ "\t" + passages.get(i).getBm25score() + "\t" + passages.get(i).getEs()+ "\t" + passages.get(i).getLcs()+ "\t" + passages.get(i).getOv());
		}
		writeFile(filePath, data.toString());
	}

	public static List<Passage> readPassages(int startId, String path) {
		List<Passage> psgs = new ArrayList<Passage>();
		try {
			String line;
			BufferedReader bufferreader = new BufferedReader(new FileReader(
					path));
			line = bufferreader.readLine();
			String[] q = new String[6];
			int rightAns;
			int count = 1;
			while (line != null) {
				q = line.split("\t");
				System.out.println(line);
				psgs.add(new Passage(startId + count, q[0], q[1]));
				line = bufferreader.readLine();
				count ++;
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		return psgs;
	}

	public static void insertPassages(List<Passage> psgs) {
		// TODO Auto-generated method stub
		
	}

}

/**
 * 
 */
package vn.uet.wwbm.question_answering.helpers;
/**
 * @author "PhanDoanCuong"
 *
 */
public class StringUtils {
	
	private static String[] punctuation = {"?", "!", "/", ";", ":", ".", ",", "\"", "'"};
	public static boolean notCHWord(String pos){
		if ("ch".equalsIgnoreCase(pos.trim())) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * @param nerLabel
	 * @return
	 */
	public static boolean isEntity(String nerLabel) {
		if (nerLabel == null || nerLabel.isEmpty()) {
			return false;
		}
		else if ("o".equalsIgnoreCase(nerLabel.trim())) {
			return false;
		}
		else
			return true;
	}

	/**
	 * @param replace
	 * @return
	 */
	public static boolean isWord(String term) {
		boolean result = false;
		if (notIn(term)) {
			result = true;
		}
		return result;
	}
	
	private static boolean notIn(String term){
		boolean result = true;
		for (int i = 0; i < punctuation.length; i++) {
			if (punctuation[i].equalsIgnoreCase(term)) {
				result = false;
				break;
			}
		}
		return result;
	}

}

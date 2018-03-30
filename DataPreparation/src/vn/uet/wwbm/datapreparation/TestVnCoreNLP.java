/**
 * 
 */
package vn.uet.wwbm.datapreparation;

import java.io.IOException;

import vn.pipeline.Annotation;
import vn.pipeline.VnCoreNLP;

/**
 * @author "PhanDoanCuong"
 *
 */
public class TestVnCoreNLP {
	public static void main(String[] args) throws IOException {
        
        // "wseg", "pos", "ner", and "parse" refer to as word segmentation, POS tagging, NER and dependency parsing, respectively. 
        String[] annotators = {"wseg", "pos", "ner"}; 
        VnCoreNLP pipeline = new VnCoreNLP(annotators); 
    
        String str = "Khi đến nơi, Thủ Độ vặn hỏi trước mặt, người quân hiệu ấy cứ theo sự thực trả lời"; 
        Annotation annotation = new Annotation(str); 
        pipeline.annotate(annotation); 
        
        System.out.println(annotation.toString());
        
        
    
//        String str = "Lượng choán nước lớn nhất: 2,8m3"; 
//        String query = "Tập hợp các số thực được ký hiệu bằng chữ cái nào";
//        String query = "Giải Grand Slam đầu tiên trong năm là giải nào?";
//        String query = "An Dương Vương đặt quốc hiệu nước ta là gì?";
//        String query = "Năm 1010, Lý Thái Tổ đã cho xây dựng điện nào ở trung tâm hoàng thành Thăng Long";
//        
//        Annotation annotation = new Annotation(query); 
//        pipeline.annotate(annotation); 
//        System.out.println(annotation.toString());
//        List<Word> tokens = annotation.getWords();
        
//        for (Iterator iterator = tokens.iterator(); iterator.hasNext();) {
//			Word string = (Word) iterator.next();
//			System.out.println(string.getNerLabel());
//		}
        
//        System.out.println(annotation.toString());
//        
//        //Write to file
//        PrintStream outputPrinter = new PrintStream("output.txt", "UTF-8");
//        pipeline.printToFile(annotation, outputPrinter); 
//    
//        // You can also get a single sentence to analyze individually 
//        Sentence firstSentence = annotation.getSentences().get(0);
//        System.out.println(firstSentence.toString());
	}

}

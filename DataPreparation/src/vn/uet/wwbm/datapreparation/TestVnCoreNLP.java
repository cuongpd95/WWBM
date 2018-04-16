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
        String[] annotators = {"wseg", "pos", "ner", "parse"}; 
        VnCoreNLP pipeline = new VnCoreNLP(annotators); 
    
        String str ="Khởi nghĩa của hai Bà Trưng đánh bại quân xâm lược nào?"; 
        String str1 = "Sau khi lên làm vua, Lý Bí đặt quốc hiệu nước ta là gì?";
        String str2 = "Ai là người đã lãnh đạo cuộc khởi nghĩa đánh đuổi quân xâm lược nhà Đường năm 722?";
        String str3 = "Thành phố nào được mệnh danh là 'Thủ đô ngàn gió'";
        String str4 = "1867 ÷ 1874 Pháp tiếp tục xâm lấn ba tỉnh miền Tây Nam kỳ và tiến tới chiếm đóng toàn bộ Nam kỳ";
        String str5 = "Bắc thuộc	Bắc thuộc lần thứ ba (602 - 905): nhà Tùy, nhà Đường. Trong giai đoạn Tự chủ từ 905-938 có một thời gian Việt Nam rơi vào tay Nam Hán.";
        String str6 = "Lý Nam Đế (chữ Hán: 李南帝; 503–548), húy là Lý Bí hoặc Lý Bôn (李賁) (xem mục Tên gọi bên dưới), là vị vua đầu tiên của nhà Tiền Lý và nước Vạn Xuân.";
        String str7 = "Cô ấy đưa cho tôi một chiếc bút.";
        String str8 = "Vị vua đầu tiên của nhà nước Liên Xô là ai?";
        Annotation annotation = new Annotation(str8); 
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

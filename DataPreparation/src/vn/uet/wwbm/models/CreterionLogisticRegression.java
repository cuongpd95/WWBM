/**
 * 
 */
package vn.uet.wwbm.models;

import java.io.IOException;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.CSVLoader;

/**
 * @author "PhanDoanCuong"
 *
 */
public class CreterionLogisticRegression {
	/** file names are defined*/
	public static final String TRAINING_DATA_SET_FILENAME="tranindD.csv";
	public static final String MODEL_PATH = "bin/vn/uet/wwbm/models/logisticRegression.model";
	public static final String DATASET_PATH = "bin/vn/uet/wwbm/models/question_anwering.dataset";

	/**
	 * This method is to load the data set.
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Instances getDataSet(String fileName) throws IOException {
		/**
		 * we can set the file i.e., loader.setFile("finename") to load the data
		 */
		int classIdx = 3;
		/** the arffloader to load the arff file */
//		ArffLoader loader = new ArffLoader();
		CSVLoader loader = new CSVLoader();
		//loader.setFile(new File(fileName));
		/** load the traing data */
//		loader.setSource(CreterionLogisticRegression.class.getResourceAsStream(fileName));
		loader.setSource(CreterionLogisticRegression.class.getResourceAsStream(fileName));
		/**
		 * we can also set the file like loader3.setFile(new
		 * File("test-confused.arff"));
		 */
		Instances dataSet = loader.getDataSet();
		/** set the index based on the data given in the arff files */
		dataSet.setClassIndex(classIdx);
		return dataSet;
	}

	/**
	 * This method is used to process the input and return the statistics.
	 * 
	 * @throws Exception
	 */
	public static void process() throws Exception {

		Instances trainingDataSet = getDataSet(TRAINING_DATA_SET_FILENAME);
//		Instances testingDataSet = getDataSet(TESTING_DATA_SET_FILENAME);
		/** Classifier here is Linear Regression */
		Classifier classifier = new weka.classifiers.functions.Logistic();
		/** */
		classifier.buildClassifier(trainingDataSet);
		
		SerializationHelper.write("bin/vn/uet/wwbm/models/logisticRegression.model", classifier);
		
		/**
		 * train the alogorithm with the training data and evaluate the
		 * algorithm with testing data
		 */
//		Evaluation eval = new Evaluation(trainingDataSet);
//		eval.evaluateModel(classifier, testingDataSet);
//		/** Print the algorithm summary */
//		System.out.println("** Linear Regression Evaluation with Datasets **");
//		System.out.println(eval.toSummaryString());
//		System.out.print(" the expression for the input data as per alogorithm is ");
//		System.out.println(classifier);
//
//		Instance predicationDataSet = getDataSet(PREDICTION_DATA_SET_FILENAME).lastInstance();
//		double value = classifier.classifyInstance(predicationDataSet);
//		/** Prediction Output */
//		System.out.println(value);
	}
	
	public static Classifier loadModel() throws Exception{
		return (Classifier) weka.core.SerializationHelper.read(MODEL_PATH);
	}
	
	public static Instances loadDataSet() throws Exception{
		return (Instances) weka.core.SerializationHelper.read(DATASET_PATH);
	}
	
	public static void writeDataset() throws Exception{
		Instances dataSet = getDataSet(TRAINING_DATA_SET_FILENAME);
		SerializationHelper.write(DATASET_PATH, dataSet);
	}
	
	public static void main(String[] args) throws Exception {
		
		Instance instance = new Instance(3);
//		25.45675938	0.752941176	0.666666667
		instance.setValue(0, 24.40214711);
		instance.setValue(1, 0.847058824);
		instance.setValue(2, 0.80952381);

//		instance.setValue(0, 23.05431324);
//		instance.setValue(1, 0.821917808);
//		instance.setValue(2, 0.722222222);
//		instance.setValue(0, 12.47429266);
//		instance.setValue(1, 0.506849315);
//		instance.setValue(2, 0.277777778);
		
//		Instances trainingDataSet = getDataSet(TRAINING_DATA_SET_FILENAME);
//		instance.setDataset(trainingDataSet);
//		
//		Logistic classifier = (Logistic) CreterionLogisticRegression.loadModel();
//		
//		System.out.println(classifier.classifyInstance(instance));
//		double[] value = classifier.distributionForInstance(instance);
//		
//		for (int i = 0; i < value.length; i++) {
//			System.out.println(value[i]);
//		}

//		CreterionLogisticRegression.process();
		
		CreterionLogisticRegression.writeDataset();
	}
	
	

}

package ch.supsi.omega.dll;

public class DLLTester
{
	public static void main(String[] args)
	{
		testTrain();
		testSegment();
	}

	private static void testTrain()
	{
		String trajectoriesDir 	= "C:\\Users\\galliva\\Desktop\\tests\\from-max\\tracks_train";
		String labelsFile 		= "C:\\Users\\galliva\\Desktop\\tests\\from-max\\label.dat";
		String parameterDir 		= "C:\\Users\\galliva\\Desktop\\tests\\from-max\\parameters";
		String patternOutDir 	= "C:\\Users\\galliva\\Desktop\\tests\\from-max\\patterns_out";

		TSCaller.callTrainSVM(trajectoriesDir, labelsFile, parameterDir, patternOutDir);
	}

	private static void testSegment()
	{
		String trajectoriesDir 	= "C:\\Users\\galliva\\Desktop\\tests\\from-max\\tracks_train";
		String patternDir 		= "C:\\Users\\galliva\\Desktop\\tests\\from-max\\patterns_out";
		String resultDir 			= "C:\\Users\\galliva\\Desktop\\tests\\from-max\\segmentation_out";

		TSCaller.callSegment(trajectoriesDir, patternDir, resultDir);
	}

}
package au.edu.usyd.it.siftClusteringKmeans;
import java.io.*;
import java.util.*;



import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class LocalFeatureKmeans 
{
	static final int NUMCLUSTERS = 2;
	static final String DIR = "/Users/natechen/Desktop/2/2/";
	static final String filename = "/Users/natechen/Desktop/2/2/siftAll.arff";
	static final String CODEWORDFILE = "";
	
	
	public static void main(String[] args) 
	{
		PrintWriter histo;
		int[] distribution = new int[NUMCLUSTERS]; 
		try
		{
			/*
			 * histo is used to write .arff file 
			 * 
			 * about distribution of each shot based on 'code-word'
			 * 
			 * */
			histo = new PrintWriter(DIR + "siftHisto.arff");
			histo.println("@relation SIFTHistogram");
			for(int i = 0; i < 3 * NUMCLUSTERS; i++)
			{
				histo.println("@attribute a"+ i + " real");
			}
			histo.println("@data");
			
			SimpleKMeans sk = new SimpleKMeans();
			DataSource source = new DataSource(filename);
			Instances instances = source.getDataSet();
			
			sk.setNumClusters(NUMCLUSTERS);
			sk.buildClusterer(instances);
			/*
			 * code-word
			 * 
			 * */
			Instances ins = sk.getClusterCentroids();
			/*
			 * put "code-word" into a NUMCLUSTERS * 128 array
			 * 
			 * */
			double[][] array = new double[NUMCLUSTERS][128];
			for(int i = 0; i < ins.numInstances(); i++)
			{
				Instance in = ins.instance(i);
				
				double[] temp = in.toDoubleArray();
				for(int n = 0; n < 128; n++)
				{
					array[i][n] = temp[n];
				} 
			}
			/*
			 * read sift info from files
			 * 
			 * */
			try
			{
				File dir = new File(DIR);
				int totalNumOfFrames = 0;
				
				for(String str: dir.list())
				{
					/*
					 * totalNumOfFrames: assume it can be divided by 3; best case
					 * 
					 * */
					if(str.endsWith(".jpg_sift.txt"))
					{
						totalNumOfFrames ++;
					}
				}
				Matrix mat = new Matrix(NUMCLUSTERS, totalNumOfFrames);
				double[][] matrix = mat.getMatrix();
				int lineNum = 0;
				/*
				 * process one shot's sift file together
				 * 
				 * */
				for(String str : dir.list())
				{
					if(str.endsWith("_1.jpg_sift.txt"))
					{
						String res = "";
						/*
						 * initial a double array to store sift code-word distribution for one shot
						 * */
						double[] distri = new double[NUMCLUSTERS * 3];
						for(int i = 0; i < NUMCLUSTERS * 3; i++)
						{
							distri[i] = 0;
						}
						/*
						 * process three frames in one shot one by one
						 * 
						 * */
						for(int f = 1; f <= 3; f++)
						{
							String temp = str.substring(0, str.lastIndexOf("_1.jpg_sift.txt")) + "_" + f + ".jpg_sift.txt";
							/*
							 * put the distribution info to an array
							 * */
							double[] tempArr = calDistribution( temp,  array);
							for(int i = (f-1) * NUMCLUSTERS; i < f * NUMCLUSTERS; i++)
							{
								distri[i] = tempArr[i-(f-1) * NUMCLUSTERS];
							}
						}
						for(int line = lineNum; line < lineNum + 3; line++)
						{
							for(int i = 0; i < NUMCLUSTERS; i++)
							{
//								java.lang.ArrayIndexOutOfBoundsException: 6
//								at au.edu.usyd.it.siftClusteringKmeans.LocalFeatureKmeans.main(LocalFeatureKmeans.java:109)

								matrix[i][line] = distri[(line-lineNum) * NUMCLUSTERS + i];
							}
						}
						lineNum = lineNum + 3;
//						for(double d : distri)
//						{
//							res = res + d + ",";
//						}
//						System.out.println(res);
//						histo.append(res.substring(0, res.lastIndexOf(",")) + "\n");
					}
				}
				LocalFeatureKmeans lfk = new LocalFeatureKmeans();
				double[][] score = lfk.scoreMatrix(matrix, totalNumOfFrames, NUMCLUSTERS);
				String result = "";
				for(int i = 0; i < totalNumOfFrames; i ++)
				{
					for(int j = 0; j < NUMCLUSTERS; j++)
					{
						result = result + score[j][i] + ",";
					}
					if((i+1) % 3 == 0)
					{
						histo.append(result.substring(0,  result.lastIndexOf(",")) + "\n");
						result = "";
					}
					System.out.println();
				}
				histo.close();
				SimpleKMeans k = new SimpleKMeans();
				k.setDistanceFunction(new CosineSimilarity());
				DataSource src = new DataSource(filename);
				Instances in = src.getDataSet();
				k.setNumClusters(NUMCLUSTERS);
				k.buildClusterer(in);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static double calDistance(double[] sift, double[] ins)
	{
		double result = 0;
		for(int i = 0; i < 128; i ++)
		{
			result = result + Math.pow((sift[i] - ins[i]), 2);
		}
		return result;
	}
	private static double[] stringToDoubleArray(String result)
	{
		double[] arr = new double[128];
		String[] string = result.split(",");
		for(int i = 0; i < 128; i++)
		{
			arr[i] = Double.parseDouble(string[i]);
		}
		return arr;
	}
	
	/*
	 * input:
	 * @param: temp -> .sift filename
	 * @param: array -> code word
	 * 
	 * output:
	 * @param: double[] result -> feature distribution for one frame
	 * */
	private static double[] calDistribution(String temp, double[][] array)
	{
		double[] distribution = new double[NUMCLUSTERS];
		double[] result = new double[NUMCLUSTERS];
		for(int i = 0; i < NUMCLUSTERS; i++)
		{
			distribution[i] = 0;
		}
		try
		{
			File file = new File(DIR + temp);
			Scanner scan = new Scanner(file);
			/*
			 * number of features in one frame
			 * 
			 * */
			int total = scan.nextInt();
			scan.nextLine();
			int lineNum = 1;
			double[] arr = new double[128];
			String record = "";
			double min = Double.MAX_VALUE;
			int index = NUMCLUSTERS + 1;
			/*
			 * read a .sift file
			 * 
			 * */
			while(scan.hasNext())
			{
				String line = scan.nextLine();
				if((lineNum -1) % 8 == 0)
				{
					/*
					 * process one feature
					 * 
					 * */
					if(lineNum != 1)
					{
						arr = stringToDoubleArray(record.trim());
						for(int i = 0; i < NUMCLUSTERS; i++)
						{
							double[] dou = new double[128];
							for(int n = 0; n < 128; n++)
							{
								dou[n] = array[i][n];
							}
							double distance = calDistance(arr, dou);
							/*
							 * see which code-word this feature belongs to
							 * 
							 * */
							if(distance < min)
							{
								min = distance;
								index = i;
							}
						}
						distribution[index] ++;
					}
				}
				else
				{
					record = record + line;
				}
				lineNum++;
			}
			for(int i = 0; i < NUMCLUSTERS; i++)
			{
				result[i] = distribution[i];
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * The number of features in this feature doc belongs to a specific cluster / total number of features in this feature doc
	 *
	 * 
	 * */
	
	private double calTF(int codewordIndex, int docIndex, int totalFeat, double[][] ma)
	{
		return (ma[codewordIndex][docIndex] / totalFeat);
	}
	
	private double calIdf(int codewordIndex, double[][] matrix, int numOfDocs)
	{
		int numContain = 0;
		for(int i = 0; i < numOfDocs; i++)
		{
			if(matrix[codewordIndex][i] != 0)
			{
				numContain ++;
			}
		}
		return Math.log(numOfDocs/numContain + 1);
	}
	
	private double[][] scoreMatrix(double[][] matrix, int numOfDocs, int numOfCols)
	{
		double[][] score = new double[numOfCols][numOfDocs];
		int[] total = new int[numOfDocs];
		for(int i = 0; i < numOfDocs; i++)
		{
			total[i] = 0;
		}
		
		/*
		 * calculate the total number of features for one doc
		 * 
		 * */
		for(int row = 0; row < numOfDocs; row++)
		{
			for(int col = 0; col < numOfCols; col++)
			{
				total[row] = (int) (total[row] + matrix[col][row]);
			}
		}
		
		for(int col = 0; col < numOfCols; col++)
		{
			for(int row = 0; row < numOfDocs; row++)
			{
				score[col][row] = calTF(col, row, total[row], matrix) * calIdf(col, matrix, numOfDocs);
			}
		}
		
		return score;
	}
	
	
	
}

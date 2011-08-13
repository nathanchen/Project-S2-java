package au.edu.usyd.it.globalFeatureClusteringKmeans;

import java.io.File;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/*
 * perform kmeans on Global features using Weka api
 * manually change from .arff to .cedd 
 * 
 * 
 * */
public class GlobalFeatureKmeans 
{
	/*
	 *  -N <num>
	 *  number of clusters.(default 2).
	 *  
	 *  -V
	 *  Display std. deviations for centroids.
	 *  
	 *  -M
	 *  Replace missing values with mean/mode.
	 *  
	 *  -S <num>
	 *  Random number seed. (default 10)
	 *  
	 *  -A <classname and options>
	 *  Distance function to be used for instance comparison (default weka.core.EuclidianDistance)
	 *  
	 *  -I <num>
	 *  Maximum number of iterations. 
	 *  
	 *  -O
	 *  Preserve order of instances.
	 * */
	static final int NUMCLUSTERS = 6;
	static final String filename = "/Users/natechen/Desktop/keyframe/globalFeatures/ceddAll.arff";
	static final String DIR = "/Users/natechen/Desktop/keyframe/globalFeatures/";
	static final String LOGFILE = "/Users/natechen/Desktop/keyframe/globalFeatures/ceddLog.txt";
	/*
	 * input:
	 * @param: ceddAll.arff or fcthAll.arff
	 * @param: logfile, which indicates the order of data in the ceddAll/fcth.arff file
	 * output:
	 * 
	 * move the original _0.cedd files or _0.fcth files to new folders, which represents each cluster
	 * (delete the original files)
	 * */
	public static void main(String[] args) 
	{
		SimpleKMeans sk = new SimpleKMeans();
		try
		{
			
			/*
			 * Create new folders to store final results
			 * 
			 * */
			for(int i = 1; i <=NUMCLUSTERS; i++)
			{
				File file = new File(DIR + "cluster " + i);
				if(!file.exists())
				{
					file.mkdir();
				}
			}
			
			
			DataSource source = new DataSource(filename);
			Instances instances = source.getDataSet();
			
			sk.setPreserveInstancesOrder(true);
			sk.setNumClusters(NUMCLUSTERS);
			sk.buildClusterer(instances);
			System.out.println(sk.toString());
			int[] arr = sk.getAssignments();
			File log = new File(LOGFILE);
			Scanner scn = new Scanner(log);
			int n = 0;
			while(scn.hasNext())
			{
				String fileName = scn.nextLine();
				File file = new File(DIR + fileName);
				File des = new File(DIR + "cluster " + arr[n]);
				FileUtils.moveFileToDirectory(file, des, true);
				n++;
			}
			for(int i : sk.getAssignments())
			{
				System.out.println(i);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

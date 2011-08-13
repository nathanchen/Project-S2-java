package au.edu.usyd.it.globalFeatureClusteringKmeans;
import java.io.*;
import java.util.*;

/*
 * 将csv格式文件拼接，转化为.arff格式文件
 * 从而让weka能识别，进行kmeans操作
 * 并且保留一个log文件，纪录arff里没行数据对应的文件名
 * 
 * 
 * */

public class GlobalFeatureKmeansPreprocessing 
{
	static final String fileDir = "/Users/natechen/Desktop/keyframe/globalFeatures/";
	/*
	 * convert csv-style files to .arff files
	 * 
	 * */
	public static void main(String[] args) 
	{
		File dir = new File(fileDir);
		PrintWriter ceddlogWriter, fcthlogWriter, ceddWriter, fcthWriter;
		try
		{
			File ceddLog = new File(fileDir + "ceddLog.txt");
			File fcthLog = new File(fileDir + "fcthLog.txt");
			ceddlogWriter = new PrintWriter(ceddLog);
			fcthlogWriter = new PrintWriter(fcthLog);
			
			File ceddAll = new File(fileDir + "ceddAll.arff");
			File fcthAll = new File(fileDir + "fcthAll.arff");
			ceddWriter = new PrintWriter(ceddAll);
			fcthWriter = new PrintWriter(fcthAll);
			
			ceddWriter.println("@relation CEDD");
			for(int i =0; i < 144 * 3; i++)
			{
				ceddWriter.println("@attribute a"+ i + " real");
			}
			ceddWriter.println("@data");
			fcthWriter.println("@relation FCTH");
			for(int i =0; i < 192 * 3; i++)
			{
				fcthWriter.println("@attribute a"+ i + " real");
			}
			fcthWriter.println("@data");
			
			int ceddNum = 1;
			int fcthNum = 1;
			
			for(String str : dir.list())
			{
				if(str.endsWith("0.cedd"))
				{
					File cedd = new File(fileDir + str);
					Scanner scn = new Scanner(cedd);
					String line = scn.nextLine();
					ceddWriter.append("\n" + line);
					ceddlogWriter.append(str + "\n");
				}
				else if(str.endsWith("0.fcth"))
				{
					File fcth = new File(fileDir + str);
					Scanner scn = new Scanner(fcth);
					String line = scn.nextLine();
					fcthWriter.append("\n" + line );
					fcthlogWriter.println(str + "\n");
				}
			}
			ceddWriter.close();
			ceddlogWriter.close();
			fcthWriter.close();
			fcthlogWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
}

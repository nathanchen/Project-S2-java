package au.edu.usyd.it.siftClusteringKmeans;
import java.util.*;
import java.io.*;


/*
 * Combine all sift.txt file and convert it to .arff format
 * 
 * */
public class LocalFeatureKmeansPreprocessing 
{
	static final String fileDir = "/Users/natechen/Desktop/2/2/";
	
	public static void main(String[] args) 
	{
		File dir = new File(fileDir);
		PrintWriter siftWriter;
		try
		{
			siftWriter = new PrintWriter(fileDir + "siftAll.arff");
			siftWriter.println("@relation SIFT");
			for(int i = 0; i < 128; i++)
			{
				siftWriter.println("@attribute a"+ i + " real");
			}
			siftWriter.println("@data");
			int totalfeat = 0;
			for(String str : dir.list())
			{
				if(str.endsWith("_sift.txt"))
				{
					try
					{
						File sift = new File(fileDir + str);
						Scanner scan = new Scanner(sift);
						totalfeat = totalfeat + scan.nextInt();
						scan.nextLine();
						int lineNum = 0;
						while(scan.hasNext())
						{
							String line = scan.nextLine();
							if(lineNum % 8 == 0)
							{
								siftWriter.append("\n");
							}
							else if((lineNum+1) % 8 == 0)
							{
								siftWriter.append(line.substring(0, line.lastIndexOf(",")));
							}
							else
							{
								siftWriter.append(line);
							}
							lineNum++;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			siftWriter.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

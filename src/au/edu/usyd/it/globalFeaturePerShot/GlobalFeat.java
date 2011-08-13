package au.edu.usyd.it.globalFeaturePerShot;
import java.io.*;
import java.util.*;

/*
 *	对fileDir目录下的所有.cedd和.fcth文件进行综合
 *	一个shot产生一个总的.cedd和一个.fcth文件
 * 	文件的格式遵从.csv文件格式
 * 
 * 
 * */

public class GlobalFeat 
{

	static final String fileDir = "/Users/natechen/Desktop/keyframe/globalFeatures/";
	static final int CEDDLENGTH = 146;
	static final int FCTHLENGTH = 194;
	public static void main(String[] args) 
	{
		/*
		 * Combine three .cedd files together for each shot
		 * 
		 * numbers are seperated by ",". NO SPACE
		 * 
		 * */
		try
		{
			File dir = new File(fileDir);
			for(String str : dir.list())
			{
				if(str.endsWith("_1.cedd"))
				{
					int index = str.lastIndexOf("_1.cedd");
					String output = str.substring(0, index) + "_0.cedd";
					String f2 = str.substring(0, index) + "_2.cedd";
					String f3 = str.substring(0, index) + "_3.cedd";
					
					String fc1 = readFile((fileDir + str), CEDDLENGTH);
					String fc2 = readFile((fileDir + f2), CEDDLENGTH);
					String fc3 = readFile((fileDir + f3), CEDDLENGTH);
					
					File out = new File(fileDir + output);
					PrintWriter pw = new PrintWriter(out);
					pw.write(fc1.trim() + "," + fc2.trim() + "," + fc3.trim().substring(0, fc3.lastIndexOf(",")));
					pw.close();
				}
				else if(str.endsWith("_1.fcth"))
				{
					int index = str.lastIndexOf("_1.fcth");
					String output = str.substring(0, index) + "_0.fcth";
					String f2 = str.substring(0, index) + "_2.fcth";
					String f3 = str.substring(0, index) + "_3.fcth";
					
					String fc1 = readFile((fileDir + str), FCTHLENGTH);
					String fc2 = readFile((fileDir + f2), FCTHLENGTH);
					String fc3 = readFile((fileDir + f3), FCTHLENGTH);
					
					File out = new File(fileDir + output);
					PrintWriter pw = new PrintWriter(out);
					pw.write(fc1.trim() + "," + fc2.trim() + "," + fc3.trim());
					pw.close();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static String readFile(String fileName, int max)
	{
		String result = "";
		try
		{
			File file = new File(fileName);
			Scanner scn = new Scanner(file);
			String fc = scn.nextLine();
			String[] ff = fc.split(" ");
			
			for(int i = 2; i < max; i++)
			{
				result = result + ff[i] + ",";
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
}

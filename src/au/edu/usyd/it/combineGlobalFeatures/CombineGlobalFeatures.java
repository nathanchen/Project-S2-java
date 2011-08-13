package au.edu.usyd.it.combineGlobalFeatures;
import java.io.*;
import java.util.Scanner;

public class CombineGlobalFeatures 
{

	static String fileDir = "/Users/natechen/Desktop/keyframe/globalFeatures/";
	static String saveToDir = "";
	
	final static int  CEDDLENGTH = 146;
	final static int FCTHLENGTH = 194;
	public static void main(String[] args) 
	{
		File dir = new File(fileDir);
		for(String str : dir.list())
		{
			if(str.endsWith(".cedd"))
			{
				try
				{
					String fileName = str.substring(0, str.lastIndexOf(".cedd"));
					File fcthFile = new File(fileDir + fileName + ".fcth");
					Scanner scan = new Scanner(fcthFile);
					String fcthContent = "";
					while (scan.hasNext())
					{
						fcthContent = scan.nextLine();
					}
					File ceddFile = new File(fileDir + str);
					Scanner scn = new Scanner(ceddFile);
					String ceddContent = "";
					while (scn.hasNext())
					{
						ceddContent = ceddContent + scn.nextLine();;
					}
					String [] cedd = new String[CEDDLENGTH];
					cedd = ceddContent.split(" ");
					System.err.println(cedd[0]);
					
					String [] fcth = new String[FCTHLENGTH];
					fcth = fcthContent.split(" ");
					String result = "";
					for(int i= 2; i < CEDDLENGTH; i++)
					{
						result = result + cedd[i] + " ";
					}
//					result = result + "\n";
					for(int i = 2; i < FCTHLENGTH; i++)
					{
						result = result + fcth[i] + " ";
					}
					File combination = new File(fileDir + fileName + ".combine");
					PrintWriter out = new PrintWriter(combination);
					out.println(result);
					out.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

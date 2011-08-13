package au.edu.usyd.it.changename;

import java.io.File;


/*
 * modify video file's name
 * 
 * delete ".part" 
 * 
 * input: video file directory
 * 
 * output: video files with modified names and store in the same directory as before
 * 
 * */
public class ChangeName 
{
	static String fileDir = "/Users/natechen/Desktop/URLs 11am 5 Aug/";
	public static void main(String[] args) 
	{
		File dir = new File(fileDir);
		for(String string : dir.list())
		{
			if(string.endsWith(".part"))
			{
				File file = new File(fileDir + string);
				file.renameTo(new File(fileDir + string.substring(0, string.lastIndexOf("."))));
				System.out.println(string + " 's name is changed");
			}
		}
	}
}

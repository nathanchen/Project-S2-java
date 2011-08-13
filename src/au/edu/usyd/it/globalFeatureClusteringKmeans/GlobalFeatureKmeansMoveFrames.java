package au.edu.usyd.it.globalFeatureClusteringKmeans;
import java.util.*;
import java.io.*;

import org.apache.commons.io.FileUtils;

/*
 * move the original image files to the cluster accordingly for the demonstration benefit (keep the original files)
 * 
 * 
 * */
public class GlobalFeatureKmeansMoveFrames 
{
	static final String FRAMEDIR = "/Users/natechen/Desktop/keyframe/";
	static final String CLUSTER = "/Users/natechen/Desktop/keyframe/globalFeatures/cluster ";
	static final int NUMCLUSTERS = 6;
	public static void main(String[] args) 
	{
		try
		{
			for(int i = 0; i <= NUMCLUSTERS; i++)
			{
				String str = CLUSTER + i + "/";
				File dir = new File(str);
//				2009-06-01(Mon)PM050000-TEN_DTV10-0fd3b3ef.mp4_19_0.cedd
//				2009-06-01(Mon)PM070000-ABC1_DTV2-0fd3d00f.mp4_167_1.jpg
				for(String s : dir.list())
				{
					int n = 1;
					while(n <= 3)
					{
						String frameName = s.substring(0, s.lastIndexOf("0.cedd")) + n + ".jpg";
						n++;
						File ori = new File(FRAMEDIR + frameName);
						File des = new File(str + frameName);
						FileUtils.copyFile(ori, des);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

package au.edu.usyd.it.ceddfcth;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.imageio.ImageIO;

import net.semanticmetadata.lire.imageanalysis.CEDD;
import net.semanticmetadata.lire.imageanalysis.FCTH;
import net.semanticmetadata.lire.imageanalysis.cedd.*;

/*
 * Generate global features descriptions
 * 
 * CEDD and FCTH
 * 
 * 
 * input: image files (directory)
 * 
 * output: .cedd and .fcth files accordingly and to the same directory
 * 
 * */

public class GlobalFeatureGen 
{
	//the directory that stores image files
	static String fileDir = "/Users/natechen/Desktop/keyframe/";

	public static void main(String[] args) throws IOException 
	{
		File file = new File(fileDir);
		PrintWriter out = null;
		for (String fileName : file.list())
		{
			if(fileName.endsWith(".jpg"))
			{
				try
				{
					File f = new File(fileDir + fileName);
					CEDD cedd = new CEDD();
					FCTH fcth = new FCTH();
					URL url = f.toURL();
					BufferedImage bimg = ImageIO.read(url);
					cedd.extract(bimg);
					String result = null;
					result = cedd.getStringRepresentation();
					String output = fileName.substring(0, fileName.lastIndexOf(".")) + ".cedd";
					File outputFile = new File (fileDir + "globalFeatures/"+ output);
					out = new PrintWriter(outputFile);
					out.println(result);
					out.close();
					fcth.extract(bimg);
					result = fcth.getStringRepresentation();
					output = fileName.substring(0, fileName.lastIndexOf(".")) + ".fcth";
					outputFile = new File(fileDir + "globalFeatures/" +output);
					out = new PrintWriter(outputFile);
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

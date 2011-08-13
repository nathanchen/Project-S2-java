package au.edu.usyd.it.siftClusteringKmeans;

import java.io.File;
import java.util.*;

import net.semanticmetadata.lire.imageanalysis.sift.Feature;
//import net.semanticmetadata.lire.imageanalysis.sift.KMeans.Cluster;
//import net.semanticmetadata.lire.imageanalysis.sift.Feature;
//import net.semanticmetadata.lire.imageanalysis.sift.KMeans;

public class SiftKmeans 
{
	static String siftDir = "/Users/natechen/Desktop/2/2/";
	static final int KK = 3;
	public static void main(String[] args) 
	{
		File dir = new File(siftDir);
		KMeans k = new KMeans();
		for(String str : dir.list())
		{
			if(str.contains(".txt"))
			{
				try
				{
					File sift = new File(siftDir + str);
					Scanner scan = new Scanner(sift);
					scan.nextLine();
					ArrayList<Feature> arrFeat = new ArrayList<Feature>();
					float[] arr = new float[128];
					int lineNum = 1;
					String record = "";
					float y = 0, x = 0, scale = 0, ori =0;
					
					while(scan.hasNext())
					{
						String line = scan.nextLine();
						if((lineNum -1) % 8 == 0)
						{
							if(lineNum != 1)
							{
								float[] loc = new float[2];
								loc[0] = y;
								loc[1] = x;
								arr = stringToFloatArray(record.trim());
								Feature feat = new Feature(scale, ori, loc, arr);
								arrFeat.add(feat);
								System.out.println(arrFeat.size());
							}
							Scanner scn = new Scanner(line);
							y = scn.nextFloat();
							x = scn.nextFloat();
							scale = scn.nextFloat();
							ori = scn.nextFloat();
						}
						else
						{
							record = record + line;
						}
						lineNum++;
					}
					k.addImage(str, arrFeat);
					System.out.println(str);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		k.setNumClusters(KK);
		System.out.println(k.getNumClusters());
		System.out.println("Init clustering");
		
        k.init();
        System.out.println("First step");
        double laststress = k.clusteringStep();
        System.out.println(laststress);
        System.out.println("2nd step");
        double newstress = k.clusteringStep();
        System.out.println(newstress); 
        while (newstress > laststress) {
            System.out.println("newstress-laststress = " + (newstress - laststress));
            laststress = newstress;
            newstress = k.clusteringStep();
            System.out.print(".");
        }
        System.out.println("\nfinished");
        printClusters(k);
        
     // create histograms ...
        List<Image> imgs = k.getImages();
        Cluster[] clusters = k.getClusters();
        for (Iterator<Image> imageIterator = imgs.iterator(); imageIterator.hasNext();) {
            Image image = imageIterator.next();
            image.initHistogram(k.getNumClusters());
            for (Iterator<Feature> iterator = image.features.iterator(); iterator.hasNext();) {
                Feature feat = iterator.next();
                image.getLocalFeatureHistogram()[k.getClusterOfFeature(feat)]++;
                image.normalizeFeatureHistogram();
            }
        }

        for (Image i : imgs) {
            i.printHistogram();
        }
	}
	
	
	private static float[] stringToFloatArray(String result)
	{
		float[] arr = new float[128];
		String[] string = result.split(" ");
		for(int i = 0; i < 128; i++)
		{
			arr[i] = Float.parseFloat(string[i]);
		}
		return arr;
	}
	
	private static void printClusters(KMeans k) 
	{
        Cluster[] clusters = k.getClusters();
        for (int i = 0; i < clusters.length; i++) 
		{
            Cluster cluster = clusters[i];
            System.out.println(i + ": " + cluster.toString());
        }
    }

}


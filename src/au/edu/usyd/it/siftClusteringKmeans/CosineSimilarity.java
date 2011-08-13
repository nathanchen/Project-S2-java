package au.edu.usyd.it.siftClusteringKmeans;

import java.util.Enumeration;

import weka.core.DistanceFunction;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.neighboursearch.PerformanceStats;

public class CosineSimilarity implements DistanceFunction {

	public String[] getOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration listOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOptions(String[] arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	public double distance(Instance first, Instance second) 
	{
		double dis;
		double dotProduct = 0;
		double powFirst = 0;
		double powSecond = 0;
		for(int i = 0; i < first.numAttributes(); i++)
		{
			dotProduct = Double.parseDouble(first.toString(i)) * Double.parseDouble(second.toString(i)) + dotProduct;
			powFirst = powFirst + Math.pow(Double.parseDouble(first.toString(i)), 2);
			powSecond = powSecond + Math.pow(Double.parseDouble(second.toString(i)), 2);
		}
		dis = dotProduct / (Math.sqrt(powFirst) * Math.sqrt(powSecond));
		return dis;
	}

	public double distance(Instance arg0, Instance arg1, PerformanceStats arg2)
			throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public double distance(Instance arg0, Instance arg1, double arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double distance(Instance arg0, Instance arg1, double arg2,
			PerformanceStats arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getAttributeIndices() {
		// TODO Auto-generated method stub
		return null;
	}

	public Instances getInstances() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getInvertSelection() {
		// TODO Auto-generated method stub
		return false;
	}

	public void postProcessDistances(double[] arg0) {
		// TODO Auto-generated method stub

	}

	public void setAttributeIndices(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setInstances(Instances arg0) {
		// TODO Auto-generated method stub

	}

	public void setInvertSelection(boolean arg0) {
		// TODO Auto-generated method stub

	}

	public void update(Instance arg0) {
		// TODO Auto-generated method stub

	}

	
//	public static void main(String[] args)
//	{
//		Instance inst1 = new Instance(3);
//		inst1.setValue(1, 1);
//		inst1.setValue(2, 2);
//		inst1.setValue(0, 0);
//		
//		Instance inst2 = new Instance(3);
//		inst2.setValue(1, 1);
//		inst2.setValue(2, 2);
//		inst2.setValue(0, 0);
//		CosineSimilarity cs = new CosineSimilarity();
//		
//		System.out.println(cs.distance(inst1, inst2));
//	}
}

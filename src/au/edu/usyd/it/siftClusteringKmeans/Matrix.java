package au.edu.usyd.it.siftClusteringKmeans;

public class Matrix 
{
	private int numOfCols;
	private int numOfRows;
	
	private double[] codeWord = new double[numOfCols];
	private double[] docs = new double[numOfRows];
	
	private double[][] matrix;
	
	
	Matrix(int numOfCols, int numOfRows)
	{
		matrix = new double[numOfCols][numOfRows];
	}
	
	public int getNumOfCols() {
		return numOfCols;
	}
	public void setNumOfCols(int numOfCols) {
		this.numOfCols = numOfCols;
	}
	public int getNumOfRows() {
		return numOfRows;
	}
	public void setNumOfRows(int numOfRows) {
		this.numOfRows = numOfRows;
	}
	
	public double[] getCodeWord() {
		return codeWord;
	}
	public void setCodeWord(double[] codeWord) 
	{
		for(int i = 0; i < codeWord.length; i++)
		{
			this.codeWord[i] = codeWord[i];
		}
	}
	public double[] getDocs() {
		return docs;
	}
	public void setDocs(double[] docs) 
	{
		for(int i = 0; i < docs.length; i++)
		{
			this.docs[i] = docs[i];
		}
	}
	
	/*
	 * set matrix elements line by line
	 * 
	 * input:
	 * @param row -> which line
	 * @param featDistribution -> features' distribution for a specific file
	 * 
	 * */
	public void setMatrix(int row, double[] featDistribution)
	{
		for(int i =0; i < getNumOfCols(); i++)
		{
			matrix[i][row] = featDistribution[i];
		}
	}
	
	public double[][] getMatrix() 
	{
		return matrix;
	}
}

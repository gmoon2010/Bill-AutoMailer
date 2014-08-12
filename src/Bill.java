import java.io.Serializable;

public class Bill implements Comparable<Bill>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5503917638723324858L;
	private String name;
	private int splitNum;
	private double totalAmt;
	
	public Bill(String name, int splitNum, double totalAmt)
	{
		this.name = name;
		this.splitNum = splitNum;
		this.totalAmt = totalAmt;
	}
	
	public String toString()
	{
		return name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getSplitNum()
	{
		return splitNum;
	}
	
	public double getTotalAmt()
	{
		return totalAmt;
	}
	
	public int compareTo(Bill o) 
	{	
		return name.compareTo(o.getName());
	}
}

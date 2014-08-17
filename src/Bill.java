import java.io.Serializable;

public class Bill implements Comparable<Bill>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5503917638723324858L;
	private String name;
	private double totalAmt;
	
	public Bill(String name, double totalAmt)
	{
		this.name = name;
		this.totalAmt = totalAmt;
	}
	
	public double getSplitAmount(double splitNum)
	{
		if(splitNum > 0)
			return Math.round(totalAmt/splitNum * 100.0) / 100.0;
		else 
			return Math.round(totalAmt * 100.0) / 100.0;
	}
	
	public String toString()
	{
		return name;
	}
	
	public String getName()
	{
		return name;
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

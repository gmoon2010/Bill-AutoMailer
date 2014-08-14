import java.io.Serializable;

public class Contact implements Comparable<Contact>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3703981147122062309L;
	private String name, email;
	
	public Contact(String name, String email)
	{
		this.name = name;
		this.email = email;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String toString()
	{
		return name;
	}

	public int compareTo(Contact o) 
	{
		return name.compareTo(o.getName());
	}
}

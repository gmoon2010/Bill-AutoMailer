import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


public class Template implements Comparable<Template>, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6802216367909926446L;
	private ArrayList<Bill> billList;
	private ArrayList<Contact> contactList;
	private String templateName, preview;
	
	public Template(String name, String preview, ArrayList<Bill> billList, ArrayList<Contact> contactList)
	{
		this.preview = preview;
		this.templateName = name;
		this.billList = billList;
		this.contactList = contactList;
	}
	
	public String getName()
	{
		return templateName;
	}
	
	public ArrayList<Contact> getContactList()
	{
		return contactList;
	}
	
	public ArrayList<Bill> getBillList()
	{
		return billList;
	}
	
	public void setName(String name)
	{
		templateName = name;
	}
	
	public void addContact(Contact c)
	{
		contactList.add(c);
		Collections.sort(contactList);
	}
	
	public void removeContact(Contact c)
	{
		contactList.remove(c);
		Collections.sort(contactList);
	}
	
	public void addBill(Bill b)
	{
		billList.add(b);
		Collections.sort(billList);
	}
	
	public void removeBill(Bill b)
	{
		contactList.remove(b);
		Collections.sort(billList);
	}
	
	public String toString()
	{
		return templateName;
	}
	
	public String getPreview()
	{
		return preview;
	}
	
	@Override
	public int compareTo(Template o) 
	{
		return templateName.compareTo(o.getName());
	}

}

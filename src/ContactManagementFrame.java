import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ContactManagementFrame extends JFrame
{
	private ArrayList<Contact> contactList;

	public ContactManagementFrame(ArrayList<Contact> contactList)
	{
		this.contactList = contactList;
		
		setTitle("Contact Management");
	}
}

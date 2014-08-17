import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NavigationFrame extends JFrame
{
	private JButton sendEmailButton, manageBillsButton, manageContactsButton, manageTemplatesButton;
	private JPanel mainPanel;
	private ArrayList<Bill> billList;
	private ArrayList<Contact> contactList;
	private ArrayList<Template> templateList;
	
	public NavigationFrame(ArrayList<Bill> billList, ArrayList<Contact> contactList, ArrayList<Template> templateList)
	{
		this.billList = billList;
		this.contactList = contactList;
		this.templateList = templateList;
		
		setTitle("Bill Auto-mailer Navigation");
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonListener bListener = new ButtonListener();
		
		sendEmailButton = new JButton("Send Email");
		
		manageBillsButton = new JButton("Manage Bills");
		manageBillsButton.addActionListener(bListener);
		
		manageContactsButton = new JButton("Manage Contacts");
		manageContactsButton.addActionListener(bListener);
		
		manageTemplatesButton = new JButton("Manage Templates");
		manageTemplatesButton.addActionListener(bListener);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(4, 1));
		
		mainPanel.add(manageBillsButton);
		mainPanel.add(manageContactsButton);
		mainPanel.add(manageTemplatesButton);
		mainPanel.add(sendEmailButton);
		
		add(mainPanel);
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			JButton source = (JButton) e.getSource();
			String buttonText = source.getText();
			
			if(buttonText.equals("Manage Bills"))
			{
				BillManagementFrame frame = new BillManagementFrame(billList);
				frame.setVisible(true);
			}
			else if(buttonText.equals("Manage Contacts"))
			{
				ContactManagementFrame frame = new ContactManagementFrame(contactList);
				frame.setVisible(true);
			}
			else if(buttonText.equals("Manage Templates"))
			{
				TemplateManagementFrame frame = new TemplateManagementFrame(templateList, billList, contactList);
				frame.setVisible(true);
			}
		}		
	}
}

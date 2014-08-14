import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ContactManagementFrame extends JFrame
{
	private ArrayList<Contact> contactList;
	private JPanel topPanel, mainPanel, bottomPanel;
	private JButton addButton, removeButton, editButton;
	private JList<Contact> listDisplay;
	private JScrollPane contactPane;

	public ContactManagementFrame(ArrayList<Contact> contactList)
	{
		this.contactList = contactList;
		initComponents();
	}

	private void initComponents()
	{
		ButtonListener bListener = new ButtonListener();

		topPanel = new JPanel();
		bottomPanel = new JPanel();

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);

		removeButton = new JButton("-");
		removeButton.addActionListener(bListener);

		addButton = new JButton("+");
		addButton.addActionListener(bListener);

		editButton = new JButton("Edit");
		editButton.addActionListener(bListener);

		listDisplay = new JList<Contact>();
		listDisplay.setBackground(Color.white);
		listDisplay.setPreferredSize(new Dimension(250, 250));
		
		updateListDisplay();
		
		contactPane = new JScrollPane();
		contactPane.setViewportView(listDisplay);
		topPanel.add(addButton);
		topPanel.add(removeButton);
		topPanel.add(editButton);
		bottomPanel.add(contactPane);

		add(mainPanel);

		setTitle("Contact Management");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void addItem()
	{
		AddItemFrame frame = new AddItemFrame();
		frame.setVisible(true);
	}

	private void removeItem()
	{
		if(listDisplay.getSelectedValue() != null)
		{
			Contact billToRemove = listDisplay.getSelectedValue();
			contactList.remove(billToRemove);
			updateListDisplay();
		}
	}

	private void updateListDisplay()
	{	
		Contact[] billData = new Contact[contactList.size()];

		for(int i = 0; i < contactList.size(); i++)
			billData[i] = contactList.get(i);

		listDisplay.setListData(billData);

		if(contactList.size() > 0)
			listDisplay.setSelectedIndex(0);
	}

	private void updateListDisplay(Contact b)
	{	
		Contact[] billData = new Contact[contactList.size()];

		for(int i = 0; i < contactList.size(); i++)
			billData[i] = contactList.get(i);

		listDisplay.setListData(billData);
		listDisplay.setSelectedValue(b, true);
	}

	private void editItem()
	{
		if(listDisplay.getSelectedValue() != null)
		{
			Contact contactToEdit = listDisplay.getSelectedValue();
			AddItemFrame frame = new AddItemFrame(contactToEdit);
			frame.setVisible(true);
		}
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			JButton source = (JButton) e.getSource();
			String buttonText = source.getText();

			if(buttonText.equals("+"))
				addItem();
			else if (buttonText.equals("-"))
				removeItem();
			else if(buttonText.equals("Edit"))
				editItem();
		}
	}	//end ButtonListener

	private class AddItemFrame extends JFrame
	{
		JPanel mainPanel, topPanel, topLPanel, topRPanel, bottomPanel;
		JButton addButton, cancelButton;
		JTextField nameField, emailField;
		JLabel nameLabel, emailLabel;

		public AddItemFrame()
		{	
			initComponents();
		}

		public AddItemFrame(Contact c)
		{
			String name = c.getName();
			String email = c.getEmail();

			contactList.remove(c);

			initComponents();

			nameField.setText(name);
			emailField.setText(email);
		}

		private void initComponents()
		{
			addButtonListener aBBListener = new addButtonListener(this);

			topLPanel = new JPanel();
			topLPanel.setLayout(new GridLayout(3, 1));

			topRPanel = new JPanel();
			topRPanel.setLayout(new GridLayout(3, 1));

			topPanel = new JPanel();
			topPanel.setLayout(new GridLayout(1, 2));	
			topPanel.add(topLPanel);
			topPanel.add(topRPanel);

			bottomPanel = new JPanel();

			mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(2, 1));
			mainPanel.add(topPanel);
			mainPanel.add(bottomPanel);

			addButton = new JButton("Add Contact");
			addButton.addActionListener(aBBListener);

			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(aBBListener);

			nameField = new JTextField();
			nameField.setPreferredSize(new Dimension(20, 40));

			emailField = new JTextField();
			emailField = new JTextField();

			nameLabel = new JLabel("Name:");

			emailLabel = new JLabel("E-mail Address:");

			nameLabel.setLabelFor(nameField);
			emailLabel.setLabelFor(emailField);

			topLPanel.add(nameLabel);
			topRPanel.add(nameField);
			topLPanel.add(emailLabel);
			topRPanel.add(emailField);
			bottomPanel.add(addButton);
			bottomPanel.add(cancelButton);

			add(mainPanel);

			setTitle("Contact Information");
			setSize(500, 500);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
		}

		private class addButtonListener implements ActionListener
		{
			private AddItemFrame openFrame;

			public addButtonListener(AddItemFrame openFrame)
			{
				this.openFrame = openFrame;
			}

			public void actionPerformed(ActionEvent e) 
			{
				boolean nameBool = true, emailBool = true;
				
				JButton source = (JButton) e.getSource();
				String buttonText = source.getText();

				if(buttonText.equals("Add Contact"))
				{					
					String name = nameField.getText();
					String email = emailField.getText();

					
					if(nameExists(name))
						nameBool = false;

					if(!nameBool)
						nameField.setBackground(Color.red);

					if(!emailBool)
						emailField.setBackground(Color.red);


					if(nameBool && emailBool)
					{
						Contact contactToAdd = new Contact(name, email);
						contactList.add(contactToAdd);
						Collections.sort(contactList);
						openFrame.dispose();
						updateListDisplay(contactToAdd);
					}

				}
				else if(buttonText.equals("Cancel"))
				{
					openFrame.dispose();
				}
			}
			
			private boolean nameExists(String contactName)
			{
				for(int i = 0; i < contactList.size(); i++)
				{
					if(contactList.get(i).equals(contactName))
						return true;
				}
				
				return false;
			}

		}	//end addButtonListener
	}	//end AddItemFrame
}

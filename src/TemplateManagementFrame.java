import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TemplateManagementFrame extends JFrame
{
	private JTextArea previewArea;
	private JScrollPane templatePane;
	private ArrayList<Template> templateList;
	private ArrayList<Bill> billList;
	private ArrayList<Contact> contactList;
	private JList<Template> templateJList;
	private JPanel mainPanel, topPanel, bottomPanel, bottomLPanel, bottomRPanel;
	private JButton addButton, removeButton, editButton, previewButton;
	
	public TemplateManagementFrame(ArrayList<Template> templateList, ArrayList<Bill> billList,ArrayList<Contact> contactList)
	{
		this.contactList = contactList;
		this.billList = billList;
		this.templateList = templateList;
		
		initComponents();		
	}
	
	private void initComponents()
	{
		ButtonListener bListener = new ButtonListener();
		
		templateJList = new JList<Template>();
		
		previewArea = new JTextArea();
		
		previewButton = new JButton("View Preview");
		previewButton.addActionListener(bListener);
		
		addButton = new JButton("+");
		addButton.addActionListener(bListener);
		
		removeButton = new JButton("-");
		removeButton.addActionListener(bListener);
		
		editButton = new JButton("Edit");
		editButton.addActionListener(bListener);
		
		updateTemplateList();
		
		templatePane = new JScrollPane();
		templatePane.setViewportView(templateJList);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		
		topPanel = new JPanel();
		topPanel.add(addButton);
		topPanel.add(removeButton);
		topPanel.add(editButton);
		topPanel.add(previewButton);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		
		bottomLPanel = new JPanel();
		bottomLPanel.add(templatePane);
		
		bottomRPanel = new JPanel();
		previewArea.setText("Hello");
		bottomRPanel.add(previewArea);
		
		bottomPanel.add(bottomLPanel);
		bottomPanel.add(bottomRPanel);
		
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		
		add(mainPanel);
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Template Management");
	}
	
	private void updateTemplateList()
	{
		Template[] tempArr = new Template[templateList.size()];
		
		for(int i = 0; i < templateList.size(); i++)
			tempArr[i] = templateList.get(i);
		
		templateJList.setListData(tempArr);
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JButton source = (JButton) e.getSource();
			String buttonText = source.getText();
			
			if(buttonText.equals("+"))
			{
				AddTemplateFrame frame = new AddTemplateFrame();
				frame.setVisible(true);
			}
			else if(buttonText.equals("-"))
			{
			
			}
			else if(buttonText.equals("Edit"))
			{
				
			}
		}
	}	//end ButtonListener
	
	private class AddTemplateFrame extends JFrame
	{
		private String tempName, preview;
		private ArrayList<Contact> templateCList;
		private ArrayList<Bill> templateBList;
		private JPanel mainPanel, topPanel, midTPanel, midBPanel,  bottomPanel;
		private JLabel nameLabel, billBoxLabel, contactBoxLabel;
		private JTextField nameField;
		private JComboBox<Contact> contactComboBox;
		private JComboBox<Bill> billComboBox;
		private JButton addTemplateButton, cancelButton, addBillButton, addContactButton, removeBillButton, removeContactButton;
		
		public AddTemplateFrame()
		{
			preview = "";
			tempName = null;
			templateCList = new ArrayList<Contact>();
			templateBList = new ArrayList<Bill>();
			initComponents();
		}
		
		private void initComponents()
		{
			AddTemplateButtonListener aTBListener = new AddTemplateButtonListener(this);
			
			addTemplateButton = new JButton("Add Template");
			addTemplateButton.setName("addTemplateButton");
			
			cancelButton = new JButton("Cancel");
			cancelButton.setName("cancelButton");
			cancelButton.addActionListener(aTBListener);
			
			addContactButton = new JButton("+");
			addContactButton.setName("addContactButton");
			addContactButton.addActionListener(aTBListener);
			
			addBillButton = new JButton("+");
			addBillButton.setName("addBillButton");
			addBillButton.addActionListener(aTBListener);
			
			removeContactButton = new JButton("-");
			removeContactButton.setName("removeContactButton");
			removeContactButton.addActionListener(aTBListener);
			
			removeBillButton = new JButton("-");
			removeBillButton.setName("removeBillButton");
			removeBillButton.addActionListener(aTBListener);
			
			contactBoxLabel = new JLabel("Contacts:");
			contactBoxLabel.setLabelFor(contactComboBox);
			contactComboBox = new JComboBox<Contact>();
			updateContactBox();
			
			billBoxLabel = new JLabel("Bills:");
			billBoxLabel.setLabelFor(billComboBox);
			billComboBox = new JComboBox<Bill>();
			updateBillBox();
			
			nameLabel = new JLabel("Template Name:");
			nameLabel.setLabelFor(nameField);
			nameField = new JTextField();
			
			mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(4, 1));
			
			topPanel = new JPanel();
			topPanel.add(nameLabel);
			topPanel.add(nameField);
			
			midTPanel = new JPanel();
			midTPanel.add(billBoxLabel);
			midTPanel.add(billComboBox);
			midTPanel.add(addBillButton);
			midTPanel.add(removeBillButton);
			
			midBPanel = new JPanel();
			midBPanel.add(contactBoxLabel);
			midBPanel.add(contactComboBox);
			midBPanel.add(addContactButton);
			midBPanel.add(removeContactButton);
			
			bottomPanel = new JPanel();
			bottomPanel.add(addTemplateButton);
			bottomPanel.add(cancelButton);
			
			mainPanel.add(topPanel);
			mainPanel.add(midTPanel);
			mainPanel.add(midBPanel);
			mainPanel.add(bottomPanel);
			
			add(mainPanel);
			setTitle("Add Template");
			setSize(500, 500);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		
		private void updateBillBox()
		{
			Bill[] billArr = new Bill[billList.size()];
			
			for(int i = 0; i < billList.size(); i++)
				billArr[i] = billList.get(i);
			
			billComboBox.setModel(new DefaultComboBoxModel<Bill>(billArr));	
		}
		
		private void updateContactBox()
		{
			Contact[] contactArr = new Contact[contactList.size()];
			
			for(int i = 0; i < contactList.size(); i++)
				contactArr[i] = contactList.get(i);
			
			contactComboBox.setModel(new DefaultComboBoxModel<Contact>(contactArr));	
		}
		
		private class AddTemplateButtonListener implements ActionListener
		{
			private AddTemplateFrame openFrame;
			private String greeting, body, closing;
			
			public AddTemplateButtonListener(AddTemplateFrame openFrame)
			{
				this.greeting = null;
				this.body = null;
				this.closing = null;
				this.openFrame = openFrame;
			}
			
			public void actionPerformed(ActionEvent e)
			{
				JButton source = (JButton) e.getSource();
				String bName = source.getName();
				
				if(bName.equals("cancelButton"))
					openFrame.dispose();
				else if(bName.equals("addTemplateButton"))
					addTemplate();
				else if(bName.equals("addBillButton"))
					addBill();
				else if(bName.equals("removeBillButton"))
					removeBill();
				else if(bName.equals("addContactButton"))
					addContact();
				else if(bName.equals("removeContactButton"))
					removeContact();
			}
			
			private void removeContact()
			{
				templateCList.remove((Contact) contactComboBox.getSelectedItem());
				updatePreview();
			}
			
			private void addContact()
			{
				templateCList.add((Contact) contactComboBox.getSelectedItem());
				updatePreview();
			}
			
			private void removeBill()
			{
				templateBList.remove((Bill) billComboBox.getSelectedItem());
				updatePreview();
			}
			
			private void addBill()
			{
				templateBList.add((Bill) billComboBox.getSelectedItem());
				updatePreview();
			}
			
			private String updateBody()
			{
				body = "The following are the bills that each person"
						+ "needs to pay;\n\n";
				
				for(int i = 0; i < templateBList.size(); i++)
				{
					Bill bill = templateBList.get(i);
					
					if(i != templateBList.size())
						body += (i+1) + ".\t" + bill.getName() + ":\t" + bill.getSplitAmount(templateCList.size()) + "\n";
					else
						body += (i+1) + ".\t" + bill.getName() + ":\t" + bill.getSplitAmount(templateCList.size()) + "\n\n";
				}
				
				return body;
			}
			
			private String updateGreeting()
			{
				String name = null;
				greeting = "Hello ";
				
				for(int i = 0; i < templateCList.size(); i++)
				{
					name = templateCList.get(i).getName();
					
					if(i == templateCList.size() - 1)
						greeting += "and " + name + ",\n\n";
					else
						greeting += name + ", ";
				}
				
				return greeting;
			}
			
			public String updateClosing()
			{
				closing  = "Thanks,\n\nGreg";
				
				return closing;
			}
			
			private void updatePreview()
			{
				preview = null;
				
				preview += updateGreeting();
				preview += updateBody();
				preview += updateClosing();
				
				previewArea.setText(preview);
			}
			
			private void addTemplate()
			{
				tempName = nameField.getText();
				
				if(tempName != null)
					templateList.add(new Template(tempName, preview, templateBList, templateCList));
				else
					nameField.setBackground(Color.red);
				
				Collections.sort(templateList);
			}
		}	//end AddTemplateButtonListener
	}	//end AddTemplateFrame
}	//end TemplateManagementFrame

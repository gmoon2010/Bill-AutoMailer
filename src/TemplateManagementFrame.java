import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class TemplateManagementFrame extends JFrame
{
	private JTextArea previewArea;
	private JScrollPane templatePane, previewPane;
	private ArrayList<Template> templateList;
	private ArrayList<Bill> billList;
	private ArrayList<Contact> contactList;
	private JList<Template> templateJList;
	private JPanel mainPanel, topPanel, bottomPanel, bottomLPanel, bottomRPanel;
	private JButton addButton, removeButton, editButton;
	
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
		templateJList.addListSelectionListener(new ListListener());

		previewArea = new JTextArea();
		previewArea.setEditable(false);
		
		previewPane = new JScrollPane();
		previewPane.setViewportView(previewArea);
		
		addButton = new JButton("+");
		addButton.addActionListener(bListener);
		
		removeButton = new JButton("-");
		removeButton.addActionListener(bListener);
		
		editButton = new JButton("Edit");
		editButton.addActionListener(bListener);
		
		updateTemplateList();
		
		templatePane = new JScrollPane();
		templatePane.setViewportView(templateJList);
		templatePane.setPreferredSize(new Dimension(250, 250));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		
		topPanel = new JPanel();
		topPanel.add(addButton);
		topPanel.add(removeButton);
		topPanel.add(editButton);
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		
		bottomLPanel = new JPanel();
		bottomLPanel.add(templatePane);
		
		bottomRPanel = new JPanel();
		bottomRPanel.setLayout(new BorderLayout());
		bottomRPanel.add(previewPane, BorderLayout.CENTER);
		
		bottomPanel.add(bottomLPanel);
		bottomPanel.add(bottomRPanel);
		
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);
		
		if(templateList.size() > 0)
			templateJList.setSelectedIndex(0);
		
		if(templateJList.getSelectedValue() != null)
			previewArea.setText(templateJList.getSelectedValue().getPreview());
		
		add(mainPanel);
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Template Management");
	}
	
	private void removeTemplate()
	{
		templateList.remove(templateJList.getSelectedValue());
		updateTemplateList();
	}
	
	private void updateTemplateList()
	{
		Template[] tempArr = new Template[templateList.size()];
		
		for(int i = 0; i < templateList.size(); i++)
			tempArr[i] = templateList.get(i);
		
		templateJList.setListData(tempArr);
	}
	
	private class ListListener implements ListSelectionListener
	{

		@Override
		public void valueChanged(ListSelectionEvent e) 
		{
			previewArea.setText(templateJList.getSelectedValue().getPreview());
		}
		
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
				removeTemplate();
			else if(buttonText.equals("Edit"))
			{
				AddTemplateFrame frame = new AddTemplateFrame(templateJList.getSelectedValue());
				frame.setVisible(true);
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
		
		public AddTemplateFrame(Template temp)
		{
			preview = temp.getPreview();
			tempName = temp.getName();
			templateCList = temp.getContactList();
			templateBList = temp.getBillList();
			
			initComponents();
			
			nameField.setText(tempName);
			previewArea.setText(preview);
		}
		
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
			
			previewPane = new JScrollPane();
			
			addTemplateButton = new JButton("Add Template");
			addTemplateButton.setName("addTemplateButton");
			addTemplateButton.addActionListener(aTBListener);
			
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
			nameField.setColumns(20);
			
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
				Collections.sort(templateBList);
				body = "The following are the bills that each person"
						+ " needs to pay;\n\n";
				
				for(int i = 0; i < templateBList.size(); i++)
				{
					Bill bill = templateBList.get(i);
					
					if(i != templateBList.size() - 1)
						body += (i+1) + ".\t" + bill.getName() + ":\t$" + bill.getSplitAmount(templateCList.size()) + "/person\n";
					else
						body += (i+1) + ".\t" + bill.getName() + ":\t$" + bill.getSplitAmount(templateCList.size()) + "/person\n\n";
				}
				
				return body;
			}
			
			private String updateGreeting()
			{
				Collections.sort(templateCList);
				greeting = "Hello ";
				
				String name = null;
				
				for(int i = 0; i < templateCList.size(); i++)
				{
					name = templateCList.get(i).getName();
					
					if(i == templateCList.size() - 1)
					{
						if(templateCList.size() > 1)
							greeting += "and " + name;
						else
							greeting += name;
					}
					else
						greeting += name + ", ";
				}
				
				greeting += ",\n\n";
				
				return greeting;
			}
			
			public String updateClosing()
			{
				closing  = "Thanks,\n\nGreg";
				
				return closing;
			}
			
			private void updatePreview()
			{
				preview = "";
				
				preview += updateGreeting();
				preview += updateBody();
				preview += updateClosing();
				
				previewArea.setText(preview);
			}
			
			private void addTemplate()
			{
				tempName = nameField.getText();
				
				if(tempName != null)
				{
					templateList.add(new Template(tempName, preview, templateBList, templateCList));
					Collections.sort(templateList);
					updateTemplateList();
				}
				else
					nameField.setBackground(Color.red);
			}
		}	//end AddTemplateButtonListener
	}	//end AddTemplateFrame
}	//end TemplateManagementFrame

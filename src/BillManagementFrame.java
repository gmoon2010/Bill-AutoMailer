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
public class BillManagementFrame extends JFrame
{
	private JButton addButton, removeButton, editButton;
	private JList<Bill> listDisplay;
	private ArrayList<Bill> billList;
	private JPanel mainPanel, topPanel, bottomPanel;
	private JScrollPane listScrollPane;

	public BillManagementFrame(ArrayList<Bill> billList)
	{
		this.billList = billList;
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

		listDisplay = new JList<Bill>();
		listDisplay.setBackground(Color.white);
		listDisplay.setPreferredSize(new Dimension(250, 250));
		updateListDisplay();

		topPanel.add(addButton);
		topPanel.add(removeButton);
		topPanel.add(editButton);
		
		listScrollPane = new JScrollPane();
		listScrollPane.setViewportView(listDisplay);
		bottomPanel.add(listScrollPane);

		add(mainPanel);

		setTitle("Bill Management");
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
			Bill billToRemove = listDisplay.getSelectedValue();
			billList.remove(billToRemove);
			updateListDisplay();
		}
	}

	private void updateListDisplay()
	{	
		Bill[] billData = new Bill[billList.size()];

		for(int i = 0; i < billList.size(); i++)
			billData[i] = billList.get(i);

		listDisplay.setListData(billData);

		if(billList.size() > 0)
			listDisplay.setSelectedIndex(0);
	}

	private void updateListDisplay(Bill b)
	{	
		Bill[] billData = new Bill[billList.size()];

		for(int i = 0; i < billList.size(); i++)
			billData[i] = billList.get(i);

		listDisplay.setListData(billData);
		listDisplay.setSelectedValue(b, true);
	}

	private void editItem()
	{
		if(listDisplay.getSelectedValue() != null)
		{
			Bill billToEdit = listDisplay.getSelectedValue();
			AddItemFrame frame = new AddItemFrame(billToEdit);
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
		JTextField nameField, totalAmtField;
		JLabel nameLabel, totalAmtLabel;

		public AddItemFrame()
		{	
			initComponents();
		}

		public AddItemFrame(Bill b)
		{
			String name = b.getName();
			double totalAmt = b.getTotalAmt();

			billList.remove(b);

			initComponents();

			nameField.setText(name);
			totalAmtField.setText(Double.toString(totalAmt));	
		}

		private void initComponents()
		{
			addButtonListener aBBListener = new addButtonListener(this);

			topLPanel = new JPanel();
			topLPanel.setLayout(new GridLayout(2, 1));

			topRPanel = new JPanel();
			topRPanel.setLayout(new GridLayout(2, 1));

			topPanel = new JPanel();
			topPanel.setLayout(new GridLayout(1, 2));	
			topPanel.add(topLPanel);
			topPanel.add(topRPanel);

			bottomPanel = new JPanel();

			mainPanel = new JPanel();
			mainPanel.setLayout(new GridLayout(2, 1));
			mainPanel.add(topPanel);
			mainPanel.add(bottomPanel);

			addButton = new JButton("Add Bill");
			addButton.addActionListener(aBBListener);

			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(aBBListener);

			nameField = new JTextField();
			nameField.setPreferredSize(new Dimension(20, 40));

			totalAmtField = new JTextField();

			nameLabel = new JLabel("Name:");

			totalAmtLabel = new JLabel("Total Amount:");

			nameLabel.setLabelFor(nameField);
			totalAmtLabel.setLabelFor(totalAmtField);

			topLPanel.add(nameLabel);
			topRPanel.add(nameField);
			topLPanel.add(totalAmtLabel);
			topRPanel.add(totalAmtField);
			bottomPanel.add(addButton);
			bottomPanel.add(cancelButton);

			add(mainPanel);

			setTitle("Bill Information");
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
				boolean billNameBool = true, totalAmtBool = true;
				JButton source = (JButton) e.getSource();
				String buttonText = source.getText();

				if(buttonText.equals("Add Bill"))
				{					
					String billName = nameField.getText();
					String totalAmt = totalAmtField.getText();

					double totalAmtDouble = 0;

					if(!isDouble(totalAmt))
					{
						if(totalAmt.charAt(0) == '$')
							totalAmt = totalAmt.replace("$", "");
						else
							totalAmtBool = false;
					}
					else
					{
						totalAmtDouble= Math.round(100*Double.parseDouble(totalAmt))/((double)100);
					}

					if(nameExists(billName))
						billNameBool = false;

					if(!billNameBool)
						nameField.setBackground(Color.red);

					if(!totalAmtBool)
						totalAmtField.setBackground(Color.red);

					if(billNameBool && totalAmtBool)
					{
						Bill billToAdd = new Bill(billName, totalAmtDouble);
						billList.add(billToAdd);
						Collections.sort(billList);
						openFrame.dispose();
						updateListDisplay(billToAdd);
					}

				}
				else if(buttonText.equals("Cancel"))
				{
					openFrame.dispose();
				}
			}

			private boolean nameExists(String s)
			{
				for(int i = 0; i < billList.size(); i++)
				{
					if(s.equals(billList.get(i).toString()))
						return true;
				}

				return false;
			}

			private boolean isDouble(String s)
			{
				try {
					Double.parseDouble(s);
					return true;
				}
				catch(NumberFormatException e)
				{
					return false;
				}
			}
		}	//end addButtonListener
	}	//end AddItemFrame
}	//end BillManagementFrame

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
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BillManagementFrame extends JFrame
{
	private JButton addBillButton, removeBillButton, editBillButton;
	private JList<Bill> billDisplay;
	private ArrayList<Bill> billList;
	private JPanel mainPanel, topPanel, bottomPanel;

	public BillManagementFrame(ArrayList<Bill> billList)
	{
		this.billList = billList;

		ButtonListener bListener = new ButtonListener();

		topPanel = new JPanel();
		bottomPanel = new JPanel();

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2, 1));
		mainPanel.add(topPanel);
		mainPanel.add(bottomPanel);

		removeBillButton = new JButton("-");
		removeBillButton.addActionListener(bListener);

		addBillButton = new JButton("+");
		addBillButton.addActionListener(bListener);
		
		editBillButton = new JButton("Edit");
		editBillButton.addActionListener(bListener);

		billDisplay = new JList<Bill>();
		billDisplay.setBackground(Color.white);
		billDisplay.setPreferredSize(new Dimension(250, 250));
		updateBillDisplay();
		
		topPanel.add(addBillButton);
		topPanel.add(removeBillButton);
		topPanel.add(editBillButton);
		bottomPanel.add(billDisplay);

		add(mainPanel);
		
		setTitle("Bill Management");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private void addBill()
	{
		AddBillFrame frame = new AddBillFrame();
		frame.setVisible(true);
	}

	private void removeBill()
	{
		if(billDisplay.getSelectedValue() != null)
		{
			Bill billToRemove = billDisplay.getSelectedValue();
			billList.remove(billToRemove);
			updateBillDisplay();
		}
	}

	private void updateBillDisplay()
	{	
		Bill[] billData = new Bill[billList.size()];

		for(int i = 0; i < billList.size(); i++)
			billData[i] = billList.get(i);
		
		billDisplay.setListData(billData);
		
		if(billList.size() > 0)
			billDisplay.setSelectedIndex(0);
	}
	
	private void updateBillDisplay(Bill b)
	{	
		Bill[] billData = new Bill[billList.size()];

		for(int i = 0; i < billList.size(); i++)
			billData[i] = billList.get(i);

		billDisplay.setListData(billData);
		billDisplay.setSelectedValue(b, true);
	}

	private void editBill()
	{
		if(billDisplay.getSelectedValue() != null)
		{
			Bill billToEdit = billDisplay.getSelectedValue();
			AddBillFrame frame = new AddBillFrame(billToEdit);
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
				addBill();
			else if (buttonText.equals("-"))
				removeBill();
			else if(buttonText.equals("Edit"))
				editBill();
		}
	}	//end ButtonListener

	private class AddBillFrame extends JFrame
	{
		JPanel addBillMainPanel, topPanel, topLPanel, topRPanel, bottomPanel;
		JButton addBillButton, cancelButton;
		JTextField nameField, totalAmtField, splitNumField;
		JLabel nameLabel, totalAmtLabel, splitNumLabel;

		public AddBillFrame()
		{	
			initComponents();
		}
		
		public AddBillFrame(Bill b)
		{
			String name = b.getName();
			int splitNum = b.getSplitNum();
			double totalAmt = b.getTotalAmt();
			
			billList.remove(b);
			
			initComponents();
			
			nameField.setText(name);
			splitNumField.setText(Integer.toString(splitNum));
			totalAmtField.setText(Double.toString(totalAmt));	
		}

		private void initComponents()
		{
			AddBillButtonListener aBBListener = new AddBillButtonListener(this);

			topLPanel = new JPanel();
			topLPanel.setLayout(new GridLayout(3, 1));
			
			topRPanel = new JPanel();
			topRPanel.setLayout(new GridLayout(3, 1));
			
			topPanel = new JPanel();
			topPanel.setLayout(new GridLayout(1, 2));	
			topPanel.add(topLPanel);
			topPanel.add(topRPanel);
			
			bottomPanel = new JPanel();
			
			addBillMainPanel = new JPanel();
			addBillMainPanel.setLayout(new GridLayout(2, 1));
			addBillMainPanel.add(topPanel);
			addBillMainPanel.add(bottomPanel);
			
			addBillButton = new JButton("Add Bill");
			addBillButton.addActionListener(aBBListener);

			cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(aBBListener);

			nameField = new JTextField();
			nameField.setPreferredSize(new Dimension(20, 40));
			
			totalAmtField = new JTextField();
			splitNumField = new JTextField();

			nameLabel = new JLabel("Name:");
			
			totalAmtLabel = new JLabel("Total Amount:");
			
			splitNumLabel = new JLabel("Number of People to Split Bill:");
			
			nameLabel.setLabelFor(nameField);
			totalAmtLabel.setLabelFor(totalAmtField);
			splitNumLabel.setLabelFor(splitNumField);

			topLPanel.add(nameLabel);
			topRPanel.add(nameField);
			topLPanel.add(totalAmtLabel);
			topRPanel.add(totalAmtField);
			topLPanel.add(splitNumLabel);
			topRPanel.add(splitNumField);
			bottomPanel.add(addBillButton);
			bottomPanel.add(cancelButton);

			add(addBillMainPanel);

			setTitle("Bill Information");
			setSize(500, 500);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
		}

		private class AddBillButtonListener implements ActionListener
		{
			private AddBillFrame openFrame;

			public AddBillButtonListener(AddBillFrame openFrame)
			{
				this.openFrame = openFrame;
			}

			public void actionPerformed(ActionEvent e) 
			{
				boolean billNameBool = true, splitNumBool = true, totalAmtBool = true;
				JButton source = (JButton) e.getSource();
				String buttonText = source.getText();

				if(buttonText.equals("Add Bill"))
				{					
					String billName = nameField.getText();
					String splitNum = splitNumField.getText();
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

					if(!isInteger(splitNum))
						splitNumBool = false;
					else
					{
						if(Integer.parseInt(splitNum) < 1)
							splitNumBool = false;
					}
							
					if(nameExists(billName))
						billNameBool = false;
					
					if(!billNameBool)
						nameField.setBackground(Color.red);
					
					if(!splitNumBool)
						splitNumField.setBackground(Color.red);
					
					if(!totalAmtBool)
						totalAmtField.setBackground(Color.red);
	
					if(billNameBool && splitNumBool && totalAmtBool)
					{
						Bill billToAdd = new Bill(billName, Integer.parseInt(splitNum), totalAmtDouble);
						billList.add(billToAdd);
						Collections.sort(billList);
						updateBillDisplay();
						openFrame.dispose();
						updateBillDisplay(billToAdd);
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

			private boolean isInteger(String s)
			{
				try {
					Integer.parseInt(s);
					return true;
				}
				catch(NumberFormatException e)
				{
					return false;
				}
			}
		}	//end AddBillButtonListener
	}	//end AddBillFrame
}	//end BillManagementFrame

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
public class ManagementFrame extends JFrame
{
	private JButton addButton, removeButton, editButton;
	private JList<Object> listDisplay;
	private ArrayList<Object> infoList;
	private JPanel mainPanel, topPanel, bottomPanel;

	public ManagementFrame(String title, ArrayList<Object> infoList)
	{
		this.infoList = infoList;

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

		listDisplay = new JList<Object>();
		listDisplay.setBackground(Color.white);
		listDisplay.setPreferredSize(new Dimension(250, 250));
		updateListDisplay();

		topPanel.add(addButton);
		topPanel.add(removeButton);
		topPanel.add(editButton);
		bottomPanel.add(listDisplay);

		add(mainPanel);

		setTitle(title);
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public void addToList()
	{
		AddToListFrame frame = new AddToListFrame();
		frame.setVisible(true);
	}

	public void removeFromList()
	{
		if(listDisplay.getSelectedValue() != null)
		{
			Object objToRemove = listDisplay.getSelectedValue();
			infoList.remove(objToRemove);
			updateListDisplay();
		}
	}

	public void updateListDisplay()
	{	
		Object[] data = new Object[infoList.size()];

		for(int i = 0; i < infoList.size(); i++)
			data[i] = infoList.get(i);

		listDisplay.setListData(data);

		if(infoList.size() > 0)
			listDisplay.setSelectedIndex(0);
	}

	public void updateListDisplay(Object o)
	{	
		Object[] data = new Bill[infoList.size()];

		for(int i = 0; i < infoList.size(); i++)
			data[i] = infoList.get(i);

		listDisplay.setListData(data);
		listDisplay.setSelectedValue(o, true);
	}

	private void editBill()
	{
		if(listDisplay.getSelectedValue() != null)
		{
			Object objToEdit = listDisplay.getSelectedValue();
			AddToListFrame frame = new AddToListFrame(objToEdit);
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
				addToList();
			else if (buttonText.equals("-"))
				removeFromList();
			else if(buttonText.equals("Edit"))
				editBill();
		}
	}	//end ButtonListener

	private class AddToListFrame extends JFrame
	{
		private JPanel addToListMainPanel, topPanel, topLPanel, topRPanel, bottomPanel;
		private JButton addButton, cancelButton;

		public AddToListFrame()
		{	
			initComponents();
		}

		public AddToListFrame(Object o)
		{	
		}

		private void initComponents()
		{
			setSize(500, 500);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
		}

		private abstract class AddInfoButtonListener implements ActionListener
		{
			private AddToListFrame openFrame;

			public AddInfoButtonListener(AddToListFrame openFrame)
			{
				this.openFrame = openFrame;
			}
		}
	}	//end addButtonListener
}	//end AddToListFrame
//end BillManagementFrame

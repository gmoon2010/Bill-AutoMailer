import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class SendEmailFrame extends JFrame
{
	private JPanel mainPanel;
	private JTextField subjectField, toField, fromField;
	private JLabel subjectFieldLabel, toFieldLabel, fromFieldLabel;
	
	public SendEmailFrame()
	{
		initComponents();
	}
	
	private void initComponents()
	{
		mainPanel = new JPanel();
		
		subjectField = new JTextField();
		toField = new JTextField();
		fromField = new JTextField();
		
		subjectFieldLabel = new JLabel("Subject: ");
		subjectFieldLabel.setLabelFor(subjectField);
		
		toFieldLabel = new JLabel("To: ");
		toFieldLabel.setLabelFor(toField);
		
		fromFieldLabel = new JLabel("From: ");
		fromFieldLabel.setLabelFor(fromField);
		
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(mainPanel);
	}
}

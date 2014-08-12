import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TemplateManagementFrame extends JFrame
{
	private ArrayList<Template> templateList;
	
	public TemplateManagementFrame(ArrayList<Template> templateList)
	{
		this.templateList = templateList;
		
		setTitle("Template Management");
	}
}

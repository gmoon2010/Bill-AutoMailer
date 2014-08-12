import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class BillAutoMailer 
{
	private static final Thread MAIN_THREAD = Thread.currentThread();
	private static final String FILE_LOC = "/Users/gregorymoon/Desktop/info.ser";
	private static ArrayList<Bill> billList;
	private static ArrayList<Contact> contactList;
	private static ArrayList<Template> templateList;

	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		billList = new ArrayList<Bill>();
		contactList = new ArrayList<Contact>();
		templateList = new ArrayList<Template>();

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				try {
					writeInfo();
					MAIN_THREAD.join();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		readInfo();
		
		InfoManagementFrame frame = new InfoManagementFrame(billList, contactList, templateList);

		frame.setVisible(true);
	}

	@SuppressWarnings({ "unchecked" })
	private static void readInfo() throws IOException, ClassNotFoundException
	{
		File inFile = new File(FILE_LOC);

		if(inFile.exists())
		{
			ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(inFile));

			billList = (ArrayList<Bill>) inStream.readObject();
			contactList = (ArrayList<Contact>) inStream.readObject();
			templateList = (ArrayList<Template>) inStream.readObject();

			inStream.close();
		}
		else
		{
			inFile.createNewFile();
		}
		
	}

	private static void writeInfo() throws IOException
	{
		File outFile = new File(FILE_LOC);

		if(outFile.exists())
		{
			ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(outFile));
			
			outStream.writeObject(billList);
			outStream.writeObject(contactList);
			outStream.writeObject(templateList);
					
			outStream.close();
		}
		else
		{
			outFile.createNewFile();
		}
	}
}	//	end Main

package jms;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.management.MalformedObjectNameException;
import javax.swing.JFrame;

import ducvp.frames.MainFrame;

public class JMSSubscriber implements MessageListener{
	public MainFrame mainFrame;
	
	public JMSSubscriber(MainFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
	}

	@Override
	public void onMessage(Message message) {
		echo("New song message has been received.");
		try {
			mainFrame.updateTable();
		} catch (MalformedObjectNameException | IOException | JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void echo(String msg) {
        System.out.println(msg + "\n");
    }
}

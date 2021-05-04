package ducvp.main;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.management.MalformedObjectNameException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;

import ducvp.frames.MainFrame;
import jms.JMSSubscriber;
import model.SongDTO;
import service.SongDAOMbean;

public class App {
	SongDAOMbean mbeanProxy;
	Context namingContext = null;
	JMSContext context = null;
	
	public App(SongDAOMbean mbeanProxy) {
		super();
		this.mbeanProxy = mbeanProxy;
	}

//	 Create ActiveMQ connection
	public void listenMessage(MainFrame mainFrame) throws JMSException, NamingException {
//		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("https://192.168.107.49:61616");
//		Connection connection = connectionFactory.createConnection("admin", "admin");
		final Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");
		env.put(Context.PROVIDER_URL, "http-remoting://192.168.107.49:8080");
		env.put(Context.SECURITY_PRINCIPAL, "vpduc");
		env.put(Context.SECURITY_CREDENTIALS, "123456789");
		namingContext = new InitialContext(env);
		ConnectionFactory connectionFactory = (ConnectionFactory) namingContext
				.lookup("jms/RemoteConnectionFactory");
		Connection connection = connectionFactory.createConnection("vpduc","vpduc");
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("song-added-event");
		MessageConsumer client = session.createConsumer(topic);
		client.setMessageListener(new JMSSubscriber(mainFrame));
		connection.start();
//		connection.close();
	}

	public synchronized void addSong(SongDTO dto) {
		//getProxy();
		mbeanProxy.add(dto);
	}

	public List<SongDTO> getSongs() throws MalformedObjectNameException, IOException {
		//getProxy();
		return mbeanProxy.findAll();
	}

	public synchronized void updateSong(SongDTO dto) throws MalformedObjectNameException, IOException {
		//getProxy();
		mbeanProxy.update(dto);

	}

	public synchronized void deleteSong(int id) {
		//getProxy();
		mbeanProxy.delete(id);

	}
}

package utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import service.SongDAOMbean;

public class JMXUtils {
	static SongDAOMbean mbeanProxy;
	static JMXConnector jmxc;
	static MBeanServerConnection mbsc;
	
	public SongDAOMbean getProxy() throws IOException, MalformedObjectNameException {
//		Create an RMI connector client and " + "connect it to the RMI connector server
		String urlString = System.getProperty("jmx.service.url", "service:jmx:remote+http://192.168.107.49:8080");
		JMXServiceURL url = new JMXServiceURL(urlString);
		Map<String, String[]> env = new HashMap<String, String[]>();
		String[] credentials = { "vpduc", "vpduc" };
		env.put(JMXConnector.CREDENTIALS, credentials);
		if (jmxc == null) {
			jmxc = JMXConnectorFactory.connect(url);
		}
		if (mbsc == null) {
			mbsc = jmxc.getMBeanServerConnection();
		}
		ObjectName mbeanName = new ObjectName("SongManager:name=SongManager");
		if (mbeanProxy == null) {
			mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, SongDAOMbean.class, true);
		}
		return mbeanProxy;
	}
}

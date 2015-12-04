package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSPublish {
	private static final int WAITING_MSG = 15;
	private static final String JMS_QUEUE_IN = "jms/JAADTopic";
	private static final String JMS_CONNFACT = "jms/TopicCF";
	private static final Logger LOG = Logger.getLogger(JMSPublish.class);
	
	public static void main(String[] args) {
		try {
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory)ctx.lookup(JMS_CONNFACT);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination topic = (Destination)ctx.lookup(JMS_QUEUE_IN);
			connection.start();
			
			MessageProducer producer = session.createProducer(topic);
			ObjectMessage msg = session.createObjectMessage();
			Oferta oferta = new Oferta("Recien llegado", 20.0, "Televisor HD 30p");
			msg.setObject(oferta);
			producer.send(msg);
			
			LOG.info("Mensaje enviado:" + msg);
			
			producer.close();
			connection.close();
			
			System.exit(0);//Retirame
		} catch (Exception ex) {
			LOG.error("Erro al enviar mensaje", ex);
		}
	}
}

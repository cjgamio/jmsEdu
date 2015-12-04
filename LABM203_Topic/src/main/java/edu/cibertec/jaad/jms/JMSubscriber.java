package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSubscriber implements MessageListener{
	private static final String JMS_QUEUE_IN = "jms/JAADTopic";
	private static final String JMS_CONNFACT = "jms/TopicCF";
	private static final Logger LOG = Logger.getLogger(JMSubscriber.class);
	
	private Session session;
	private String id;
	
	public JMSubscriber(String id){
		this.id = id;
	}
	
	public void start(){
		try {
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory)ctx.lookup(JMS_CONNFACT);
			Connection connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination colaIn = (Destination)ctx.lookup(JMS_QUEUE_IN);
			connection.start();
			
			MessageConsumer consumer = session.createConsumer(colaIn);
			
			consumer.setMessageListener(this);
			
			LOG.info("Esperando por mensaje...");
		} catch (Exception ex) {
			LOG.error("Error al iniciar el lector", ex);
		}
	}
	@Override
	public void onMessage(Message msg) {
		try {
			ObjectMessage msgReq = (ObjectMessage)msg;
			LOG.info("[" + id + "]Msg=[" + msgReq.getObject() + "]");
		} catch (Exception ex) {
			LOG.error("Error al recibir el mensaje", ex);
		}
	}

}

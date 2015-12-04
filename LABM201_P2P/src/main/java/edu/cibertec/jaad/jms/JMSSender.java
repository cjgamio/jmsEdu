package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSSender {
	private static final Logger LOG = LoggerFactory.getLogger(JMSSender.class);
	private static String CNF_NAME = "jms/QueueCF";
	private static String QUEUE_NAME = "jms/JAADQueue";
	
	public static void main(String[] args) {
		try{
			Context ctx = new InitialContext();
			ConnectionFactory cnFactory = (ConnectionFactory)ctx.lookup(CNF_NAME);
			Destination destination = (Destination)ctx.lookup(QUEUE_NAME);
			Connection connection = cnFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			
			MessageProducer producer = session.createProducer(destination);
			//TextMessage message = session.createTextMessage("Hola JMS");
			ObjectMessage message = session.createObjectMessage();
			message.setObject(new Profesor("Manuel Rivas", "13245678"));
			producer.send(message);
			producer.close();
			session.close();
			connection.close();

			//BORRAME, NO hacer esto
			System.exit(0);
		}catch(Exception ex){
			LOG.error("Error al enviar al mensaje", ex);
		}
	}
}

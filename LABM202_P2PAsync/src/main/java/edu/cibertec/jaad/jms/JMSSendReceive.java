package edu.cibertec.jaad.jms;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSSendReceive {
	private static final int WAITING_MSG = 15;
	private static final String JMS_QUEUE_IN = "jms/QueueIN";
	private static final String JMS_QUEUE_OUT = "jms/QueueOUT";
	private static final String JMS_CONNFACT = "jms/QueueCFA";
	private static final Logger LOG = Logger.getLogger(JMSSendReceive.class);
	
	public static void main(String[] args) {
		try{
			Context ctx = new InitialContext();
			ConnectionFactory factory = (ConnectionFactory)ctx.lookup(JMS_CONNFACT);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			Destination colaIn = (Destination)ctx.lookup(JMS_QUEUE_IN);
			Destination colaOut = (Destination)ctx.lookup(JMS_QUEUE_OUT);
			connection.start();
			
			//Enviando mensaje
			MessageProducer producer = session.createProducer(colaIn);
			TextMessage msgReq = session.createTextMessage("Realizar recarga");
			String id = UUID.randomUUID().toString();
			msgReq.setJMSCorrelationID(id);
			msgReq.setJMSReplyTo(colaOut);
			msgReq.setStringProperty("OPERACION", "Recarga");
			producer.send(msgReq);
			
			//Recibiendo la respuesta
			String selector = "JMSCorrelationID = '" + id + "aaa'";
			MessageConsumer consumer = session.createConsumer(colaOut, selector);
			LOG.info("Esperando " + WAITING_MSG + "seg.");
			TextMessage msgResp = (TextMessage)consumer.receive(WAITING_MSG*1000);
			String result = msgResp == null ? "SIN_RESPUESTA" : msgResp.getText();
			LOG.info("Response=[" + result + "]");
			LOG.info("Message=" + msgResp);
			
			producer.close();
			consumer.close();
			connection.close();
			
			System.exit(0);
		}catch(Exception ex){
			LOG.error("Error al enviar/recibir el mensaje", ex);
		}
	}
}

package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;

public class JMSReceiveSend implements MessageListener{
	private static final String JMS_QUEUE_IN = "jms/QueueIN";
	private static final String JMS_CONNFACT = "jms/QueueCFA";
	private static final Logger LOG = Logger.getLogger(JMSReceiveSend.class);
	
	private Session session;
	
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
			TextMessage msgReq = (TextMessage)msg;
			LOG.info("Recibido=[" + msg + "]");
			LOG.info("Operacion=[" + msg.getStringProperty("OPERACION") + "]");
			
			TextMessage msgResp = session.createTextMessage("OPERACION_OK");
			MessageProducer producer = session.createProducer(msgReq.getJMSReplyTo());
			msgResp.setJMSCorrelationID(msgReq.getJMSCorrelationID());
			producer.send(msgResp);
			producer.close();
			LOG.info("Mensaje enviado=" + msgResp);
		} catch (Exception ex) {
			LOG.error("Error al recibir el mensaje", ex);
		}
	}
	public static void main(String[] args) {
		JMSReceiveSend rs = new JMSReceiveSend();
		rs.start();
	}
}

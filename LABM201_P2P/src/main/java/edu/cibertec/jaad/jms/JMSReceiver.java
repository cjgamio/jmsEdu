package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JMSReceiver {
	private static final Logger LOG = LoggerFactory
			.getLogger(JMSReceiver.class);
	private static String CNF_NAME = "jms/QueueCF";
	private static String QUEUE_NAME = "jms/JAADQueue";

	public static void main(String[] args) {
		try {
			Context ctx = new InitialContext();
			ConnectionFactory cnFactory = (ConnectionFactory) ctx
					.lookup(CNF_NAME);
			Destination destination = (Destination) ctx.lookup(QUEUE_NAME);
			Connection connection = cnFactory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			connection.start();

			MessageConsumer consumer = session.createConsumer(destination);
			LOG.info("Enviando mensaje...");
			// TextMessage message = (TextMessage)consumer.receive();
			ObjectMessage message = (ObjectMessage) consumer.receive(2000);
			if (message != null) {
				LOG.info("Mesaje recibido=[" + message + "]");
				Profesor profesor = (Profesor) message.getObject();
				LOG.info("Profesor recibido=[{}]", profesor);
				consumer.close();
				session.close();
				connection.close();
			}

			// BORRAME, NO hacer esto
			LOG.info("ejecucion terminada");
			System.exit(0);
		} catch (Exception ex) {
			LOG.error("Error al enviar al mensaje", ex);
		}
	}
}

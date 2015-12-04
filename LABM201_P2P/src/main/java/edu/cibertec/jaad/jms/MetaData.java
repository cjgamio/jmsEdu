package edu.cibertec.jaad.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.ConnectionMetaData;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetaData {
	private static final Logger LOG = LoggerFactory.getLogger(MetaData.class);
	public static void main(String[] args) {
		try{
			Context ctx = new InitialContext();
			ConnectionFactory cnFactory = 
					(ConnectionFactory)ctx.lookup("jms/QueueCF");
			Connection connection = cnFactory.createConnection();
			ConnectionMetaData metadata = connection.getMetaData();
			
			LOG.info("JMS Version:" + metadata.getJMSMajorVersion()
					+ "." + metadata.getJMSMinorVersion());
			LOG.info("JMS Provider:" + metadata.getJMSProviderName());
			LOG.info("JMS Provider Version:" + metadata.getProviderMajorVersion()
					+ "." + metadata.getProviderMinorVersion());
			connection.close();
			//NUNCA poner, solo para este Ejemplo
			System.exit(0);
		}catch(Exception ex){
			LOG.error("Error al obtener la metadata", ex);
		}
	}
}

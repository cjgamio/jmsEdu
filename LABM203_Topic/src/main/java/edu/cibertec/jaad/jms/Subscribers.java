package edu.cibertec.jaad.jms;

public class Subscribers {
	public static void main(String[] args) {
		int size = 3;
		for (int i = 0; i < size; i++) {
			JMSubscriber subs = new JMSubscriber("SUBS-" + i);
			subs.start();
		}
	}
}

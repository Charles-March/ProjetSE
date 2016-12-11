package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	
	private String mon_message;
	public static final MessageX CONDITION_ARRET = new MessageX("STOP !");
	
	public MessageX(String contenu) {
		// TODO Auto-generated constructor stub
		super();
		mon_message = contenu;
	}
	
	public String toString(){
		return mon_message;
	}

}

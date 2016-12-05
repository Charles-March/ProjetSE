package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	
	public String mon_message;
	
	public MessageX(String contenu) {
		// TODO Auto-generated constructor stub
		super();
		mon_message = contenu;
	}
	
	public String toString(){
		return mon_message;
	}

}

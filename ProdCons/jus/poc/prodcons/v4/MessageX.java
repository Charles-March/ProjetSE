package jus.poc.prodcons.v4;

import jus.poc.prodcons.Message;

public class MessageX implements Message {
	
	private String mon_message;
	private int nombreDeRepetition;
	
	public MessageX(String contenu) {
		// TODO Auto-generated constructor stub
		super();
		mon_message = contenu;
	}
	
	public void setNbExemplaire(int nbExemplaire){nombreDeRepetition = nbExemplaire;}
	public int getNbExemplaire(){return nombreDeRepetition;}
	
	public String toString(){
		return mon_message;
	}

}

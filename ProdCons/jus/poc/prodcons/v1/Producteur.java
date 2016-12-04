package jus.poc.prodcons.v1;

import java.util.LinkedList;
import java.util.List;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private int nbMessagesADeposer;
	private List<Message> messages;
	private Tampon tampon;
	
	public Producteur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement,Tampon tp)
			throws ControlException {
		super(typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		nbMessagesADeposer = (new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement)).next();
		messages = new LinkedList<Message>();
		for(int i=0;i<nbMessagesADeposer;i++){
			messages.add(new MessageX());
		}
		tampon = tp;
	}
	
	@Override
	public void run(){
		for(int i=0; i<nbMessagesADeposer; i++){
			try {
				tampon.put(this,messages.get(i));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	//nombre de messages que le producteur doit produire 
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesADeposer;
	}

}

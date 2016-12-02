package jus.poc.prodcons.v1;

import java.util.LinkedList;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Message;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {

	private int id;
	private int nbMessagesTraites;
	private LinkedList<Message> msgs;
	private Tampon tp;
	private Observateur obs;
	
	public Consommateur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, Tampon tps)
			throws ControlException {
		super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		id = identification();
		msgs = new LinkedList<Message>();
		this.tp=tps;
		obs = observateur;
		nbMessagesTraites = 0;
	}
	
	public long getId(){return id;}
	
	public void consomme(Message message){
		nbMessagesTraites++;
		msgs.add(message);
	}
	
	@Override
	public void run(){
			try {
				obs.consommationMessage(this, tp.get(this),this.moyenneTempsDeTraitement());
				System.out.println("Consommé !");
				nbMessagesTraites++;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@Override
	//nombre de messages que le consommateur a deja traite
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesTraites;
	}

}

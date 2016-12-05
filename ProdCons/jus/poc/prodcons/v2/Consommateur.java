package jus.poc.prodcons.v2;

import java.util.LinkedList;
import java.util.List;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {

	private int nbMessagesTraites;
	private ProdCons tampon;
	private List<MessageX> messagesLus;
	
	public Consommateur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tp)
			throws ControlException {
		super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tp;
		nbMessagesTraites = 0;
		messagesLus = new LinkedList<MessageX>();
	}
	
	public List<MessageX> getConsommes(){return messagesLus;}
	
	@Override
	public void run(){
	}

	@Override
	//nombre de messages que le consommateur a deja traite
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesTraites;
	}

}

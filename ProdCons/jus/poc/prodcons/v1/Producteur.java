package jus.poc.prodcons.v1;

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
	private Observateur prodObservateur;
	private List<Message> messages;
	
	public Producteur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement,Tampon tps)
			throws ControlException {
		super(typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		prodObservateur = observateur;
		nbMessagesADeposer = (new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement)).next();
		for(int i=0;i<nbMessagesADeposer;i++){
			messages.add(new MessageX());
		}
	}
	
	public int getNbDepot(){return nbMessagesADeposer;}
	public void setNbDepot(int i){nbMessagesADeposer = i;}
	
	public void Depot() throws ControlException{
		prodObservateur.depotMessage(this,messages.get(0));
		messages.remove(0);
	}
	
	@Override
	public void run(){
		
	}
	
	@Override
	//nombre de messages que le producteur doit produire 
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return messages.size();
	}

}

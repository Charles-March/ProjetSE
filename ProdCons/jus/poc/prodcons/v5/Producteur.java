package jus.poc.prodcons.v5;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private int nbMessagesADeposer;
	public List<MessageX> messages;
	private ProdCons tampon;
	
	public Producteur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement,ProdCons tp)
			throws ControlException {
		super(typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		nbMessagesADeposer = (new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement)).next();
		messages = new LinkedList<MessageX>();
		for(int i=0;i<nbMessagesADeposer;i++){
			messages.add(new MessageX("Ceci est le message n�"+(i+1)+" depose par le producteur "+identification()));
		}
		tampon = tp;
	}
	
	@Override
	public void run(){
		try {
			//tampon.debutProduction();
			System.out.println("Arriv�e dans le try du producteur");
			for(int i=0; i<nbMessagesADeposer; i++){
				observateur.productionMessage(this, messages.get(i), moyenneTempsDeTraitement);
				sleep(200);
				tampon.vide.P();
				tampon.mutex.P();
				tampon.put(this,messages.get(i));
				observateur.depotMessage(this, messages.get(i));
				tampon.mutex.V();
				tampon.plein.V();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//tampon.finProduction();
	}
		
	@Override
	//nombre de messages que le producteur doit produire 
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesADeposer;
	}

}

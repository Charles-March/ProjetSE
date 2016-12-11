package jus.poc.prodcons.v5;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons.v5.MessageX;

public class Consommateur extends Acteur implements _Consommateur {

	private int nbMessagesTraites;
	private ProdCons tampon;
	private List<MessageX> messagesLus;
	private boolean etat = false;
	private Semaphore plein;
	private Semaphore vide;
	public Semaphore mutex;
	
	public Consommateur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tp)
			throws ControlException {
		super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tp;
		nbMessagesTraites = 0;
		messagesLus = new LinkedList<MessageX>();
		vide = tp.vide;
		plein = tp.plein;
		mutex = tp.mutexConso;
	}
	
	public List<MessageX> getConsommes(){return messagesLus;}
	
	public void arret(){etat = false;}
	
	@Override
	public void run(){
		etat = true;
		MessageX reception;
		while(etat){
			try {
				//tampon.debutConsommation();
				sleep(200);
				plein.acquire();
				mutex.acquire();
				reception = (MessageX)tampon.get(this);
				if(reception == null){
					arret();
					System.out.println("adieu !");
				}
				else{
					System.out.println("TU ne rentres pas l� wtf?");
					messagesLus.add(reception);
					observateur.consommationMessage(this, messagesLus.get(messagesLus.size()-1), moyenneTempsDeTraitement);
					nbMessagesTraites++;
					System.out.println(reception);
					observateur.retraitMessage(this, reception);
				}
				mutex.release();
				vide.release();
				//tampon.finConsommation();
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
	//nombre de messages que le consommateur a deja traite
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesTraites;
	}

}

package jus.poc.prodcons.v4;

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
	private List<MessageX> messages;
	private ProdCons tampon;
	private Semaphore plein;
	private Semaphore vide;
	private Semaphore mutex;
	public Semaphore activite;
	
	public Producteur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement,ProdCons tp, int nbExemplaireMoyen, int deviationNbExemplaire)
			throws ControlException {
		super(typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		nbMessagesADeposer = (new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement)).next();
		messages = new LinkedList<MessageX>();
		for(int i=0;i<nbMessagesADeposer;i++){
			messages.add(new MessageX("Ceci est le message n°"+(i+1)+" depose par le producteur "+identification()));
			messages.get(i).setNbExemplaire((new Aleatoire(nbExemplaireMoyen, deviationNbExemplaire)).next());
			//messages.get(i).setNbExemplaire(3);
		}
		tampon = tp;
		vide = tp.vide;
		plein = tp.plein;
		mutex = tp.mutexDepot;
		activite = new Semaphore(1);
	}
    
	@Override
	public void run(){
		for(int i=0; i<nbMessagesADeposer; i++){
			try {
		//		System.out.println(Thread.currentThread().getName()+" hello!" + i);
				sleep(200);
				System.out.println("vide");
				vide.acquire();
				System.out.println("activite");
				activite.acquire();
				System.out.println("mutex");
				mutex.acquire();
				tampon.put(this,messages.get(i));
				
				mutex.release();
				
				if(messages.get(i).getNbExemplaire() == 0){
					activite.release();
				}
				tampon.plein.release();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//	System.out.println(this.getName()+ " a plus");
	}
	
	@Override
	//nombre de messages que le producteur doit produire 
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesADeposer;
	}

}

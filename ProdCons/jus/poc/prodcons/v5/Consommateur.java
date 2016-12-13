package jus.poc.prodcons.v5;

import java.util.LinkedList;
import java.util.List;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Consommateur;

public class Consommateur extends Acteur implements _Consommateur {

	private int nbMessagesTraites;
	private ProdCons tampon;
	private List<MessageX> messagesLus;
	private boolean etat = false;
	private int tempsDeTraitement;
	private Aleatoire alea;
	
	public Consommateur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tp)
			throws ControlException {
		super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tp;
		nbMessagesTraites = 0;
		messagesLus = new LinkedList<MessageX>();
		alea = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
	}
	
	public List<MessageX> getConsommes(){return messagesLus;}
	
	public void arret(){etat = false;}
	
	@Override
	public void run(){
		etat = true;
		MessageX reception;
		System.out.println("debut "+getName());
		while(etat){
			try {
				//tampon.plein.P();
				//tampon.mutexOut.P();
				try{
					//System.out.println("consommateur avant lock");
					//tampon.lock.lock();
					//System.out.println("consommateur entree get");
					//while(tampon.enAttente() == 0) tampon.plein.await();
					reception = (MessageX)tampon.get(this);
					/*if(reception == null){
						arret();
					}
					else{*/
					if(reception != null){
						observateur.retraitMessage(this, reception);
						sleep(alea.next()*50);
						messagesLus.add(reception);
						observateur.consommationMessage(this, messagesLus.get(messagesLus.size()-1), tempsDeTraitement);
						nbMessagesTraites++;
					}
					//tampon.vide.signal();
					//System.out.println("consommateur sortie get");
					//tampon.lock.unlock();
					//System.out.println("après le lock consommateur");
					//tampon.mutexOut.V();
					//tampon.vide.V();
				}catch (ControlException e){
					e.printStackTrace();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("fin "+getName());
	}

	@Override
	//nombre de messages que le consommateur a deja traite
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesTraites;
	}

}

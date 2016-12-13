package jus.poc.prodcons.v6;

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
	private Aleatoire alea;
	private MonObservateur obs;
	
	public Consommateur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement, ProdCons tp, MonObservateur observateur2)
			throws ControlException {
		super(typeConsommateur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		tampon = tp;
		nbMessagesTraites = 0;
		messagesLus = new LinkedList<MessageX>();
		alea = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		obs = observateur2;
	}
	
	public List<MessageX> getConsommes(){return messagesLus;}
	
	public void arret(){etat = false;}
	
	@Override
	public void run(){
		etat = true;
		MessageX reception;
		while(etat){
			try {
				tampon.plein.P();
				tampon.mutexOut.P();
				reception = (MessageX)tampon.get(this);
				if(reception == null){
					arret();
				}
				else{
					sleep(alea.next()*50);
					observateur.retraitMessage(this, reception);
					obs.retraitMessage(this, reception);
					messagesLus.add(reception);
					observateur.consommationMessage(this, reception, moyenneTempsDeTraitement);
					obs.consommationMessage(this, reception, moyenneTempsDeTraitement);
					nbMessagesTraites++;
				}
				tampon.mutexOut.V();
				tampon.vide.V();
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

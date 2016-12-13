package jus.poc.prodcons.v3;

import java.util.LinkedList;
import java.util.List;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Aleatoire;
import jus.poc.prodcons.ControlException;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons._Producteur;

public class Producteur extends Acteur implements _Producteur {

	private int nbMessagesADeposer;
	private List<MessageX> messages;
	private ProdCons tampon;
	private Aleatoire alea;
	
	public Producteur(Observateur observateur, int moyenneTempsDeTraitement, int deviationTempsDeTraitement,ProdCons tp)
			throws ControlException {
		super(typeProducteur, observateur, moyenneTempsDeTraitement, deviationTempsDeTraitement);
		// TODO Auto-generated constructor stub
		messages = new LinkedList<MessageX>();
		tampon = tp;
		nbMessagesADeposer = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		alea = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
	}
	
	@Override
	public void run(){
		for(int i=0; i<nbMessagesADeposer; i++){
			try {
				messages.add(new MessageX("message n°"+i+"-"+identification()));
				sleep(alea.next()*50);
				observateur.productionMessage(this, messages.get(i), moyenneTempsDeTraitement);
				tampon.vide.P();
				tampon.mutexIn.P();
				tampon.put(this,messages.get(i));
				observateur.depotMessage(this, messages.get(i));
				tampon.mutexIn.V();
				tampon.plein.V();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(getName()+" fini son execution");
	}
	
	@Override
	//nombre de messages que le producteur doit produire 
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesADeposer;
	}

}

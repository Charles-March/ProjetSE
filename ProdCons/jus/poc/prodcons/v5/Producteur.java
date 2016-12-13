package jus.poc.prodcons.v5;

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
		nbMessagesADeposer = (new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement)).next();
		messages = new LinkedList<MessageX>();
		tampon = tp;
		nbMessagesADeposer = Aleatoire.valeur(moyenneTempsDeTraitement, deviationTempsDeTraitement);
		alea = new Aleatoire(moyenneTempsDeTraitement, deviationTempsDeTraitement);
	}
	
	@Override
	public void run(){
		System.out.println("debut "+getName());
		for(int i=0; i<nbMessagesADeposer; i++){
			try {
				messages.add(new MessageX("message n°"+i+"-"+identification()));
				sleep(alea.next()*50);
				observateur.productionMessage(this, messages.get(i), moyenneTempsDeTraitement);
				//System.out.println("lock producteur");
				//tampon.lock.lock();
				//tampon.vide.P();
				//tampon.mutexIn.P();
				//System.out.println("producteur avant put");
				//while(tampon.enAttente() == tampon.taille()) tampon.vide.await();
				//System.out.println("après le while?");
				tampon.put(this,messages.get(i));
				observateur.depotMessage(this, messages.get(i));
				//tampon.plein.signal();
				//System.out.println("producteur après put");
				//tampon.lock.unlock();
				//System.out.println("sortie lock producteur");
				//tampon.mutexIn.V();
				//tampon.plein.V();
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
	//nombre de messages que le producteur doit produire 
	public int nombreDeMessages() {
		// TODO Auto-generated method stub
		return nbMessagesADeposer;
	}

}

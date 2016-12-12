package jus.poc.prodcons.v4;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Acteur;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {

	private int nbBuffer;
	private int caseDepot;
	private int caseConso;
	private MessageX[] buffer;
	public Semaphore plein;
	public Semaphore mutexDepot = new Semaphore(1);
	public Semaphore mutexConso = new Semaphore(1);
	public Semaphore vide;
	private List<_Acteur> listeDAttente;
	//private Semaphore listeDAttente;
	
	public ProdCons(int taille) {
		// TODO Auto-generated constructor stub		
		nbBuffer = taille;
		buffer = new MessageX[taille];
		caseDepot = 0;
		caseConso = 0;
		plein = new Semaphore(0);
		vide = new Semaphore(taille);
		listeDAttente = new LinkedList<_Acteur>();
		//listeDAttente = new Semaphore(1);
	}

	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		int reste = (caseDepot - caseConso)%nbBuffer;
		if(reste == 0 || buffer[caseDepot] != null) reste = nbBuffer;
		return reste;
	}
	
	@Override
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		MessageX sortie;
		sortie = buffer[caseConso];
		if(buffer[caseConso]==null) return null;
		buffer[caseConso].setNbExemplaire(buffer[caseConso].getNbExemplaire()-1);
		//si tous les messages ont été lus alors on passe au suivant et on vide la case
		if(buffer[caseConso].getNbExemplaire() == 0){
		/*	if(!(listeDAttente.get(0) instanceof Producteur)){
				return sortie;
			}*/
			
			for(int i=0; i<listeDAttente.size(); i++){
				if(listeDAttente.get(i) instanceof Consommateur){
					((Consommateur)listeDAttente.get(i)).activite.release();
					listeDAttente.remove(i);
				}
			}
			buffer[caseConso] = null;
			caseConso = (++caseConso)%nbBuffer;
			((Producteur)listeDAttente.get(0)).activite.release();
			listeDAttente.remove(0);
			vide.release();
		}
		//on place notre consommateur dans la liste d'attente
		else{
			
			listeDAttente.add(arg0);
			plein.release();
		}
	/*	System.out.println("Listes des msgs :");
		for(int i=0;i<buffer.length;i++){
			System.out.println(buffer[i]!=null?buffer[i].toString():"");
		}
	*/
		return sortie;
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		buffer[caseDepot] = (MessageX) arg1;
	//	System.out.println(Thread.currentThread().getName()+" depot du message "+arg1);
		caseDepot = (++caseDepot)%nbBuffer;
		if(((MessageX) arg1).getNbExemplaire() > 1){
			listeDAttente.add(arg0);
		}
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

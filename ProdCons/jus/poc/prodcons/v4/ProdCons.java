package jus.poc.prodcons.v4;

import java.util.LinkedList;
import java.util.List;

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
	public monSemaphore plein, vide;
	public monSemaphore mutex = new monSemaphore(1);
	private List<_Acteur> listeDAttente;
	
	public ProdCons(int taille) {
		// TODO Auto-generated constructor stub		
		nbBuffer = taille;
		buffer = new MessageX[taille];
		caseDepot = 0;
		caseConso = 0;
		plein = new monSemaphore(0);
		vide = new monSemaphore(taille);
		listeDAttente = new LinkedList<_Acteur>();
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
		//synchronized(listeDAttente){
		if(buffer[caseConso].getNbExemplaire() == 0){
			for(int i=0; i<listeDAttente.size(); i++){
				if(listeDAttente.get(i) instanceof Consommateur){
					((Consommateur)listeDAttente.get(i)).activite.V();
					listeDAttente.remove(i);
				}
			}
			System.out.println("ma liste :");
			for(int i=0; i<listeDAttente.size(); i++){
				System.out.println(listeDAttente.get(i) + " "+i);
			}
			buffer[caseConso] = null;
			caseConso = (++caseConso)%nbBuffer;
			System.out.println(listeDAttente.get(0) + " indice 0");
			((Producteur)listeDAttente.get(0)).activite.V();
			listeDAttente.remove(0);
			//vide.V();
		}
		//on place notre consommateur dans la liste d'attente
		else{
			
			listeDAttente.add(arg0);
			vide.P();
			plein.V();
		}
		//}
		return sortie;
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		buffer[caseDepot] = (MessageX) arg1;
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

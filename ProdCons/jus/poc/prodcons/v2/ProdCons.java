package jus.poc.prodcons.v2;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {

	private int nbBuffer;
	private int caseDepot;
	private int caseConso;
	public MessageX[] buffer;
	
	public ProdCons(int taille) {
		// TODO Auto-generated constructor stub		
		nbBuffer = taille;
		buffer = new MessageX[taille];
		caseDepot = 0;
		caseConso = 0;
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
		//test si la case est vide = tampon vide
		while(buffer[caseConso] == null){
			try{
				wait();	//en attente d'un depot
			}
			catch (InterruptedException e){}
		}
		//on stock le message
		Message sortie = buffer[caseConso];
		//on vide la case
		buffer[caseConso] = null;
		caseConso = (caseConso+1) % nbBuffer;
		notifyAll();
		return sortie;
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		//le tampon est plein
		while(buffer[caseDepot] != null){
			try{
				wait();	//on attend qu'un message soit lu
			}
			catch (InterruptedException e){}
		}
		buffer[caseDepot] = (MessageX) arg1;
		caseDepot = (caseDepot+1) % nbBuffer;
		notifyAll();
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

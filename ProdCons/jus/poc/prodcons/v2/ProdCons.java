package jus.poc.prodcons.v2;

import java.util.concurrent.Semaphore;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {

	private int nbBuffer;
	private int caseDepot;
	private int caseConso;
	public MessageX[] buffer;
	public Semaphore plein;
	public Semaphore mutexDepot = new Semaphore(1);
	public Semaphore mutexConso = new Semaphore(1);
	public Semaphore vide;
	
	public ProdCons(int taille) {
		// TODO Auto-generated constructor stub		
		nbBuffer = taille;
		buffer = new MessageX[taille];
		caseDepot = 0;
		caseConso = 0;
		plein = new Semaphore(0);
		vide = new Semaphore(taille);
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
		Message sortie;
		sortie = buffer[caseConso];
		buffer[caseConso] = null;
		caseConso = (caseConso+1)%nbBuffer;
		return sortie;
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		buffer[caseDepot] = (MessageX) arg1;
		caseDepot = (caseDepot+1)%nbBuffer;
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

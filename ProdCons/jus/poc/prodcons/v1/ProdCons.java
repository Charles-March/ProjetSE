package jus.poc.prodcons.v1;

import java.util.concurrent.locks.ReentrantLock;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {

	private int nbBuffer;
	private int caseDepot;
	private int caseConso;
	private final ReentrantLock lockConso = new ReentrantLock();
	private final ReentrantLock lockProd = new ReentrantLock();
	private boolean bloque = false;
	
	public Message[] buffer;
	
	public ProdCons(int taille) {
		// TODO Auto-generated constructor stub		
		nbBuffer = taille;
		buffer = new Message[taille];
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
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		while(buffer[caseConso] == null) lockConso.wait();
		Message sortie = buffer[caseConso];
		buffer[caseConso] = null;
		if(caseConso == caseDepot) bloque = true;
		caseConso = (caseConso+1) % nbBuffer;
		if(bloque){
			bloque = false;
			lockProd.notify();
		}
		return sortie;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		while(buffer[caseDepot] != null) lockProd.wait();
		if(buffer[caseDepot] == null){
			buffer[caseDepot] = arg1;
			if(caseDepot == caseConso) bloque = true;
			caseDepot = (caseDepot+1) % nbBuffer;
		}
		if(bloque){
			bloque = false;
			lockConso.notify();
		}
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

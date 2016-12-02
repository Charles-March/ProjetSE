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
	private int nbConsomme;
	public Message[] buffer;
	
	public synchronized void consomme(){
		nbConsomme++;
	}
	
	public int getCons(){
		return nbConsomme;
	}
	
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
	public synchronized Message get(_Consommateur arg0) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		synchronized (lockConso){
			while(buffer[caseConso] == null) lockConso.wait();
		}
		Message sortie = buffer[caseConso];
		buffer[caseConso] = null;
		if(caseConso == caseDepot) bloque = true;
		caseConso = (caseConso+1) % nbBuffer;
		synchronized (lockProd){
			if(bloque){
				bloque = false;
				lockProd.notifyAll();
			}
		}
		return sortie;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("coucou");
		synchronized (lockProd){
			while(buffer[caseDepot] != null) lockProd.wait();
		}
		if(buffer[caseDepot] == null){
			buffer[caseDepot] = arg1;
			if(caseDepot == caseConso) bloque = true;
			caseDepot = (caseDepot+1) % nbBuffer;
		}
		synchronized (lockConso){
			if(bloque){
				bloque = false;
				lockConso.notifyAll();
			}
		}
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

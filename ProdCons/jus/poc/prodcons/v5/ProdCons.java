package jus.poc.prodcons.v5;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {

	private int nbBuffer;
	private int caseDepot;
	private int caseConso;
	private int prod;
	private int cons;
	private MessageX[] buffer;
	public monSemaphore plein, vide;
	public monSemaphore mutex = new monSemaphore(1);
	private Lock lock = new ReentrantLock();
	private Condition prioProd = lock.newCondition();
	private Condition prioCons = lock.newCondition();
	
	public ProdCons(int taille) {
		// TODO Auto-generated constructor stub		
		nbBuffer = taille;
		buffer = new MessageX[taille];
		caseDepot = 0;
		caseConso = 0;
		prod = 0;
		cons = 0;
		plein = new monSemaphore(0);
		vide = new monSemaphore(taille);
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
		System.out.println("Get avant lock");
		lock.lock();
		System.out.println("Get apr�s lock");
		try{
			while(enAttente() == 0){
				System.out.println("IN while get avant verrou");
				prioCons.await();
				System.out.println("In while get after verrou");
			}
			Message sortie = buffer[caseConso];
			buffer[caseConso] = null;
			caseConso = (++caseConso)%nbBuffer;
			prioProd.signal();
			return sortie;
		} finally{lock.unlock();}
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		lock.lock();
		try{
			System.out.println("au boulot !!");
			while(enAttente() == nbBuffer) prioProd.await();
			buffer[caseDepot] = (MessageX) arg1;
			caseDepot = (++caseDepot)%nbBuffer;
			prioCons.signal();
		} finally{lock.unlock();}
	}
	
	public void debutProduction() throws InterruptedException{
		//prioCons.await();
		lock.lock();
		if(++prod == 1) prioProd.await();
		lock.unlock();
		//prioCons.signal();
	}
	
	public void finProduction(){
		lock.lock();
		if(--prod == 0) prioProd.signal();
		lock.unlock();
	}
	
	public void debutConsommation() throws InterruptedException{
		//prioProd.await();
		lock.lock();
		if(++cons == 1) prioCons.await();
		lock.unlock();
		//prioProd.signal();
	}
	
	public void finConsommation(){
		lock.lock();
		if(--cons == 0) prioCons.signal();
		lock.unlock();
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

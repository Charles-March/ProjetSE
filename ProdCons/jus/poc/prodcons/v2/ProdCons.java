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
	private Semaphore vide = new Semaphore(1);
	
	public ProdCons(int taille) {
		// TODO Auto-generated constructor stub		
		nbBuffer = taille;
		buffer = new MessageX[taille];
		caseDepot = 0;
		caseConso = 0;
		plein = new Semaphore(nbBuffer);
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
		//On s'empare du semaphore de consommation (qu'on appelle vide car il reste acquis si le message lu est le dernier disponible
		//soit si apres lecture le buffer est vide
		vide.acquire();
		//On protege notre variable caseConso afin que 2 consommateurs n'accedent pas a la meme case en meme temps
		//exclusion mutuelle
		mutexConso.acquire();
		sortie = buffer[caseConso];
		buffer[caseConso] = null;
		caseConso = (++caseConso)%nbBuffer;
		mutexConso.release();
		//Nouvelle place dispo dans le buffer on libère une place
		plein.release();
		//si le buffer est vide on ne rend pas la main aux prochain consommateurs, on les bloque
		if(enAttente()!=0)vide.release();
		return sortie;
	}

	@Override
	public synchronized void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		//On occupe une place dans le Semaphore = on depose un nouveau message donc il y a une place en moins de vide dans le buffer
		plein.acquire();
		//Protection de caseDepot = exclusion mutuelle
		mutexDepot.acquire();
		buffer[caseDepot] = (MessageX) arg1;
		caseDepot = (++caseDepot)%nbBuffer;
		mutexDepot.release();
		//on libere les consommateurs car un message viens d'etre pose
		vide.release();
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

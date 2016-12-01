package jus.poc.prodcons.v1;

import jus.poc.prodcons.Message;
import jus.poc.prodcons.Tampon;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;

public class ProdCons implements Tampon {

	private int nbProd;
	private int nbCons;
	private int nbBuffer;
	private int tempsMoyenProduction;
	private int deviationTempsMoyenProduction;
	private int tempsMoyenConsommation;
	private int deviationTempsMoyenConsommation;
	private int nombreMoyenDeProduction;
	private int deviationNombreMoyenDeProduction;
	private int nombreMoyenNbExemplaire;
	private int deviationNombreMoyenNbExemplaire;
	
	public boolean[] buffer = new boolean[nbBuffer];
	public Message[] messages = new Message[nbBuffer];
	
	public ProdCons() {
		// TODO Auto-generated constructor stub
		// Initialisation des variables globales depuis le xml
		
		for(int i=0; i<nbBuffer; i++){buffer[i] = false;}
	}

	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		int nbWaiting=0;
		while(buffer[nbWaiting] == true){nbWaiting++;}
		return nbWaiting;
	}

	// /!\ /!\ VARIABLES PARTAGEES PENSER AU LOCK /!\ /!\
	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		while(buffer[0] == false){arg0.wait();}	//Ou mettre le notify()? --> dans consommation?
		return null;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		while(buffer[nbBuffer-1] == true){arg0.wait();}	//notify()????
		buffer[nbBuffer-1] = true;
		messages[nbBuffer-1] = arg1;
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

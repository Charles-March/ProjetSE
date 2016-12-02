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
	private int caseDepot;
	private int caseConso;
	
	public Message[] buffer;
	
	public ProdCons() {
		// TODO Auto-generated constructor stub		
		buffer = new Message[nbBuffer];
		caseDepot = 0;
		caseConso = 0;
	}
	
	public void setTailleBuffer(int taille){nbBuffer = taille;}

	@Override
	public int enAttente() {
		// TODO Auto-generated method stub
		return ((caseDepot - caseConso)%nbBuffer);
	}

	// /!\ /!\ VARIABLES PARTAGEES PENSER AU LOCK /!\ /!\
	@Override
	public Message get(_Consommateur arg0) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		while(buffer[caseConso] == null)arg0.wait();
		Message sortie = buffer[caseConso];
		buffer[caseConso] = null;
		caseConso = (caseConso+1) % nbBuffer;
		return sortie;
	}

	@Override
	public void put(_Producteur arg0, Message arg1) throws Exception, InterruptedException {
		// TODO Auto-generated method stub
		if(buffer[caseDepot] == null){
			buffer[caseDepot] = arg1;
			caseDepot = (caseDepot+1) % nbBuffer;
		}
	}

	@Override
	public int taille() {
		// TODO Auto-generated method stub
		return nbBuffer;
	}

}

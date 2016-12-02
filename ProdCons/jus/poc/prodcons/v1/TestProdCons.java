package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import jus.poc.prodcons.Acteur;
import jus.poc.prodcons.Observateur;
import jus.poc.prodcons.Simulateur;

public class TestProdCons extends Simulateur {
	
	protected static int nbProd;
	protected static int nbCons;
	protected static int nbBuffer;
	protected static int tempsMoyenProduction;
	protected static int deviationTempsMoyenProduction;
	protected static int tempsMoyenConsommation;
	protected static int deviationTempsMoyenConsommation;
	protected static int nombreMoyenDeProduction;
	protected static int deviationNombreMoyenDeProduction;
	protected static int nombreMoyenNbExemplaire;
	protected static int deviationNombreMoyenNbExemplaire;
	
	protected List<Producteur> producteurs = new LinkedList<Producteur>();
	protected List<Consommateur> consommateurs = new LinkedList<Consommateur>();
	protected Observateur obs;
	protected ProdCons tampon;
	protected Acteur[] tousMesActeurs;

	public TestProdCons(Observateur observateur) {
		super(observateur);
		// TODO Auto-generated constructor stub
		obs = observateur;
	}

	 protected static void init(String file){
		 final class Properties extends java.util.Properties{
				private static final long serialVersionUID = 1L;
				public int get(String key){return Integer.parseInt(getProperty(key));}
				public Properties (String file){
					try {
						loadFromXML(ClassLoader.getSystemResourceAsStream(file));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		 Properties option = new Properties("jus/poc/prodcons/options/"+file);
		 nbProd = option.get("nbProd");
		 nbCons = option.get("nbCons");
		 nbBuffer = option.get("nbBuffer");
		 tempsMoyenProduction = option.get("tempsMoyenProduction");
		 deviationTempsMoyenProduction = option.get("deviationTempsMoyenProduction");
		 tempsMoyenConsommation = option.get("tempsMoyenConsommation");
		 deviationTempsMoyenConsommation = option.get("deviationTempsMoyenConsommation");
		 nombreMoyenDeProduction = option.get("nombreMoyenDeProduction");
		 deviationNombreMoyenDeProduction = option.get("deviationNombreMoyenDeProduction");
		 nombreMoyenNbExemplaire = option.get("nombreMoyenNbExemplaire");
		 deviationNombreMoyenNbExemplaire = option.get("deviationNombreMoyenNbExemplaire");
		 
	 }
	
	@Override
	protected void run() throws Exception {
		// TODO Auto-generated method stub
		init("options.xml");
		tampon = new ProdCons(nbBuffer);
		int i;
		int nbTotalDeMessagesADeposer = 0;
		int nbTotalDeMessagesTraites = 0;
		Random rand = new Random();
		for(i=0; i<nbProd; i++){
			producteurs.add(new Producteur(obs, tempsMoyenProduction, deviationTempsMoyenProduction, tampon));
			nbTotalDeMessagesADeposer += producteurs.get(i).nombreDeMessages();
		}
		for(i=0; i<nbCons; i++)consommateurs.add(new Consommateur(obs, tempsMoyenProduction, deviationTempsMoyenProduction, tampon));
		tousMesActeurs = new Acteur[nbProd+nbCons];
		for(i=0; i<nbProd; i++)tousMesActeurs[i] = producteurs.get(i);
		for(i=0; i<nbCons; i++)tousMesActeurs[i+nbProd] = consommateurs.get(i);
		i = rand.nextInt(nbProd+nbCons+1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestProdCons(new Observateur()).start();
	}

}

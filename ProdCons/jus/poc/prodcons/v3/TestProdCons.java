package jus.poc.prodcons.v3;

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
	protected List<Acteur> tousMesActeurs = new LinkedList<Acteur>();

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
	 
	 private int nbTotalDeMessagesTraites(List<Consommateur> l){
		 int res = 0;
		 for(int i=0; i<l.size(); i++){
			 res += l.get(i).nombreDeMessages();
		 }
		 return res;
	 }
	
	@Override
	protected void run() throws Exception {
		// TODO Auto-generated method stub
		init("options.xml");
		obs.init(nbProd,nbCons,nbBuffer);
		tampon = new ProdCons(nbBuffer);
		int i;
		int nbTotalDeMessagesADeposer = 0;
		Random rand = new Random();
		for(i=0; i<nbProd; i++){
			producteurs.add(new Producteur(obs, tempsMoyenProduction, deviationTempsMoyenProduction, tampon));
			obs.newProducteur(producteurs.get(i));
			nbTotalDeMessagesADeposer += producteurs.get(i).nombreDeMessages();
		}
		for(i=0; i<nbCons; i++){
			consommateurs.add(new Consommateur(obs, tempsMoyenProduction, deviationTempsMoyenProduction, tampon));
			obs.newConsommateur(consommateurs.get(i));
		}
		for(i=0; i<nbProd; i++)tousMesActeurs.add(producteurs.get(i));
		for(i=0; i<nbCons; i++)tousMesActeurs.add(consommateurs.get(i));
		while(nbTotalDeMessagesADeposer != nbTotalDeMessagesTraites(consommateurs)){
			if(tousMesActeurs.size() != 0){
				i = rand.nextInt(tousMesActeurs.size());
				Acteur monActeur = tousMesActeurs.get(i);
				if(monActeur instanceof Producteur){
					monActeur.start();
					tousMesActeurs.remove(i);
				}
				else if(monActeur instanceof Consommateur){
					monActeur.start();
					tousMesActeurs.remove(i);
				}
			}
		}
		for(i=0; i<nbCons; i++){
			tampon.plein.V();
			System.out.println(consommateurs.get(i).getConsommes().toString());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestProdCons(new Observateur()).start();
	}

}

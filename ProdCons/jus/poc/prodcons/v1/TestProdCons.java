package jus.poc.prodcons.v1;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;

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

	public TestProdCons(Observateur observateur) {
		super(observateur);
		// TODO Auto-generated constructor stub
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
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TestProdCons(new Observateur()).start();
	}

}

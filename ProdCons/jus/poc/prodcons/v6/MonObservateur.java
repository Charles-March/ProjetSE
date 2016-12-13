package jus.poc.prodcons.v6;

import jus.poc.prodcons.Message;
import jus.poc.prodcons._Consommateur;
import jus.poc.prodcons._Producteur;
@SuppressWarnings("unused")
public class MonObservateur {
	private boolean coherent;
	/* Indicateur du mode de fonctionnement 
	 * true : le controleur est en fonction 
	 * false : le controleur est inopérant
	 */
	private static String	ControlClass;
	
	
	//private Controleur controleur;
	private boolean operationnel;
	
	
	private boolean coherent(){
		 /*
		 * indicateur de cohérence du controle le controle 
		 * est cohérent s'il a été correctement initialisé
		 */
		
		return coherent;
	}
	
	public void init(int nbProducteurs, int nbConsommateurs, int nbBuffers){
	/*
	 * initialisation de l'observateur
	 
	 * Parameters:
		nbproducteurs - le nombre de producteurs
		nbconsommateurs - le nomnbre de consommateurs
		nbBuffers - le nombre de places du tampon

	 * Throws:
		ControlException - si un des paramètres a une valeur incohérente (négative ou nulle)
	 
	 * Requires :
		ArgumentsValides - nbproducteurs>0 && nbconsommateurs>0 && nbBuffers>0
	 */
		
	}
	
	
	public void	consommationMessage(_Consommateur c, Message m, int tempsDeTraitement){
		/*
		 * Evenement correspondant à la consommation d'un message

		 * Parameters:
			c - le consommateur à l'origine de la consommation
			m - le message consommé
			tempsDeTraitement - le temps de traitement (consommation) du message

		 * Throws:
			ControlException - si un des paramètres a une valeur incohérente (null)

		 * Requires :
			ArgumentsValides - c!=null && m!=null && tempsDeTraitement>0
			EtatControleurcoherent - coherent()
		*/
	}
		
	public void	depotMessage(_Producteur p, Message m){
		/*
		 * Evenement correspondant au dépot d'un message dans le tampon
		 
		 * Parameters:
			p - le producteur à l'origine du dépot
			m - le message déposé
			
		 * Throws:
			ControlException - si un des paramètres a une valeur incohérente (null)
		
		 * Requires :
			ArgumentsValides - p!=null && m!=null
			EtatControleurcoherent - coherent()
		*/
	}
	
	public void	newConsommateur(_Consommateur c){
		/*
		 * Evenement correspondant à la création d'un nouveau consommateur
		 
		 * Parameters:
			c - le consommateur créé
		 
		 * Throws:
			ControlException - si un des paramètres a une valeur incohérente (null)
		
		 * Requires :
			ArgumentsValides - c!=null
			EtatControleurcoherent - coherent()
		*/

	}
	
	public void newProducteur(_Producteur p){
		/*
		 * Evenement correspondant à la création d'un nouveau producteur
		 
		 * Parameters:
			p - le producteur créé
		
		 * Throws:
			ControlException - si un des paramètres a une valeur incohérente (null)
		
		 * Requires :
			ArgumentsValides - p!=null
			EtatControleurcoherent - coherent()
		*/

	}
	
	public void productionMessage(_Producteur p, Message m, int tempsDeTraitement){
		/*
		 * Evenement correspondant à la production d'un message
		 
		 * Parameters:
			p - le producteur à l'origine de la production
			m - le message produit
			tempsDeTraitement - le temps de traitement (production) du message
		
		 * Throws:
			ControlException - si un des paramètres a une valeur incohérente (null)
		 
		 * Requires :
			ArgumentsValides - p!=null && m!=null && tempsDeTraitement>0
			EtatControleurcoherent - coherent()
		 */	 
	}
	
	public void	retraitMessage(_Consommateur c, Message m){
		/*
		 * Evenement correspondant au retrait d'un message du tampon

		 * Parameters:
			c - le consommateur à l'origine du retrait
			m - le message retiré
		 * Throws:
			ControlException - si un des paramètres a une valeur incohérente (null)

		 * Requires :
			ArgumentsValides - c!=null && m!=null
			EtatControleurcoherent - coherent()
		*/		
	}
}

/**
 * 
 */
package model.factory;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Adresse;
import model.Plan;
import model.Troncon;

/**
 * @author Adrien Menella
 *
 */
public class FactoryPlan implements FactoryBase {
	
	private Plan plan = null;
	
	public Document getDomTree(String pathXml) {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();		
		    final Document domTree= builder.parse(new File(pathXml));
		    return domTree;
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retourne un objet Adresse avec les paramètres correspondants si cette nouvelle adresse n'a pas déjà été ajoutée à la liste d'adresses du plan
	 * retourne null en cas d'erreur
	 * @param id est l'id de l'adresse que l'on veut ajouter
	 * @param coordX est la coordonnée X de l'adresse que l'on veut ajouter
	 * @param coordY est la coordonnée Y de l'adresse que l'on veut ajouter
	 * @return
	 */
	public Adresse getAdresse(int id, int coordX, int coordY){
		// check if the given id does not exist in the adresses list and if coords are positive
		if(this.plan.getAdresseById(id)==null && coordX>=0 && coordY>=0){
			return new Adresse(id, coordX, coordY);
		}
		return null;
	}
	
	/**
	 * Retourne un nouvel objet Troncon avec les paramètres correspondants si son adresse de destination a été ajoutée à la liste d'adresses du plan
	 * retourne null en cas d'erreur
	 * @param nomRue est le nom de la rue correspondante au nouveau troncon
	 * @param vitesseMoyenne est la vitesse moyenne de la rue correspondante au nouveau troncon
	 * @param longueur est la longueur de la rue correspondante au nouveau troncon
	 * @param idDestination est l'id de l'adresse de destination de la rue correspondante au nouveau troncon
	 * @return
	 */
	public Troncon getTroncon(String nomRue, double vitesseMoyenne, double longueur, int idOrigine, int idDestination){
		// check parameters and if destination exists in in the adresses list
		Adresse origine = this.plan.getAdresseById(idOrigine);
		Adresse destination = this.plan.getAdresseById(idDestination);

		if(nomRue.length()!=0 && vitesseMoyenne>0 && longueur>0 && origine!=null && destination!=null){
			return new Troncon(nomRue, vitesseMoyenne, longueur, origine, destination);
		}
		return null;
	}
	
	public boolean checkNodeTypeAndName(Node currentNode, String correctNodeName) {
		if ( currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().equalsIgnoreCase(correctNodeName)) return true;
		return false;
	}
	
	/**
	 * vérifie si l'element considéré est valide syntaxiquement, par rapport à notre définition
	 * instancie un nouvel objet Adresse
	 * insère cet objet au sein de l'objet Plan
	 * 
	 * @param adresse
	 * @return
	 */
	private boolean ajouterAdresse(Element adresse){
		try {
            int idAdresse = Integer.parseInt(adresse.getAttribute("id"));
            int coordX = Integer.parseInt(adresse.getAttribute("x"));
            int coordY = Integer.parseInt(adresse.getAttribute("y"));
            
            // instantiate a new Adresse object with attributes given in the xml tag
        	Adresse newAdresse = this.getAdresse(idAdresse, coordX, coordY);
        	
        	// add the adresse to the list of the plan if it is not null
            if( newAdresse != null ){
            	this.plan.addAdresse(newAdresse);
            	return true;
            } else {
            	return false;
            }
        } catch (Exception e) {
        	
        	// s'il manque des paramï¿½tres pour créer une adresse dans le fichier xml on stoppe l'instanciation du plan
        	return false;
        }
	}
	
	/**
	 * vérifie si l'element considéré est valide syntaxiquement, par rapport à notre définition
	 * instancie un nouvel object Troncon
	 * insère cet objet au sein de l'objet Plan et l'ajoute en tant que Troncon sortant à adresseCourante
	 * 
	 * @param tronconSortant
	 * @param adresseCourante
	 */
	private void ajouterTroncon (Element tronconSortant, Adresse adresseCourante) {
		try {
    		String nomRue = tronconSortant.getAttribute("nomRue");
    		double vitesse = Double.parseDouble(tronconSortant.getAttribute("vitesse").replace(',', '.'));
    		double longueur = Double.parseDouble(tronconSortant.getAttribute("longueur").replace(',', '.'));
    		int idAdresseDestination = Integer.parseInt(tronconSortant.getAttribute("idNoeudDestination"));
    		
    		int idOrigine = adresseCourante.getId();
    		
	        Troncon newTroncon = this.getTroncon(nomRue, vitesse, longueur, idOrigine, idAdresseDestination);
	        
	        // add the Troncon to the list of the Plan and to the TronconsSortants list of the current Adresse if it is not null
            if( newTroncon != null ) {
            	this.plan.addTroncon(newTroncon);
            	adresseCourante.addTroncon(newTroncon);
            }
		} catch(Exception e) {
			
		}
	}
	
	/**
	 * Instancie et retourne un objet Plan à partir du fichier XML passé en paramètre
	 * retourne null en cas d'erreur
	 * @param pathXml
	 * @return
	 */
	public Plan getPlan(String pathXml) {
		
		try {
			// get the domTree of the XML file
			final Document domTree = this.getDomTree(pathXml);
			final Element racine = domTree.getDocumentElement();
			
			// instantiate the plan to a new Plan
			this.plan = new Plan();
					
			// get all Noeud tag from the xml file to instantiate the Adresse objects
			final NodeList adressesListe = racine.getChildNodes();
			final int nbAdresses = adressesListe.getLength();
			
			Node noeudAdresseCourante, noeudTronconSortantCourant;
			
		    // first reading, instantiate all addresses
		    for (int i = 0; i<nbAdresses; i++) {
		    	
		    	noeudAdresseCourante = adressesListe.item(i);
		    	
		    	// check if the current tag is a NODE and if it is called Noeud (ignoring the case)
		        if(checkNodeTypeAndName(noeudAdresseCourante, "Noeud")) {
		        	final Element adresse = (Element) noeudAdresseCourante;
		        	
		            if(!ajouterAdresse(adresse)) return null;
		            
		        }
		    }
		    
		    
		    // second reading, instantiate all Troncon and add tronconSortant to Adresse
		    for (int i = 0; i<nbAdresses; i++) {
		    	
		    	noeudAdresseCourante = adressesListe.item(i);
		    	
		    	// check if the current tag is a NODE and if it is called Noeud (ignoring the case)
		        if(checkNodeTypeAndName(noeudAdresseCourante, "Noeud")) {
		        	
		        	final Element adresse = (Element) adressesListe.item(i);
		            
		            // get the Adresse from the Adresse list of Plan by its Id
		            final Adresse adresseCourante = this.plan.getAdresseById(Integer.parseInt(adresse.getAttribute("id")));
		            
		            if( adresseCourante != null ) {
		            	
		            	// get all LeTronconSortant tag from the xml file to instantiate the Troncon objects
			            final NodeList tronconsSortantsListe = adresse.getElementsByTagName("LeTronconSortant");
					    final int nbTronconsSortants = tronconsSortantsListe.getLength();
						
					    // if the current Adresse does not have some Troncons we delete this Adresse from the list of the Plan object
					    if(nbTronconsSortants==0) this.plan.removeAdresse(adresseCourante);
					    
					    for(int j = 0; j<nbTronconsSortants; j++) {
					    	
					    	noeudTronconSortantCourant = tronconsSortantsListe.item(j);
					    	
					    	// check if the current tag is a NODE and if it is called LeTronconSortant (ignoring the case)
					    	if(checkNodeTypeAndName(noeudTronconSortantCourant, "LeTronconSortant")) {
					    		
					    		final Element tronconSortant = (Element) tronconsSortantsListe.item(j);
					    		
					    		ajouterTroncon(tronconSortant, adresseCourante);
						        
					    	}
					    }
					    // on enlève l'adresseCourante de la liste des adresses du plan si finalement celle ci ne possède aucun tronconSortant
				    	if(adresseCourante.getTronconsSortants().isEmpty()) this.plan.removeAdresse(adresseCourante);
		            }
		        }
		    }
		}catch(Exception e) {
				
		// si le fichier n'est pas bien formï¿½ on ne stoppe l'instanciation de la demande de livraisons
		return null;
	}
		// if the Adresse list of the Plan is empty return null
	    if(this.plan.getAdresses().isEmpty()) return null;
	    
	    return this.plan;
	    
	}
}

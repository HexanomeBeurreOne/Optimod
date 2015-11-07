/**
 * 
 */
package model;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Adrien Menella
 *
 */
public class FactoryPlan implements FactoryBase {
	
	private Plan plan = null;
	
	
	/**
	 * parse a xml file and return its domTree, return null otherwise 
	 */
	public Document getDomTree(String uriXml) {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();		
		    final Document domTree= builder.parse(new File(uriXml));
		    return domTree;
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return a new Adresse object with given parameters if it has not already been instantiated and added to the adresseList of plan
	 * return null otherwise
	 * @param id
	 * @param coordX
	 * @param coordY
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
	 * Return a new Troncon object with given parameters if its destination adresse has been added to the list of plan
	 * return null otherwise
	 * @param nomRue
	 * @param vitesseMoyenne
	 * @param longueur
	 * @param idDestination
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
	
	/**
	 * Instantiate and return a Plan object basing to the XML file passed in parameters
	 * @param uriXml
	 * @return
	 */
	public Plan getPlan(String uriXml) {
		
		try {
			// get the domTree of the XML file
			final Document domTree = this.getDomTree(uriXml);
			final Element racine = domTree.getDocumentElement();
			
			// instantiate the plan to a new Plan
			this.plan = new Plan();
					
			// get all Noeud tag from the xml file to instantiate the Adresse objects
			final NodeList adressesListe = racine.getChildNodes();
			final int nbAdresses = adressesListe.getLength();
			
		    // first reading, instantiate all addresses
		    for (int i = 0; i<nbAdresses; i++) {
		    	
		    	// check if the current tag is a NODE and if it is called Noeud (ignoring the case)
		        if(adressesListe.item(i).getNodeType() == Node.ELEMENT_NODE && adressesListe.item(i).getNodeName().equalsIgnoreCase("Noeud")) {
		            final Element adresse = (Element) adressesListe.item(i);
		            
		            try {
			            int idAdresse = Integer.parseInt(adresse.getAttribute("id"));
			            int coordX = Integer.parseInt(adresse.getAttribute("x"));
			            int coordY = Integer.parseInt(adresse.getAttribute("y"));
			            
			            // instantiate a new Adresse object with attributes given in the xml tag
		            	Adresse newAdresse = this.getAdresse(idAdresse, coordX, coordY);
		            	// add the adresse to the list of the plan if it is not null
			            if( newAdresse != null ){
			            	this.plan.addAdresse(newAdresse);
			            } else {
			            	System.err.println("Invalid parameters for Noeud tag #"+(Math.ceil(i/2))+" in the file "+uriXml);
			            	return null;
			            }
		            } catch (Exception e) {
		            	
		            	// s'il manque des param�tres pour cr�er une adresse dans le fichier xml on stoppe l'instanciation du plan
		            	System.err.println("Missing parameters for Noeud tag #"+(Math.ceil(i/2))+" in the file "+uriXml);
		            	return null;
		            }
		            
		            
		        }
		    }
		    
		    // second reading, instantiate all Troncon and add tronconSortant to Adresse
		    for (int i = 0; i<nbAdresses; i++) {
		    	
		    	// check if the current tag is a NODE and if it is called Noeud (ignoring the case)
		        if(adressesListe.item(i).getNodeType() == Node.ELEMENT_NODE && adressesListe.item(i).getNodeName().equalsIgnoreCase("Noeud")) {
		            final Element adresse = (Element) adressesListe.item(i);
		            
		            // get the Adresse from the Adresse list of Plan by its Id
		            final Adresse currentAdresse = this.plan.getAdresseById(Integer.parseInt(adresse.getAttribute("id")));
		            
		            if( currentAdresse != null ) {
		            	
		            	// get all LeTronconSortant tag from the xml file to instantiate the Troncon objects
			            final NodeList tronconsSortantsListe = adresse.getElementsByTagName("LeTronconSortant");
					    final int nbTronconsSortants = tronconsSortantsListe.getLength();
						
					    // if the current Adresse does not have some Troncons we delete this Adresse from the list of the Plan object
					    if(nbTronconsSortants==0) this.plan.removeAdresse(currentAdresse);
					    
					    for(int j = 0; j<nbTronconsSortants; j++) {
					    	
					    	// check if the current tag is a NODE and if it is called LeTronconSortant (ignoring the case)
					    	if(tronconsSortantsListe.item(j).getNodeType() == Node.ELEMENT_NODE) {
					    		final Element tronconSortant = (Element) tronconsSortantsListe.item(j);
					    		
					    		try {
						    		String nomRue = tronconSortant.getAttribute("nomRue");
						    		double vitesse = Double.parseDouble(tronconSortant.getAttribute("vitesse").replace(',', '.'));
						    		double longueur = Double.parseDouble(tronconSortant.getAttribute("longueur").replace(',', '.'));
						    		int idAdresseDestination = Integer.parseInt(tronconSortant.getAttribute("idNoeudDestination"));
						    		int idOrigine = currentAdresse.getId();
				                	System.out.println(nomRue + " " + vitesse + " "  + longueur + " "  + idOrigine + " "  + idAdresseDestination);
							        Troncon newTroncon = this.getTroncon(nomRue, vitesse, longueur, idOrigine, idAdresseDestination);
							        
							        // add the Troncon to the list of the Plan and to the TronconsSortants list of the current Adresse if it is not null
					                if( newTroncon != null ) {
					                	this.plan.addTroncon(newTroncon);
						                currentAdresse.addTroncon(newTroncon);
					                }
					                else {
					                	System.out.println(newTroncon);
					                }
					    		} catch(Exception e) {
					    			System.err.println("Missing parameters for LeTronconSortant tag #"+j+" of Noeud tag #"+(Math.ceil(i/2))+" in the file "+uriXml);
					    		}
						        
					    	}
					    }
		            }
		        }
		    }
		}catch(Exception e) {
				
		// si le fichier n'est pas bien form� on ne stoppe l'instanciation de la demande de livraisons
		System.err.println("The file "+uriXml+" is not well formed");
		return null;
	}
		// if the Adresse list of the Plan is empty return null
	    if(this.plan.getAdresses().isEmpty()) return null;
	    
	    return this.plan;
	    
	}
}

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
public class FactoryDemandeLivraisons implements FactoryBase {

	private DemandeLivraisons demandeLivraisons = null;
	private Plan plan = null;
	
	/**
	 * parse a xml file and return its domTree, return null otherwise 
	 */
	public Document getDomTree(String uriXml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();		
		    Document domTree= builder.parse(new File(uriXml));
		    return domTree;
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Return a new Livraison object with given parameters if its corresponding Adresse has been added to the list of Plan object
	 * and if the FenetreLivraison object has been added to the DemandeLivraisons object
	 * return null otherwise
	 * @param client
	 * @param idAdresse
	 * @param fenetreLivraison
	 * @return
	 */
	public Livraison getLivraison(int client, int idAdresse, FenetreLivraison fenetreLivraison) {
		if(client>0 && this.plan.getAdresseById(idAdresse)!=null && this.demandeLivraisons.getFenetresLivraisons().contains(fenetreLivraison)) {
			return new Livraison(client, this.plan.getAdresseById(idAdresse), fenetreLivraison);	
		}
		return null;
	}
	
	/**
	 * Return a new FenetreLivraison object with given parameters if it has not already been instantiated and added to the FenetreLivraison list of DemandeLivraison object
	 * return null otherwise
	 * @param heureDebut
	 * @param heureFin
	 * @return
	 */
	public FenetreLivraison getFenetreLivraison(double heureDebut, double heureFin) {
		if(heureDebut>=0 && heureFin>=heureDebut && this.demandeLivraisons.getFenetreLivraison(heureDebut)==null){
			return new FenetreLivraison(heureDebut, heureFin);
		}
		return null;
	}
	
	/**
	 * Convert a time format hh:mm:ss to its equivalent number of seconds since 00:00:00
	 * @param time
	 * @return
	 */
	public int convertTimeToSeconds(String time){
		String[] timeSplitted = time.split(":");
		int hours = Integer.parseInt(timeSplitted[0]);
		int minutes = Integer.parseInt(timeSplitted[1]);
		int seconds = Integer.parseInt(timeSplitted[2]);
		
		return (hours*3600+minutes*60+seconds);
	}
	
	/**
	 * Instantiate and return a DemandeLivraison object basing to the XML file passed in parameters
	 * @param uriXml
	 * @param plan
	 * @return
	 */
	public DemandeLivraisons getDemandeLivraisons(String uriXml, Plan plan) {
		
		// on passe le plan en paramètre pour pouvoir aller récupérer les adresses à partir de leur id
		this.plan = plan;
		
		try {
			// get the domTree of the XML file
			final Document domTree = this.getDomTree(uriXml);
			final Element racine = domTree.getDocumentElement();
			
			// instantiate the demandeLivraisons to a new DemandeLivraisons
			this.demandeLivraisons = new DemandeLivraisons();
			
			// get all children Nodes
			final NodeList childrenNodes = racine.getChildNodes();
			
			if (childrenNodes.item(1).getNodeType() == Node.ELEMENT_NODE && childrenNodes.item(1).getNodeName().equalsIgnoreCase("Entrepot")) {
				final Element entrepot = (Element) childrenNodes.item(1);
				Adresse adresseEntrepot = this.plan.getAdresseById(Integer.parseInt(((Element) entrepot).getAttribute("adresse")));
				this.demandeLivraisons.setEntrepot(adresseEntrepot);
			}
			else {
				System.err.println("Entrepot tag must be the first child of "+racine.getNodeName()+" tag in the file "+uriXml);
				return null;
			}
			
			// get all Plage tag from the xml file to instantiate the FenetreLivraisons objects
			final NodeList fenetreLivraisonListe = racine.getElementsByTagName("Plage");
			final int nbFenetreLivraison = fenetreLivraisonListe.getLength();
			
		    for (int i = 0; i<nbFenetreLivraison; i++) {
		        if(fenetreLivraisonListe.item(i).getNodeType() == Node.ELEMENT_NODE) {
		            final Element fenetreLivraison = (Element) fenetreLivraisonListe.item(i);
		            
		            try {
		            	int heureDebutEnSec = this.convertTimeToSeconds(fenetreLivraison.getAttribute("heureDebut"));
		            	int heureFinEnSec = this.convertTimeToSeconds(fenetreLivraison.getAttribute("heureFin"));
			            
		            	// instantiate a new FenetreLivraison object with attributes given in the xml tag
			            FenetreLivraison newFenetreLivraison = this.getFenetreLivraison(heureDebutEnSec, heureFinEnSec);
			            
		            	// add the FenetreLivraison to the list of the DemandeLivraisons and get all children tags from xml if it is not null
			             if( newFenetreLivraison != null ){
			            	 this.demandeLivraisons.addFenetreLivraison(newFenetreLivraison);
			             
				            // get all Livraison tag from the xml file to instantiate the Livraison objects
				            final NodeList LivraisonListe = fenetreLivraison.getElementsByTagName("Livraison");
				            final int nbLivraison = LivraisonListe.getLength();
							
						    for(int j = 0; j<nbLivraison; j++) {
						        final Element livraison = (Element) LivraisonListe.item(j);
						        
						        try {
						        int idClient = Integer.parseInt(livraison.getAttribute("client"));
						        int idAdresse = Integer.parseInt(livraison.getAttribute("adresse"));
						        
						        // instantiate a new Livraison object with attributes given in the xml tag
						        Livraison newLivraison = this.getLivraison(idClient, idAdresse, newFenetreLivraison);
						        
						        // add the Livraison object to the list of the DemandeLivraisons if it is not null
					            if( newLivraison != null ) this.demandeLivraisons.addLivraison(newLivraison, newFenetreLivraison);
						        } catch(Exception e) {
						        	System.err.println("Missing parameters for Livraison tag #"+j+" of Plage tag #"+i+" in the file "+uriXml);
						        }
						    }
			             }
		            } catch(Exception e) {
		            	System.err.println("Missing parameters for Plage tag #"+i+" in the file "+uriXml);
		            }
		        }
		    }
		} catch(Exception e) {
					
			// si le fichier n'est pas bien formé on ne stoppe l'instanciation de la demande de livraisons
			System.err.println("The file "+uriXml+" is not well formed");
			return null;
		}
		
		return this.demandeLivraisons;
	}
		
	

}

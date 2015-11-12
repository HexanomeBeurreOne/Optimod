/**
 * 
 */
package model.factory;

import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Adresse;
import model.DemandeLivraisons;
import model.FenetreLivraison;
import model.Livraison;
import model.Plan;

/**
 * @author Adrien Menella
 *
 */
public class FactoryDemandeLivraisons implements FactoryBase {

	private DemandeLivraisons demandeLivraisons = null;
	private Plan plan = null;
	
	public Document getDomTree(String pathXml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();		
		    Document domTree= builder.parse(new File(pathXml));
		    return domTree;
		} catch(Exception e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retourne un objet Livraison instancié avec les paramètres si l'Adresse passée en paramètre n'appartient pas déjà à la liste d'adresses du plan
	 * et si l'objet FenetreLivraison a été ajouté à la liste de FenetreLivraison de demandeLivraison
	 * retourne null en cas d'erreur
	 * @param client est l'id du client
	 * @param idAdresse est l'id de l'adresse que l'on veut créée
	 * @param fenetreLivraison est la fenetreLivraison dans laquelle on veut ajouter cette livraison
	 * @return
	 */
	public Livraison getLivraison(int client, int idAdresse, FenetreLivraison fenetreLivraison) {
		if(client>0 && this.plan.getAdresseById(idAdresse)!=null && this.demandeLivraisons.getFenetresLivraisons().contains(fenetreLivraison)) {
			return new Livraison(client, this.plan.getAdresseById(idAdresse), fenetreLivraison);	
		}
		return null;
	}
	
	/**
	 * Retourne un objet FenetreLivraison instancié avec les paramètres si cette nouvelle FenetreLivraison n'appartient pas déjà à la liste de fentres de livraisons de la demande de livraison.
	 * retourne null en cas d'erreur
	 * @param heureDebut est l'heure de début de la nouvelle FenetreLivraison
	 * @param heureFin est l'heure de fin de la nouvelle FenetreLivraison
	 * @return
	 */
	public FenetreLivraison getFenetreLivraison(int heureDebut, int heureFin) {
		if(heureDebut>=0 && heureFin>heureDebut && this.demandeLivraisons.getFenetreLivraison(heureDebut)==null){
			return new FenetreLivraison(heureDebut, heureFin);
		}
		return null;
	}
	
	/**
	 * Converti un temps au format hh:mm:ss à son nombre équvalent de secondes depuis 00:00:00
	 * si les heures, minutes et secondes correspondent à des chiffres valides pour un horaire
	 * retorune -1 en cas d'erreur
	 * @param time est la chaine de caractère représentant le temps
	 * @return
	 */
	private int convertTimeToSeconds(String time){
		String[] timeSplitted = time.split(":");
		int hours = Integer.parseInt(timeSplitted[0]);
		int minutes = Integer.parseInt(timeSplitted[1]);
		int seconds = Integer.parseInt(timeSplitted[2]);
		
		if (hours<24 && hours >=0 && minutes<60 && minutes>=0 && seconds<60 && seconds>=0) return (hours*3600+minutes*60+seconds);
		
		return -1;
	}
	
	public boolean checkNodeTypeAndName(Node currentNode, String correctNodeName) {
		if ( currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().equalsIgnoreCase(correctNodeName)) return true;
		return false;
	}
	
	/**
	 * Ajoute l'entrepot à la demande de livraisons si la balise XMl correspondante est bien formée et si l'adresse correspondant à cet entrepot existe dans le plan
	 * retourne true si l'ajout s'est bien déroulé
	 * retourne false en cas d'erreur
	 * @param noeudEntrepot est la balise XML correspondante 
	 * @return
	 */
	private boolean ajouterEntrepot(Node noeudEntrepot) {
			try {
				final Element entrepot = (Element) noeudEntrepot;
				int idEntrepot = Integer.parseInt(((Element) entrepot).getAttribute("adresse"));
				// on vérifie que l'id de l'entrepot du XML pointe vers une adresse existante
				Adresse adresseEntrepot = this.plan.getAdresseById(idEntrepot);
				if( adresseEntrepot!= null) {
					this.demandeLivraisons.setEntrepot(adresseEntrepot);
					return true;
				} else {
					return false;
				}
			} catch(Exception e) {
				return false;
			}
	}
	
	/**
	 * vérifie qu'il n'existe aucune FenetreLivraison dont la plage horaire ne chevauche la plage horaire correspondante aux paramètres heureDebut - heureFin
	 * retourne true si ca ne se chevauche pas
	 * retourne false en cas d'erreur
	 * @param heureDebut est l'heure de début de la plage horaire à vérifier
	 * @param heureFin est l'heure de fin de la plage horaire à vérifier
	 * @return
	 */
	private boolean verifierChevauchementFenetreLivraison(int heureDebut, int heureFin) {
		Iterator<FenetreLivraison> iFL = this.demandeLivraisons.getFenetresLivraisons().iterator();
		FenetreLivraison fenetreLivraison;
		while(iFL.hasNext()){
			fenetreLivraison = iFL.next();
			if( (fenetreLivraison.getHeureDebut()<=heureDebut && fenetreLivraison.getHeureFin()>heureDebut)
					|| (heureFin<=fenetreLivraison.getHeureFin() && heureFin>fenetreLivraison.getHeureDebut())){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Instantie et retourne un objet DemandeLivraison à partir du fichier XML passé en paramètre
	 * retourne null en cas d'erreur
	 * @param pathXml
	 * @param plan
	 * @return
	 */
	public DemandeLivraisons getDemandeLivraisons(String pathXml, Plan plan) {
		
		// on passe le plan en paramètre pour pouvoir aller récupérer les adresses à partir de leur id
		this.plan = plan;
		
		try {
			// get the domTree of the XML file
			final Document domTree = this.getDomTree(pathXml);
			final Element racine = domTree.getDocumentElement();
			
			// instantiate the demandeLivraisons to a new DemandeLivraisons
			this.demandeLivraisons = new DemandeLivraisons();
			
			// get all children Nodes
			final NodeList childrenNodes = racine.getChildNodes();
			
			if(!ajouterEntrepot(childrenNodes.item(1))) return null;
			
			// get all Plage tag from the xml file to instantiate the FenetreLivraisons objects
			final NodeList fenetreLivraisonListe = racine.getElementsByTagName("Plage");
			final int nbFenetreLivraison = fenetreLivraisonListe.getLength();
			
			FenetreLivraison newFenetreLivraison = null;
			
		    for (int i = 0; i<nbFenetreLivraison; i++) {
		        if(fenetreLivraisonListe.item(i).getNodeType() == Node.ELEMENT_NODE) {
		            final Element fenetreLivraison = (Element) fenetreLivraisonListe.item(i);
		            
		            try {
		            	int heureDebutEnSec = this.convertTimeToSeconds(fenetreLivraison.getAttribute("heureDebut"));
		            	int heureFinEnSec = this.convertTimeToSeconds(fenetreLivraison.getAttribute("heureFin"));
		            	
		            	// on verifie que les heures obtenues ne sont pas fausses
			            if(heureDebutEnSec!=(-1) && heureFinEnSec!=(-1)) {
			            	// on vérifie que la fenetre de livraison ne chevauche pas une autre
			            	if(verifierChevauchementFenetreLivraison(heureDebutEnSec, heureFinEnSec)) {
			            		// instantiate a new FenetreLivraison object with attributes given in the xml tag
			            		newFenetreLivraison = this.getFenetreLivraison(heureDebutEnSec, heureFinEnSec);
			            	} else {
			            		return null;
			            	}
			            } else {
			            	newFenetreLivraison = null;
			            }
			            
			            // get all Livraison tag from the xml file to instantiate the Livraison objects
			            final NodeList LivraisonListe = fenetreLivraison.getElementsByTagName("Livraison");
			            final int nbLivraison = LivraisonListe.getLength();
			            
		            	// add the FenetreLivraison to the list of the DemandeLivraisons and get all children tags from xml if it is not null
			             if( newFenetreLivraison != null && nbLivraison!=0){
			            	 this.demandeLivraisons.addFenetreLivraison(newFenetreLivraison);
							
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
						        	//System.err.println("Missing parameters for Livraison tag #"+j+" of Plage tag #"+i+" in the file "+uriXml);
						        }
						    }
						    
						    if( newFenetreLivraison.getLivraisons().isEmpty() ) this.demandeLivraisons.removeFenetreLivraison(newFenetreLivraison);
			             }
		            } catch(Exception e) {
		            	//System.err.println("Missing parameters for Plage tag #"+i+" in the file "+uriXml);
		            }
		        }
		    }
		} catch(Exception e) {
					
			// si le fichier n'est pas bien formé on ne stoppe l'instanciation de la demande de livraisons
			//System.err.println("The file "+uriXml+" is not well formed");
			return null;
		}
		
		// if the FenetreLivraison list of the DemandeLivraison is empty return null
	    if(this.demandeLivraisons.getFenetresLivraisons().isEmpty()) return null;
	    
		return this.demandeLivraisons;
	}
		
	

}

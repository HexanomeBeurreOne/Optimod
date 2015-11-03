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
	
	public Document getDomTree(String uriXml) {
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			final DocumentBuilder builder = factory.newDocumentBuilder();		
		    final Document domTree= builder.parse(new File(uriXml));
		    return domTree;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Adresse getAdresse(int id, int coordX, int coordY){
		// check if the given id already exists in the adresses list
		if(this.plan.getAdresseById(id)==null && coordX>=0 && coordY>=0){
			return new Adresse(id, coordX, coordY);
		}
		return null;
	}
	
	public Troncon getTroncon(String nomRue, double vitesseMoyenne, double longueur, int idDestination, int idOrigine){
		// check parameters and if destination exists in in the adresses list
		Adresse origine = this.plan.getAdresseById(idOrigine);
		Adresse destination = this.plan.getAdresseById(idDestination);

		if(nomRue.length()!=0 && vitesseMoyenne>0 && longueur>0 && origine!=null && destination!=null){
			return new Troncon(nomRue, vitesseMoyenne, longueur, origine, destination);
		}
		return null;
	}
	
	public Plan getPlan(String uriXml) {
		final Document domTree = this.getDomTree(uriXml);
		final Element racine = domTree.getDocumentElement();
		
		final NodeList adressesListe = racine.getChildNodes();
		final int nbAdresses = adressesListe.getLength();
		
		this.plan = new Plan();
		
	    // first reading, instantiate all addresses
	    for (int i = 0; i<nbAdresses; i++) {
	        if(adressesListe.item(i).getNodeType() == Node.ELEMENT_NODE) {
	            final Element adresse = (Element) adressesListe.item(i);
	            Adresse newAdresse = this.getAdresse(Integer.parseInt(adresse.getAttribute("id")), Integer.parseInt(adresse.getAttribute("x")), Integer.parseInt(adresse.getAttribute("y")));
	            this.plan.addAdresse(newAdresse);
	        }
	    }
	    
	    // second reading, instantiate all troncon and add tronconSortant to adresse
	    for (int i = 0; i<nbAdresses; i++) {
	        if(adressesListe.item(i).getNodeType() == Node.ELEMENT_NODE) {
	            final Element adresse = (Element) adressesListe.item(i);
	            final Adresse currentAdresse = this.plan.getAdresseById(Integer.parseInt(adresse.getAttribute("id")));
	            
	            final NodeList tronconsSortantsListe = adresse.getElementsByTagName("LeTronconSortant");
			    final int nbTronconsSortants = tronconsSortantsListe.getLength();
				
			    for(int j = 0; j<nbTronconsSortants; j++) {
			        final Element tronconSortant = (Element) tronconsSortantsListe.item(j);
					String nomRue = tronconSortant.getAttribute("nomRue");
					Double vitesse = Double.parseDouble(tronconSortant.getAttribute("vitesse").replace(',', '.'));
					Double longueur = Double.parseDouble(tronconSortant.getAttribute("longueur").replace(',', '.'));
					int idDestination = Integer.parseInt(tronconSortant.getAttribute("idNoeudDestination"));
					int idOrigine = currentAdresse.getId();
			        Troncon newTroncon = this.getTroncon(nomRue, vitesse, longueur, idOrigine, idDestination);
	                this.plan.addTroncon(newTroncon);
	                currentAdresse.addTroncon(newTroncon);
			    }
	        }
	    }
	    
	    return this.plan;
	    
	}
}

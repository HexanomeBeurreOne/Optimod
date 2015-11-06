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
	
	@Override
	public Document getDomTree(String uriXml) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();		
		    Document domTree= builder.parse(new File(uriXml));
		    return domTree;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Livraison getLivraison(int client, int idAdresse, FenetreLivraison fenetreLivraison) {
		if(client>0 && this.plan.getAdresseById(idAdresse)!=null && this.demandeLivraisons.getFenetresLivraisons().contains(fenetreLivraison)) {
			return new Livraison(client, this.plan.getAdresseById(idAdresse), fenetreLivraison);	
		}
		return null;
	}
	
	public FenetreLivraison getFenetreLivraison(double heureDebut, double heureFin) {
		if(heureDebut>=0 && heureFin>=heureDebut && this.demandeLivraisons.getFenetreLivraison(heureDebut)==null){
			return new FenetreLivraison(heureDebut, heureFin);
		}
		return null;
	}
	
	public int convertTimeToSeconds(String time){
		String[] timeSplitted = time.split(":");
		int hours = Integer.parseInt(timeSplitted[0]);
		int minutes = Integer.parseInt(timeSplitted[1]);
		int seconds = Integer.parseInt(timeSplitted[2]);
		
		return (hours*3600+minutes*60+seconds);
	}
	
	public DemandeLivraisons getDemandeLivraisons(String uriXml, Plan plan) {
		
		// on passe le plan en paramètre pour pouvoir aller récupérer les adresses à partir de leur id
		this.plan = plan;
		
		final Document domTree = this.getDomTree(uriXml);
		final Element racine = domTree.getDocumentElement();
		
		this.demandeLivraisons = new DemandeLivraisons();
		
		final NodeList childrenNodes = racine.getChildNodes();
		
		final Element entrepot = (Element) childrenNodes.item(1);
		this.demandeLivraisons.setIdEntrepot(Integer.parseInt(((Element) entrepot).getAttribute("adresse")));
		
		final NodeList fenetreLivraisonListe = racine.getElementsByTagName("Plage");
		final int nbFenetreLivraison = fenetreLivraisonListe.getLength();
		
	    for (int i = 0; i<nbFenetreLivraison; i++) {
	        if(fenetreLivraisonListe.item(i).getNodeType() == Node.ELEMENT_NODE) {
	            final Element fenetreLivraison = (Element) fenetreLivraisonListe.item(i);
	            
	            FenetreLivraison newFenetreLivraison = this.getFenetreLivraison(
	            		this.convertTimeToSeconds(fenetreLivraison.getAttribute("heureDebut")),
	            		this.convertTimeToSeconds(fenetreLivraison.getAttribute("heureFin"))
	            		);
	            
	            this.demandeLivraisons.addFenetreLivraison(newFenetreLivraison);
	            
	            
	            final NodeList LivraisonListe = fenetreLivraison.getElementsByTagName("Livraison");
	            final int nbLivraison = LivraisonListe.getLength();
				
			    for(int j = 0; j<nbLivraison; j++) {
			        final Element livraison = (Element) LivraisonListe.item(j);
			        Livraison newLivraison = this.getLivraison(
			        		Integer.parseInt(livraison.getAttribute("client")),
			        		Integer.parseInt(livraison.getAttribute("adresse")),
			        		newFenetreLivraison
			        		);
			        this.demandeLivraisons.addLivraison(newLivraison, newFenetreLivraison);
			    }
	        }
	    }
		
		return this.demandeLivraisons;
	}
		
	

}

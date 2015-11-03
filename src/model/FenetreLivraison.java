/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Adrien Menella
 *
 */
public class FenetreLivraison {

	/**
	 * Attributes
	 */
	private double heureDebut;
	private double heureFin;
	private List<Livraison> livraisons;
	
	/**
	 * Constructor
	 */
	public FenetreLivraison(double heureDebut, double heureFin) {
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.livraisons = new ArrayList();
	}
	
	/**
	 * add a Livraison object to the arrayList livraisons
	 * @param newLivraison
	 */
	public void addLivraison(Livraison newLivraison) {
		this.livraisons.add(newLivraison);
	}
	
	/**
	 * remove a Livraison object from the arrayList livraisons
	 * @param oldLivraison
	 */
	public void removeLivraison(Livraison oldLivraison) {
		this.livraisons.remove(oldLivraison);
	}

	public double getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(double heureDebut) {
		this.heureDebut = heureDebut;
	}

	public double getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(double heureFin) {
		this.heureFin = heureFin;
	}

	public List<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(List<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	public void afficheFenetreLivraison() {
		System.out.println("FenetreLivraison : heureDebut="+this.heureDebut+" heureFin="+this.heureFin);
		Iterator livraisonsIterator = this.livraisons.iterator();
		while(livraisonsIterator.hasNext()) {
			Livraison currentLivraison = (Livraison) livraisonsIterator.next();
			System.out.print("      ");
			currentLivraison.afficheLivraison();
		}
	}
}

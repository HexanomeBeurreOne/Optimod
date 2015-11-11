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
	private int heureDebut;
	private int heureFin;
	private List<Livraison> livraisons;
	
	/**
	 * Constructor
	 */
	public FenetreLivraison(int heureDebut, int heureFin) {
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.livraisons = new ArrayList<Livraison>();
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
		if (this.livraisons.contains(oldLivraison) ) this.livraisons.remove(oldLivraison);
	}

	public int getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(int heureDebut) {
		this.heureDebut = heureDebut;
	}

	public int getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(int heureFin) {
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
		Iterator<Livraison> livraisonsIterator = this.livraisons.iterator();
		while(livraisonsIterator.hasNext()) {
			Livraison currentLivraison = (Livraison) livraisonsIterator.next();
			System.out.print("      ");
			currentLivraison.afficheLivraison();
		}
	}
	
	public Livraison chercheLivraison(int x1, int y1, int x2, int y2) {
		Iterator<Livraison> itL = this.livraisons.iterator();
		Livraison livraisonCourante;
		int x, y;
		while(itL.hasNext()){
			livraisonCourante = itL.next();
			x = livraisonCourante.getAdresse().getCoordX();
			y = livraisonCourante.getAdresse().getCoordY();
			if(x>=x1 && x<=x2 && y>=y1 && y<=y2) return livraisonCourante;
		}
		return null;
	}
	
}

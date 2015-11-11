/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
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
	
	public void setLivraisonSelectionnee(Livraison livraison, boolean selectionnee) {
		if(this.livraisons.contains(livraison)) {
			this.livraisons.get(this.livraisons.indexOf(livraison)).setSelectionnee(selectionnee);
		}
	}
	
	public Livraison chercheLivraison(int x0, int y0) {
		Iterator<Livraison> itL = this.livraisons.iterator();
		Livraison livraisonCourante;
		int x, y;
		double dist;
		Hashtable<Double,Livraison> livraisonsTrouvees = new Hashtable<Double, Livraison>();
		while(itL.hasNext()){
			livraisonCourante = itL.next();
			x = livraisonCourante.getAdresse().getCoordX();
			y = livraisonCourante.getAdresse().getCoordY();
			dist = Math.sqrt( ((x0-x)*(x0-x)+(y0-y)*(y0-y)) );
			livraisonsTrouvees.put(dist, livraisonCourante);
		}
		
		Enumeration<Double> listeDistances = livraisonsTrouvees.keys();
		double minDist = 9999;
		while(listeDistances.hasMoreElements()) {
			double nextDist = listeDistances.nextElement();
			minDist = nextDist<minDist ? nextDist : minDist;
		}
		
		if(minDist<=10) return livraisonsTrouvees.get(minDist);
		
		return null;
	}
	
}

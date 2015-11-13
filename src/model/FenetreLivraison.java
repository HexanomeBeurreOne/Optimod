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
	 * @return the heureDebut
	 */
	public int getHeureDebut() {
		return heureDebut;
	}

	/**
	 * @param heureDebut the heureDebut to set
	 */
	public void setHeureDebut(int heureDebut) {
		this.heureDebut = heureDebut;
	}

	/**
	 * @return the heureFin
	 */
	public int getHeureFin() {
		return heureFin;
	}

	/**
	 * @param heureFin the heureFin to set
	 */
	public void setHeureFin(int heureFin) {
		this.heureFin = heureFin;
	}

	/**
	 * @return the livraisons
	 */
	public List<Livraison> getLivraisons() {
		return livraisons;
	}

	/**
	 * @param livraisons the livraisons to set
	 */
	public void setLivraisons(List<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	/**
	 * ajoute un objet Livraison � la liste livraisons
	 * @param newLivraison
	 */
	public void addLivraison(Livraison newLivraison) {
		this.livraisons.add(newLivraison);
	}
	
	/**
	 * enl�ve l'objet Livraison de la liste livraisons
	 * @param oldLivraison
	 */
	public void removeLivraison(Livraison oldLivraison) {
		if (this.livraisons.contains(oldLivraison) ) this.livraisons.remove(oldLivraison);
	}

//	public void afficheFenetreLivraison() {
//		System.out.println("FenetreLivraison : heureDebut="+this.heureDebut+" heureFin="+this.heureFin);
//		Iterator<Livraison> livraisonsIterator = this.livraisons.iterator();
//		while(livraisonsIterator.hasNext()) {
//			Livraison currentLivraison = (Livraison) livraisonsIterator.next();
//			System.out.print("      ");
//			currentLivraison.afficheLivraison();
//		}
//	}
	
	
	/**
	 * d�finie l'attribut selectionnee de l'objet Livraison livraison � la valeur du booleen selectionnee pass� en param�tre
	 * @param livraison est la livraison que l'on doit modifier
	 * @param selectionnee est la nouvelle valeur de l'attribut de la livraison
	 */
	public void setLivraisonSelectionnee(Livraison livraison, boolean selectionnee) {
		if(this.livraisons.contains(livraison)) {
			this.livraisons.get(this.livraisons.indexOf(livraison)).setSelectionnee(selectionnee);
		}
	}
	
	/**
	 * retourne la Livraison dont les coordonn�es sont les plus proche des points x0 et y0 dans un cercle de rayon pixels
	 * @param x0
	 * @param y0
	 * @param rayon
	 * @return
	 */
	public Livraison chercheLivraison(int x0, int y0, int rayon) {
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
		Double minDist = Double.MAX_VALUE;
		while(listeDistances.hasMoreElements()) {
			double nextDist = listeDistances.nextElement();
			minDist = nextDist<minDist ? nextDist : minDist;
		}
		
		if(minDist<=rayon) return livraisonsTrouvees.get(minDist);
		
		return null;
	}
	
}

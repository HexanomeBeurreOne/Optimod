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
public class DemandeLivraisons {

	/**
	 * Attributes
	 */
	private int idEntrepot;
	private List<FenetreLivraison> fenetresLivraisons;
	
	/**
	 * Constructor
	 */
	public DemandeLivraisons() {
		this.idEntrepot = 0;
		this.fenetresLivraisons = new ArrayList<FenetreLivraison>();
	}
	
	/**
	 * Return the FenetreLivraison object which heureDebut corresponds with the given parameter
	 * Return null otherwise
	 * @param heureDebut
	 * @return
	 */
	public FenetreLivraison getFenetreLivraison(double heureDebut) {
		Iterator<FenetreLivraison> fenetresLivraisonsIterator = this.fenetresLivraisons.iterator();
		while(fenetresLivraisonsIterator.hasNext()) {
			FenetreLivraison currentFenetreLivraison = (FenetreLivraison) fenetresLivraisonsIterator.next();
			if(currentFenetreLivraison.getHeureDebut()==heureDebut) return currentFenetreLivraison;
		}
		return null;
	}
	
	/**
	 * Add a new Livraison to the specified fenetreLivraison passed in parameters
	 * @param newLivraison
	 * @param fenetreLivraison
	 */
	public void addLivraison(Livraison newLivraison, FenetreLivraison fenetreLivraison) {
		if (this.fenetresLivraisons.contains(fenetreLivraison)) {
			FenetreLivraison fenetreLivraisonFounded = this.fenetresLivraisons.get(this.fenetresLivraisons.indexOf(fenetreLivraison));
			fenetreLivraisonFounded.addLivraison(newLivraison);
		}
	}
	
	/**
	 * Remove a Livraison from the specified fenetreLivraison passed in parameters
	 * @param oldLivraison
	 * @param fenetreLivraison
	 */
	public void removeLivraison(Livraison oldLivraison, FenetreLivraison fenetreLivraison) {
		if (this.fenetresLivraisons.contains(fenetreLivraison)) {
			FenetreLivraison fenetreLivraisonFounded = this.fenetresLivraisons.get(this.fenetresLivraisons.indexOf(fenetreLivraison));
			fenetreLivraisonFounded.removeLivraison(oldLivraison);
		}
	}
	
	/**
	 * add a new FenetreLivraison to the list fenetresLivraisons
	 * @param newFenetreLivraison
	 */
	public void addFenetreLivraison(FenetreLivraison newFenetreLivraison) {
		this.fenetresLivraisons.add(newFenetreLivraison);
	}

	public int getIdEntrepot() {
		return idEntrepot;
	}

	public void setIdEntrepot(int idEntrepot) {
		this.idEntrepot = idEntrepot;
	}

	public List<FenetreLivraison> getFenetresLivraisons() {
		return fenetresLivraisons;
	}

	public void setFenetresLivraisons(List<FenetreLivraison> fenetresLivraisons) {
		this.fenetresLivraisons = fenetresLivraisons;
	}
	
	public List<Livraison> getAllLivraisons() {
		List<Livraison> livraisons = new ArrayList<Livraison>();
		for (FenetreLivraison fen : fenetresLivraisons){
			livraisons.addAll(fen.getLivraisons());
		}
		return livraisons;
	}

	public void afficheDemandeLivraisons() {
		System.out.println("DemandeLivraison : idEntrepot="+this.idEntrepot);
		System.out.println("Liste livraisons : ");
		Iterator<FenetreLivraison> fenetresLivraisonsIterator = this.fenetresLivraisons.iterator();
		while(fenetresLivraisonsIterator.hasNext()) {
			FenetreLivraison currentFenetreLivraison = (FenetreLivraison) fenetresLivraisonsIterator.next();
			System.out.print("   ");
			currentFenetreLivraison.afficheFenetreLivraison();
		}
	}
	
}

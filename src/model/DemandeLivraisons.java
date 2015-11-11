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
	private Adresse entrepot;
	private List<FenetreLivraison> fenetresLivraisons;
	private int heureDepart;
	
	/**
	 * Constructor
	 */
	public DemandeLivraisons() {
		this.entrepot = null;
		this.fenetresLivraisons = new ArrayList<FenetreLivraison>();
		this.heureDepart = 8*3600;
	}
	
	/**
	 * Return the FenetreLivraison object which heureDebut corresponds with the given parameter
	 * Return null otherwise
	 * @param heureDebut
	 * @return
	 */
	public FenetreLivraison getFenetreLivraison(int heureDebut) {
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
	
	/**
	 * remove a FenetreLivraison to the list fenetresLivraisons
	 * @param fenetreLivraison
	 */
	public void removeFenetreLivraison(FenetreLivraison fenetreLivraison) {
		if( this.fenetresLivraisons.contains(fenetreLivraison) ) this.fenetresLivraisons.remove(fenetreLivraison);
	}

	public Adresse getEntrepot() {
		return entrepot;
	}

	public void setEntrepot(Adresse entrepot) {
		this.entrepot = entrepot;
	}
	
	public int getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(int heureDepart) {
		this.heureDepart = heureDepart;
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
	
	public Livraison getLivraison(Adresse adresse){
		List<Livraison> livraisons = getAllLivraisons();
		for (Livraison liv : livraisons){
			if(liv.getAdresse() == adresse) return liv;
		}
		return null;
	}

	public void afficheDemandeLivraisons() {
		System.out.println("DemandeLivraison : idEntrepot="+this.entrepot.getId());
		System.out.println("Liste livraisons : ");
		Iterator<FenetreLivraison> fenetresLivraisonsIterator = this.fenetresLivraisons.iterator();
		while(fenetresLivraisonsIterator.hasNext()) {
			FenetreLivraison currentFenetreLivraison = (FenetreLivraison) fenetresLivraisonsIterator.next();
			System.out.print("   ");
			currentFenetreLivraison.afficheFenetreLivraison();
		}
	}
	
	public Livraison chercheLivraison(int x0, int y0) {
		Iterator<FenetreLivraison> itFL = this.fenetresLivraisons.iterator();
		FenetreLivraison fenetreLivraisonCourante;
		Livraison livraisonTrouvee;
		while(itFL.hasNext()){
			fenetreLivraisonCourante = itFL.next();
			livraisonTrouvee = fenetreLivraisonCourante.chercheLivraison(x0, y0);
			if(livraisonTrouvee!=null) return livraisonTrouvee;
		}
		return null;
		
	}
	
	public void setLivraisonSelectionnee(Livraison livraison, boolean selectionnee) {
		Iterator<FenetreLivraison> itFL = this.fenetresLivraisons.iterator();
		FenetreLivraison fenetreLivraison;
		while(itFL.hasNext()){
			fenetreLivraison = itFL.next();
			if( fenetreLivraison.getLivraisons().contains(livraison) ) fenetreLivraison.setLivraisonSelectionnee(livraison, selectionnee);
		}
	}
	
}

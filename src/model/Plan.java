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
public class Plan {

	/**
	 * Attributes
	 */
	private String nom;
	private List<Adresse> adresses;
	private List<Troncon> troncons;
	private DemandeLivraisons demandeLivraisons;
	
	/**
	 * Constructor
	 */
	public Plan() {
		this.nom = "";

		this.adresses = new ArrayList<Adresse>();
		this.troncons = new ArrayList<Troncon>();
		this.demandeLivraisons = new DemandeLivraisons();
	}
	
	public DemandeLivraisons getDemandeLivraisons() {
		return demandeLivraisons;
	}

	public void setDemandeLivraisons(DemandeLivraisons demandeLivraisons) {
		this.demandeLivraisons = demandeLivraisons;
	}
	
	/**
	 * Add a new Livraison to the specified fenetreLivraison passed in parameters
	 * @param newLivraison
	 * @param fenetreLivraison
	 */
	public void addLivraison(Livraison newLivraison, FenetreLivraison fenetreLivraison) {
		List<FenetreLivraison> fenetresLivraisonsList = this.demandeLivraisons.getFenetresLivraisons();
		if (fenetresLivraisonsList.contains(fenetreLivraison)) {
			FenetreLivraison fenetreLivraisonFounded = fenetresLivraisonsList.get(fenetresLivraisonsList.indexOf(fenetreLivraison));
			fenetreLivraisonFounded.addLivraison(newLivraison);
		}
	}
	
	/**
	 * Remove a Livraison from the specified fenetreLivraison passed in parameters
	 * @param oldLivraison
	 * @param fenetreLivraison
	 */
	public void removeLivraison(Livraison oldLivraison, FenetreLivraison fenetreLivraison) {
		List<FenetreLivraison> fenetresLivraisonsList = this.demandeLivraisons.getFenetresLivraisons();
		if (fenetresLivraisonsList.contains(fenetreLivraison)) {
			FenetreLivraison fenetreLivraisonFounded = fenetresLivraisonsList.get(fenetresLivraisonsList.indexOf(fenetreLivraison));
			fenetreLivraisonFounded.removeLivraison(oldLivraison);
		}
	}
	
	/**
	 * add a new FenetreLivraison to the list fenetresLivraisons
	 * @param newFenetreLivraison
	 */
	public void addFenetreLivraison(FenetreLivraison newFenetreLivraison) {
		this.demandeLivraisons.getFenetresLivraisons().add(newFenetreLivraison);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public List<Adresse> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Adresse> adresses) {
		this.adresses = adresses;
	}

	public List<Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}

	/**
	 * Add an Adresse to the ArrayList adresses
	 * @param newAdresse
	 */
	public void addAdresse(Adresse newAdresse) {
		this.adresses.add(newAdresse);
	}
	 /**
	  * Add a Troncon to the ArrayList troncons
	  * @param newTroncon
	  */
	public void addTroncon(Troncon newTroncon) {
		this.troncons.add(newTroncon);
	}
	
	/**
	 * Get the Adresse which id is corresponding, return null if it does not contain
	 * @param id
	 * @return
	 */
	public Adresse getAdresseById(int id) {
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		while(adressesIterator.hasNext()) {
			Adresse currentAdresse = (Adresse) adressesIterator.next();
			if(currentAdresse.getId()==id) return currentAdresse;
		}
		return null;
	}
	
	public void affichePlan() {
		System.out.println("Plan : "+this.nom);
		System.out.println("Liste adresses : ");
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		while(adressesIterator.hasNext()) {
			Adresse currentAdresse = (Adresse) adressesIterator.next();
			System.out.print("   ");
			currentAdresse.afficheAdresse();
		}
	}

}

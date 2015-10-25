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
	
	/**
	 * Constructor
	 */
	public Plan() {
		this.nom = "";
		this.adresses = new ArrayList();
		this.troncons = new ArrayList();		
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
		Iterator adressesIterator = this.adresses.iterator();
		while(adressesIterator.hasNext()) {
			Adresse currentAdresse = (Adresse) adressesIterator.next();
			if(currentAdresse.getId()==id) return currentAdresse;
		}
		return null;
	}
	
	public void affichePlan() {
		System.out.println("Plan : "+this.nom);
		System.out.println("Liste adresses : ");
		Iterator adressesIterator = this.adresses.iterator();
		while(adressesIterator.hasNext()) {
			Adresse currentAdresse = (Adresse) adressesIterator.next();
			System.out.print("   ");
			currentAdresse.afficheAdresse();
		}
	}

}

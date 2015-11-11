/**
 * 
 */
package model;

import java.util.Observable;

/**
 * @author Adrien Menella
 *
 */
public class Livraison extends Observable {

	/**
	 * Attributes
	 */
	private int client;
	private Adresse adresse;
	private FenetreLivraison fenetreLivraison;
	private boolean estSelectionnee;
	
	/**
	 * Constructor
	 */
	public Livraison() {
	}
	
	public Livraison(int client, Adresse adresse, FenetreLivraison fenetreLivraison) {
		this.client = client;
		this.adresse = adresse;
		this.fenetreLivraison = fenetreLivraison;
		this.estSelectionnee = false;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public FenetreLivraison getFenetreLivraison() {
		return fenetreLivraison;
	}

	public void setFenetreLivraison(FenetreLivraison fenetreLivraison) {
		this.fenetreLivraison = fenetreLivraison;
	}

	public boolean isEstSelectionnee() {
		return estSelectionnee;
	}

	public void setEstSelectionnee(boolean estSelectionnee) {
		this.estSelectionnee = estSelectionnee;
		setChanged();
		notifyObservers(this);
	}

	public void afficheLivraison() {
		System.out.println("Livraison : client : "+this.client);
	}
	
}

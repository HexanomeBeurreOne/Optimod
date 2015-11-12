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
	private boolean selectionnee;
	
	/**
	 * Constructor
	 */
	public Livraison() {
	}
	
	public Livraison(int client, Adresse adresse, FenetreLivraison fenetreLivraison) {
		this.client = client;
		this.adresse = adresse;
		this.fenetreLivraison = fenetreLivraison;
		this.selectionnee = false;
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

	public boolean isSelectionnee() {
		return selectionnee;
	}

	public void setSelectionnee(boolean selectionnee) {
		this.selectionnee = selectionnee;
	}

	public void afficheLivraison() {
		System.out.println("Livraison : client : "+this.client);
	}
	
}

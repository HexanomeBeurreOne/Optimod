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
	 * Attributs
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

	/**
	 * @return the client
	 */
	public int getClient() {
		return client;
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(int client) {
		this.client = client;
	}

	/**
	 * @return the adresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the fenetreLivraison
	 */
	public FenetreLivraison getFenetreLivraison() {
		return fenetreLivraison;
	}

	/**
	 * @param fenetreLivraison the fenetreLivraison to set
	 */
	public void setFenetreLivraison(FenetreLivraison fenetreLivraison) {
		this.fenetreLivraison = fenetreLivraison;
	}

	/**
	 * @return the selectionnee
	 */
	public boolean isSelectionnee() {
		return selectionnee;
	}

	/**
	 * @param selectionnee the selectionnee to set
	 */
	public void setSelectionnee(boolean selectionnee) {
		this.selectionnee = selectionnee;
	}
	
}

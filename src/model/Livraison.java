/**
 * 
 */
package model;

/**
 * @author Adrien Menella
 *
 */
public class Livraison {

	/**
	 * Attributes
	 */
	private int client;
	private Adresse adresse;
	private FenetreLivraison fenetreLivraison;
	
	/**
	 * Constructor
	 */
	public Livraison(int client, Adresse adresse, FenetreLivraison fenetreLivraison) {
		this.client = client;
		this.adresse = adresse;
		this.fenetreLivraison = fenetreLivraison;
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

	
}

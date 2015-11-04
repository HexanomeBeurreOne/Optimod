/**
 * 
 */
package model;

/**
 * @author Adrien Menella
 *
 */
public class Troncon {

	/**
	 * Attributes
	 */
	private String nomRue;
	private double vitesseMoyenne;
	private double longueur;
	private Adresse origine;
	private Adresse destination;
	private double tempsTroncon = 0; // null does not exists for double
	
	/**
	 * Constructor
	 */
	public Troncon(String nomRue, double vitesseMoyenne, double longueur, Adresse origine, Adresse destination) {
		this.nomRue = nomRue;
		this.vitesseMoyenne = vitesseMoyenne;
		this.longueur = longueur;
		this.destination = destination;
		this.origine = origine;
	}

	public Adresse getOrigine() {
		return origine;
	}

	public Adresse getDestination() {
		return destination;
	}

	public double getTempsTroncon() {
		if(this.tempsTroncon == 0) this.tempsTroncon = this.vitesseMoyenne * this.longueur;
		return this.tempsTroncon;
	}

	public void afficheTroncon() {
		System.out.println("TronconSortant : nomRue : "+this.nomRue+" vitesseMoyenne="+this.vitesseMoyenne+" longueur="+this.longueur+" idOrigine : "+this.origine.getId()+" idDestination : "+this.destination.getId());
	}
}

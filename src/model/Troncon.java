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
	private Adresse destination;
	private double tempsTroncon = 0; // null does not exists for double
	
	/**
	 * Constructor
	 */
	public Troncon(String nomRue, double vitesseMoyenne, double longueur, Adresse destination) {
		this.nomRue = nomRue;
		this.vitesseMoyenne = vitesseMoyenne;
		this.longueur = longueur;
		this.destination = destination;
	}
	
	public double getTempsTroncon() {
		if(this.tempsTroncon == 0) this.tempsTroncon = this.vitesseMoyenne * this.longueur;
		return this.tempsTroncon;
	}

	public void afficheTroncon() {
		System.out.println("TronconSortant : nomRue : "+this.nomRue+" vitesseMoyenne="+this.vitesseMoyenne+" longueur="+this.longueur+" idDestination : "+this.destination.getId());
	}
}

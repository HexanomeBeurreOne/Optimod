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
	 * Attributs
	 */
	private String nomRue;
	private double vitesseMoyenne;
	private double longueur;
	private Adresse origine;
	private Adresse destination;
	private Double tempsTroncon;
	
	/**
	 * Constructeur
	 */
	public Troncon(String nomRue, double vitesseMoyenne, double longueur, Adresse origine, Adresse destination) {
		this.nomRue = nomRue;
		this.vitesseMoyenne = vitesseMoyenne;
		this.longueur = longueur;
		this.destination = destination;
		this.origine = origine;
		this.tempsTroncon = 0.;
	}

	/**
	 * @return the origine
	 */
	public Adresse getOrigine() {
		return origine;
	}

	/**
	 * @return the destination
	 */
	public Adresse getDestination() {
		return destination;
	}

	/**
	 * @return the tempsTroncon
	 */
	public Double getTempsTroncon() {
		if(this.tempsTroncon == 0.) this.tempsTroncon = this.longueur /this.vitesseMoyenne;
		return this.tempsTroncon;
	}

	@Override
	public String toString() {
		return "Troncon [nomRue=" + nomRue + ", vitesseMoyenne="
				+ vitesseMoyenne + ", longueur=" + longueur + ", origine="
				+ origine + ", destination=" + destination + ", tempsTroncon="
				+ tempsTroncon + "]\n";
	}
}

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
	private Double tempsTroncon;
	
	/**
	 * Constructor
	 */
	public Troncon(String nomRue, double vitesseMoyenne, double longueur, Adresse origine, Adresse destination) {
		this.nomRue = nomRue;
		this.vitesseMoyenne = vitesseMoyenne;
		this.longueur = longueur;
		this.destination = destination;
		this.origine = origine;
		this.tempsTroncon = 0.;
	}

	public Adresse getOrigine() {
		return origine;
	}

	public Adresse getDestination() {
		return destination;
	}

	public double getTempsTroncon() {
		if(this.tempsTroncon == 0.) this.tempsTroncon = this.longueur /this.vitesseMoyenne;
		return this.tempsTroncon;
	}

	public void afficheTroncon() {
		System.out.println("TronconSortant : nomRue : "+this.nomRue+" vitesseMoyenne="+this.vitesseMoyenne+" longueur="+this.longueur+" idOrigine : "+this.origine.getId()+" idDestination : "+this.destination.getId());
	}

	@Override
	public String toString() {
		return "Troncon [nomRue=" + nomRue + ", vitesseMoyenne="
				+ vitesseMoyenne + ", longueur=" + longueur + ", origine="
				+ origine + ", destination=" + destination + ", tempsTroncon="
				+ tempsTroncon + "]\n";
	}
}

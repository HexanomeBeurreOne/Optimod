/**
 * 
 */
package model;

/**
 * @author Adrien Menella
 *
 */
public class Adresse {

	/**
	 * Attributes
	 */
	private int id;
	private int coordX;
	private int coordY;
	private Troncon tronconSortant;
	
	/**
	 * Constructor
	 */
	public Adresse(int id, int coordX, int coordY) {
		this.id = id;
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public Troncon getTronconSortant() {
		return tronconSortant;
	}

	public void setTronconSortant(Troncon tronconSortant) {
		this.tronconSortant = tronconSortant;
	}

	/**
	 * Add a new TronconSortant to the current Adresse
	 * @param newTronconSortant
	 */
	public void addTroncon(Troncon newTronconSortant) {
		this.tronconSortant = newTronconSortant;
	}
	
	
	

}

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
public class Adresse {

	/**
	 * Attributes
	 */
	private int id;
	private int coordX;
	private int coordY;
	private List<Troncon> tronconsSortants;
	
	/**
	 * Constructor
	 */
	public Adresse(int id, int coordX, int coordY) {
		this.id = id;
		this.coordX = coordX;
		this.coordY = coordY;
		this.tronconsSortants = new ArrayList<Troncon>();
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

	/**
	 * Add a new TronconSortant to the list of tronconsSortants of the current Adresse
	 * @param newTronconSortant
	 */
	public void addTroncon(Troncon newTronconSortant) {
		this.tronconsSortants.add(newTronconSortant);
	}
	
	public List<Troncon> getTroncons() {
		return this.tronconsSortants;
	}
	
	public void afficheAdresse() {
		System.out.println("Adresse "+this.id+" | x="+this.coordX+" y="+this.coordY);
		Iterator<Troncon> tronconsSortantsIterator = this.tronconsSortants.iterator();
		while(tronconsSortantsIterator.hasNext()) {
			Troncon currentTroncon = (Troncon) tronconsSortantsIterator.next();
			System.out.print("      ");
			currentTroncon.afficheTroncon();
		}
	}

	@Override
	public String toString() {
		return "Adresse [id=" + id + "]";
	}
	
	
}

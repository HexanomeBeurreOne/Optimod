/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * @author Adrien Menella
 *
 */
public class Adresse extends Observable {

	/**
	 * Attributes
	 */
	private int id;
	private int coordX;
	private int coordY;
	private List<Troncon> tronconsSortants;
	private boolean estSelectionnee;
	
	/**
	 * Constructor
	 */
	public Adresse(int id, int coordX, int coordY) {
		this.id = id;
		this.coordX = coordX;
		this.coordY = coordY;
		this.tronconsSortants = new ArrayList<Troncon>();
		this.estSelectionnee = false;
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

	public boolean isEstSelectionnee() {
		return estSelectionnee;
	}

	public void setEstSelectionnee(boolean estSelectionnee) {
		this.estSelectionnee = estSelectionnee;
		setChanged();
		notifyObservers(this);
	}

	public List<Troncon> getTronconsSortants() {
		return tronconsSortants;
	}

	/**
	 * Add a new TronconSortant to the list tronconsSortants of the current Adresse
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

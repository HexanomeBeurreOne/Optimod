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
	private boolean selectionnee;
	
	/**
	 * Constructor
	 */
	
	// Constructeur par defaut ne pas ENLEVER
	public Adresse() {
	}
	
	public Adresse(int id, int coordX, int coordY) {
		this.id = id;
		this.coordX = coordX;
		this.coordY = coordY;
		this.tronconsSortants = new ArrayList<Troncon>();
		this.selectionnee = false;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the coordX
	 */
	public int getCoordX() {
		return coordX;
	}

	/**
	 * @param coordX the coordX to set
	 */
	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	/**
	 * @return the coordY
	 */
	public int getCoordY() {
		return coordY;
	}

	/**
	 * @param coordY the coordY to set
	 */
	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	/**
	 * @return the tronconsSortants
	 */
	public List<Troncon> getTronconsSortants() {
		return tronconsSortants;
	}

	/**
	 * @param tronconsSortants the tronconsSortants to set
	 */
	public void setTronconsSortants(List<Troncon> tronconsSortants) {
		this.tronconsSortants = tronconsSortants;
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

	/**
	 * Ajoute un nouveau Troncon a la liste tronconSortant de l'Adresse courante
	 * @param newTronconSortant
	 */
	public void addTroncon(Troncon newTronconSortant) {
		this.tronconsSortants.add(newTronconSortant);
	}
	
	@Override
	public String toString() {
		return id + " aux coordonnees (" + coordX + ";" + coordY + ")";
	}
	
	
}

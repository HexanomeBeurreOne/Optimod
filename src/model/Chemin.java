package model;

import java.util.ArrayList;
import java.util.List;

public class Chemin {
	
	/**
	 * Attributes
	 */
	private Double tempsDeParcours;
	private List<Troncon> troncons;
	
	/**
	 * Constructor
	 */
	public Chemin() {
		tempsDeParcours = 0.;
		troncons = new ArrayList<Troncon>();
	}
	
	/**
	 * Constructor
	 * @param troncons
	 */
	public Chemin(List<Troncon> troncons) {
		this.troncons = new ArrayList<Troncon>(troncons);
	}
	
	/**
	 * @return the tempsDeParcours
	 */
	public Double getTempsDeParcours() {
		return tempsDeParcours;
	}

	/**
	 * @param tempsDeParcours the tempsDeParcours to set
	 */
	public void setTempsDeParcours(Double tempsDeParcours) {
		this.tempsDeParcours = tempsDeParcours;
	}

	/**
	 * @return the troncons
	 */
	public List<Troncon> getTroncons() {
		return troncons;
	}

	/**
	 * @param troncons the troncons to set
	 */
	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}

	/**
	 * ajoute un troncon au début de la liste troncons
	 * @param troncon
	 */
	public void addTroncon(Troncon troncon)	{
		troncons.add(0, troncon);
	}
	
	/**
	 * enlève le troncon de la liste troncons
	 * @param troncon
	 */
	public void removeTroncon(Troncon troncon)	{
		troncons.remove(troncon);
	}
	
	/**
	 * Retourne la première adresse du chemin courant
	 * retourne null en cas d'erreur
	 * @return
	 */
	public Adresse getDebut()	{
		if(troncons != null && troncons.size() >= 1)	{
			return troncons.get(0).getOrigine();
		}
		
		return null;
	}
	
	/**
	 * Retourne la dernière adresse du chemin courant
	 * retourne null en cas d'erreur
	 * @return
	 */
	public Adresse getFin()	{
		if(troncons != null && troncons.size() >= 1)	{
			return troncons.get(troncons.size()-1).getDestination();
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "Chemin [tempsDeParcours=" + tempsDeParcours + ", troncons="
				+ troncons + "]";
	}
	
}

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
	
	public void setTroncons(List<Troncon> troncons)	{
		this.troncons = new ArrayList<Troncon>(troncons);
		
	}
	
	public void addTroncon(Troncon troncon)	{
		troncons.add(0, troncon);
		
	}
	
	public void removeTroncon(Troncon troncon)	{
		troncons.remove(troncon);
	}
	
	public List<Troncon> getTroncons() {
		return troncons;
	}
	
	public Double getTempsDeParcours() {
		return tempsDeParcours;
	}

	public void setTempsDeParcours(Double tempsDeParcours) {
		this.tempsDeParcours = tempsDeParcours;
	}

	public Adresse getDebut()	{
		if(troncons != null && troncons.size() >= 1)	{
			troncons.get(0).getOrigine();
		}
		
		return null;
	}
	
	public Adresse getFin()	{
		if(troncons != null && troncons.size() >= 1)	{
			troncons.get(troncons.size()-1).getDestination();
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "Chemin [tempsDeParcours=" + tempsDeParcours + ", troncons="
				+ troncons + "]";
	}
	
}

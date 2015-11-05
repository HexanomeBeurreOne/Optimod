package model;

import java.util.ArrayList;
import java.util.List;

public class Chemin {
	
	/**
	 * Attributes
	 */
	Integer tempsDeParcours;
	List<Troncon> troncons;
	
	/**
	 * Constructor
	 */
	public Chemin() {
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
		troncons.add(troncon);
	}
	
	public void removeTroncon(Troncon troncon)	{
		troncons.remove(troncon);
	}
	
	public List<Troncon> getTroncons() {
		return troncons;
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
	
}

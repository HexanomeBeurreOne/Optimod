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
	
	public Adresse getDebut()	{
		if(troncons != null && troncons.size() >= 1)	{
			//troncons.get(0).getOrigin()
		}
		
		return null;
	}
	
	
}

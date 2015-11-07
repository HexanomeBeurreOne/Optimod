package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Tournee {

	/**
	 * Attributes
	 */
	private List<Etape> etapes;
	// Chemin qui mene de l'adresse de livraison de la derniere etape à l'entrepot
	private Chemin retourEntrepot;
	private double heureDebut;
	private double heureFin;
	
	/**
	 * Constructor
	 */
	public Tournee(int idEntrepot, int heureDebut, List<Livraison> livraisonsOrdonnees, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins) {
		this.etapes = new ArrayList<Etape>();
		this.heureDebut = heureDebut;
		double heureDepartEtape = heureDebut;
		int idDepartEtape = idEntrepot;
		int idArriveeEtape = 0;
		Etape etape;
		for(int i = 0 ; i < livraisonsOrdonnees.size() ; i++){
			idArriveeEtape = livraisonsOrdonnees.get(i).getAdresse().getId();
			etape = new Etape(livraisonsOrdonnees.get(i), plusCourtsChemins.get(idDepartEtape).get(idArriveeEtape), heureDepartEtape);
			etapes.add(0, etape);
			// 10 minutes de livraison avant de commencer l'etape suivante
			heureDepartEtape = etape.getHeureLivraison() + 10*60;
			idDepartEtape = idArriveeEtape;
		}
		retourEntrepot = plusCourtsChemins.get(idDepartEtape).get(idEntrepot);
		heureFin = heureDepartEtape + retourEntrepot.getTempsDeParcours();
		if(heureFin > 24*3600) System.out.println("La tournee se termine après minuit.");
	}
	
	public List<Etape> getEtapes() {
		return etapes;
	}
	
	public Chemin getRetourEntrepot() {
		return retourEntrepot;
	}
	
	public double getHeureDebut() {
		return heureDebut;
	}
	
	public double getHeureFin() {
		return heureFin;
	}

	public String toString(){
		return "Debut " + (int)heureDebut/3600 + ":"+ ((int)heureDebut%3600)/60 + ":"+ (int)heureDebut%60 + "\n"
			+ "Fin " + (int)heureFin/3600 + ":"+ ((int)heureFin%3600)/60 + ":"+ (int)heureFin%60;
	
	}
	
}

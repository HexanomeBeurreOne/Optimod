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
		int idDepartEtape = idEntrepot;
		int idArriveeEtape = 0;
		Etape etape;
		for(int i = 0 ; i < livraisonsOrdonnees.size() ; i++)
		{
			idArriveeEtape = livraisonsOrdonnees.get(i).getAdresse().getId();
			etape = new Etape(livraisonsOrdonnees.get(i), plusCourtsChemins.get(idDepartEtape).get(idArriveeEtape));
			etapes.add(etape);
			idDepartEtape = idArriveeEtape;
		}
		retourEntrepot = plusCourtsChemins.get(idDepartEtape).get(idEntrepot);
		calculHoraires();
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
	
	public void calculHoraires() {
		double heureDepartEtape = heureDebut;
		for(Etape etape : etapes) {
			etape.calculHeureLivraison(heureDepartEtape);
			// 10 minutes de livraison avant de commencer l'etape suivante
			heureDepartEtape = etape.getHeureLivraison() + 10*60;
		}
		heureFin = heureDepartEtape + retourEntrepot.getTempsDeParcours();
		if(heureFin > 24*3600) System.out.println("La tournee se termine après minuit.");
	}
	
	public void supprimerEtape(Livraison livraison, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins) {
		boolean found = false;
		int i;
		for( i = 0; i < etapes.size() && !found ; )	{
			if(etapes.get(i).getLivraison() == livraison) {
				found = true;
			}
			else {
				i++;
			}
		}
		if(found){
			etapes.remove(i);
			//TODO: Gerer le cas ou le plus court chemin n'a pas ete calcule
			if(etapes.size() == i) {
				// Si on a supprime la derniere etape on met a jour le chemin de retour
				int idAdresseDerniereEtape = etapes.get(i-1).getLivraison().getAdresse().getId();
				int idAdresseEntrepot = retourEntrepot.getFin().getId();
				retourEntrepot = plusCourtsChemins.get(idAdresseDerniereEtape).get(idAdresseEntrepot);
			}
			else {
				int idAdressePrecedente;
				if(i == 0){
					// Si on a supprime la premiere etape on met a jour le chemin de la nouvelle premiere en partant de l'entrepot
					idAdressePrecedente = retourEntrepot.getFin().getId();;
				}
				else {
					idAdressePrecedente = etapes.get(i-1).getLivraison().getAdresse().getId();
				}
				int idAdresseEtape = etapes.get(i).getLivraison().getAdresse().getId();
				etapes.get(i).setChemin(plusCourtsChemins.get(idAdressePrecedente).get(idAdresseEtape));
			}
			calculHoraires();
		}
		else {
			System.out.println("La livraison ne fait pas partie de la tournee");
		}
	}

	public String toString(){
		return "Tournee de " + etapes.size() + " etapes, " + 
				"debut a " + (int)heureDebut/3600 + ":"+ ((int)heureDebut%3600)/60 + ":"+ (int)heureDebut%60
				+ ", fin a " + (int)heureFin/3600 + ":"+ ((int)heureFin%3600)/60 + ":"+ (int)heureFin%60;
	
	}
	
}

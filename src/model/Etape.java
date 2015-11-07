package model;

public class Etape {

	/**
	 * Attributes
	 */
	private Livraison livraison;
	// Chemin qui mene au lieu de livraison
	private Chemin chemin;
	// Heure de depart de l'étape, avant de parcourir le chemin
	private double heureDepart;
	private double heureLivraison;
	private double retard;
	
	/**
	 * Constructor
	 */
	public Etape(Livraison livraison, Chemin chemin, double heureDepart) {
		this.livraison = livraison;
		this.chemin = chemin;
		this.heureDepart = heureDepart;
		heureLivraison = heureDepart + chemin.getTempsDeParcours();
		// Si on est en avance sur le debut de la fenetre horaire, on attend.
		if(heureLivraison < livraison.getFenetreLivraison().getHeureDebut())
		{
			heureLivraison = livraison.getFenetreLivraison().getHeureDebut();
		}
		retard = -1;
		if(heureLivraison > livraison.getFenetreLivraison().getHeureFin())
		{
			retard = livraison.getFenetreLivraison().getHeureFin() - heureLivraison;
		}
	}
	
	public Livraison getLivraison() {
		return livraison;
	}

	public Chemin getChemin() {
		return chemin;
	}	
	
	public double getHeureDepart() {
		return heureDepart;
	}
	
	public double getHeureLivraison() {
		return heureLivraison;
	}
	
	public double getHeureRetard() {
		return retard;
	}
	
}

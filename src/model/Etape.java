package model;

public class Etape {

	/**
	 * Attributs
	 */
	private Livraison livraison;
	// Chemin qui mene au lieu de livraison
	private Chemin chemin;
	// Heure de debut de livraison
	private double heureLivraison;
	// Retard eventuel sur la fenetre de livraison, sinon -1
	private double retard;
	
	/**
	 * Constructeur
	 */
	public Etape(Livraison livraison, Chemin chemin) {
		this.livraison = livraison;
		this.chemin = chemin;
		this.heureLivraison = -1;
		this.retard = -1;
	}
	
	public Etape(Livraison livraison, Chemin chemin, double heureDepart) {
		this.livraison = livraison;
		this.chemin = chemin;
		calculerHeureLivraison(heureDepart);
	}
	
	
	public void calculerHeureLivraison(double heureDepart){
		heureLivraison = heureDepart + chemin.getTempsDeParcours();
		// Si on est en avance sur le debut de la fenetre horaire, on attend.
		if(heureLivraison < livraison.getFenetreLivraison().getHeureDebut())
		{
			heureLivraison = livraison.getFenetreLivraison().getHeureDebut();
		}
		retard = -1;
		if(heureLivraison > livraison.getFenetreLivraison().getHeureFin())
		{
			retard = heureLivraison - livraison.getFenetreLivraison().getHeureFin();
		}
	}
	
	public Livraison getLivraison() {
		return livraison;
	}

	public Chemin getChemin() {
		return chemin;
	}
	
	public void setChemin(Chemin chemin) {
		this.chemin = chemin;
	}
	
	public double getHeureLivraison() {
		return heureLivraison;
	}
	
	public double getHeureRetard() {
		return retard;
	}
	
}

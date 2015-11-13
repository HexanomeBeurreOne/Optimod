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
	
	/**
	 * @return the livraison
	 */
	public Livraison getLivraison() {
		return livraison;
	}

	/**
	 * @param livraison the livraison to set
	 */
	public void setLivraison(Livraison livraison) {
		this.livraison = livraison;
	}

	/**
	 * @return the chemin
	 */
	public Chemin getChemin() {
		return chemin;
	}

	/**
	 * @param chemin the chemin to set
	 */
	public void setChemin(Chemin chemin) {
		this.chemin = chemin;
	}

	/**
	 * @return the heureLivraison
	 */
	public double getHeureLivraison() {
		return heureLivraison;
	}

	/**
	 * @param heureLivraison the heureLivraison to set
	 */
	public void setHeureLivraison(double heureLivraison) {
		this.heureLivraison = heureLivraison;
	}

	/**
	 * @return the retard
	 */
	public double getRetard() {
		return retard;
	}

	/**
	 * @param retard the retard to set
	 */
	public void setRetard(double retard) {
		this.retard = retard;
	}

	/**
	 * calcule l'heure de livraison de la livraison a partir de l'heure de depart de la fenetre de livraison
	 * @param heureDepart
	 */
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

	@Override
	public String toString() {
		String str = "\tLivraison du client " + livraison.getClient() + " a l'adresse " + livraison.getAdresse() + "\n\tHeure d'arrivee a " + secondeToHeure(this.heureLivraison);
		
		if(retard!=-1.)
		{
			str += " avec un retard prevu de " + secondeToHeure(this.retard);
		}
		
		str += "\n";
		
		return str;
	}
	
	public String secondeToHeure (double heureEnSeconde) {
		if (heureEnSeconde < 0) return "0";
		return (int)heureEnSeconde/3600 + ":"+ ((int)heureEnSeconde%3600)/60 + ":"+ (int)heureEnSeconde%60;
	}
}

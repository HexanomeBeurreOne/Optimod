/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Adrien Menella
 *
 */
public class DemandeLivraisons {

	/**
	 * Attributs
	 */
	private Adresse entrepot;
	private List<FenetreLivraison> fenetresLivraisons;
	private int heureDepart;
	
	/**
	 * Constructeur
	 */
	public DemandeLivraisons() {
		this.entrepot = null;
		this.fenetresLivraisons = new ArrayList<FenetreLivraison>();
		this.heureDepart = 8*3600;
	}
	
	/**
	 * Retourne l'objet FenetreLivraison dont heureDebut correspond avec le parametre passe
	 * Retourne null sinon
	 * @param heureDebut
	 * @return
	 */
	public FenetreLivraison getFenetreLivraison(double heureDebut) {
		Iterator<FenetreLivraison> itFenetresLivraisons = this.fenetresLivraisons.iterator();
		while(itFenetresLivraisons.hasNext()) {
			FenetreLivraison fenetreLivraisonCourante = (FenetreLivraison) itFenetresLivraisons.next();
			if(fenetreLivraisonCourante.getHeureDebut()==heureDebut) return fenetreLivraisonCourante;
		}
		return null;
	}
	
	/**
	 * Ajoute une nouvelle Livraison a la fenetreLivraison passee en parametre 
	 * @param nouvelleLivraison
	 * @param fenetreLivraison
	 */
	public void addLivraison(Livraison nouvelleLivraison, FenetreLivraison fenetreLivraison) {
		if (this.fenetresLivraisons.contains(fenetreLivraison)) {
			FenetreLivraison fenetreLivraisonTrouvee = this.fenetresLivraisons.get(this.fenetresLivraisons.indexOf(fenetreLivraison));
			fenetreLivraisonTrouvee.addLivraison(nouvelleLivraison);
		}
	}
	
	/**
	 * Supprime une Livraison a partir de son Adresse passee en parametre
	 * @param adresseLivraison
	 */
	public void supprimerLivraison(Adresse adresseLivraison) {
		Livraison livraisonASupprimer = getLivraison(adresseLivraison);
		FenetreLivraison fenetre = livraisonASupprimer.getFenetreLivraison();
		fenetre.getLivraisons().remove(livraisonASupprimer);
	}
	
	/**
	 * Ajoute une nouvelle FenetreLivraison a la liste fenetresLivraisons
	 * @param nouvelleFenetreLivraison
	 */
	public void addFenetreLivraison(FenetreLivraison nouvelleFenetreLivraison) {
		this.fenetresLivraisons.add(nouvelleFenetreLivraison);
	}
	
	/**
	 * remove a FenetreLivraison to the list fenetresLivraisons
	 * @param fenetreLivraison
	 */
	public void removeFenetreLivraison(FenetreLivraison fenetreLivraison) {
		if( this.fenetresLivraisons.contains(fenetreLivraison) ) this.fenetresLivraisons.remove(fenetreLivraison);
	}

	public Adresse getEntrepot() {
		return entrepot;
	}

	public void setEntrepot(Adresse entrepot) {
		this.entrepot = entrepot;
	}
	
	public int getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(int heureDepart) {
		this.heureDepart = heureDepart;
	}

	public List<FenetreLivraison> getFenetresLivraisons() {
		return fenetresLivraisons;
	}

	public void setFenetresLivraisons(List<FenetreLivraison> fenetresLivraisons) {
		this.fenetresLivraisons = fenetresLivraisons;
	}
	
	public List<Livraison> getAllLivraisons() {
		List<Livraison> livraisons = new ArrayList<Livraison>();
		for (FenetreLivraison fen : fenetresLivraisons){
			livraisons.addAll(fen.getLivraisons());
		}
		return livraisons;
	}
	
	public Livraison getLivraison(Adresse adresse){
		List<Livraison> livraisons = getAllLivraisons();
		for (Livraison liv : livraisons){
			if(liv.getAdresse() == adresse) return liv;
		}
		return null;
	}

	public void afficheDemandeLivraisons() {
		System.out.println("DemandeLivraison : idEntrepot="+this.entrepot.getId());
		System.out.println("Liste livraisons : ");
		Iterator<FenetreLivraison> itFenetresLivraisons = this.fenetresLivraisons.iterator();
		while(itFenetresLivraisons.hasNext()) {
			FenetreLivraison fenetreLivraisonCourante = (FenetreLivraison) itFenetresLivraisons.next();
			System.out.print("   ");
			fenetreLivraisonCourante.afficheFenetreLivraison();
		}
	}
	
	public Livraison chercheLivraison(int x0, int y0) {
		Iterator<FenetreLivraison> itFL = this.fenetresLivraisons.iterator();
		FenetreLivraison fenetreLivraisonCourante;
		Livraison livraisonTrouvee;
		while(itFL.hasNext()){
			fenetreLivraisonCourante = itFL.next();
			livraisonTrouvee = fenetreLivraisonCourante.chercheLivraison(x0, y0);
			if(livraisonTrouvee!=null) return livraisonTrouvee;
		}
		return null;
		
	}
	
	public void setLivraisonSelectionnee(Livraison livraison, boolean selectionnee) {
		Iterator<FenetreLivraison> itFL = this.fenetresLivraisons.iterator();
		FenetreLivraison fenetreLivraison;
		while(itFL.hasNext()){
			fenetreLivraison = itFL.next();
			if( fenetreLivraison.getLivraisons().contains(livraison) ) fenetreLivraison.setLivraisonSelectionnee(livraison, selectionnee);
		}
	}
	
}

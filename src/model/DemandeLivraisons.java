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
	 * @return the entrepot
	 */
	public Adresse getEntrepot() {
		return entrepot;
	}

	/**
	 * @param entrepot the entrepot to set
	 */
	public void setEntrepot(Adresse entrepot) {
		this.entrepot = entrepot;
	}

	/**
	 * @return the fenetresLivraisons
	 */
	public List<FenetreLivraison> getFenetresLivraisons() {
		return fenetresLivraisons;
	}

	/**
	 * @param fenetresLivraisons the fenetresLivraisons to set
	 */
	public void setFenetresLivraisons(List<FenetreLivraison> fenetresLivraisons) {
		this.fenetresLivraisons = fenetresLivraisons;
	}

	/**
	 * @return the heureDepart
	 */
	public int getHeureDepart() {
		return heureDepart;
	}

	/**
	 * @param heureDepart the heureDepart to set
	 */
	public void setHeureDepart(int heureDepart) {
		this.heureDepart = heureDepart;
	}

	/**
	 * Retourne l'objet FenetreLivraison dont heureDebut correspond avec le parametre passe
	 * Retourne null sinon
	 * @param heureDebut
	 * @return
	 */
	public FenetreLivraison getFenetreLivraison(int heureDebut) {
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
	 * Enleve une FenetreLivraison de la liste fenetresLivraisons
	 * @param fenetreLivraison
	 */
	public void removeFenetreLivraison(FenetreLivraison fenetreLivraison) {
		if( this.fenetresLivraisons.contains(fenetreLivraison) ) this.fenetresLivraisons.remove(fenetreLivraison);
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

//	public void afficheDemandeLivraisons() {
//		System.out.println("DemandeLivraison : idEntrepot="+this.entrepot.getId());
//		System.out.println("Liste livraisons : ");
//		Iterator<FenetreLivraison> itFenetresLivraisons = this.fenetresLivraisons.iterator();
//		while(itFenetresLivraisons.hasNext()) {
//			FenetreLivraison fenetreLivraisonCourante = (FenetreLivraison) itFenetresLivraisons.next();
//			System.out.print("   ");
//			fenetreLivraisonCourante.afficheFenetreLivraison();
//		}
//	}
	
	/**
	 * retourne la Livraison dont les coordonnées sont les plus proche des points x0 et y0 dans un cercle de rayon pixels
	 * @param x0
	 * @param y0
	 * @param rayon
	 * @return
	 */
	public Livraison chercheLivraison(int x0, int y0, int rayon) {
		Iterator<FenetreLivraison> itFL = this.fenetresLivraisons.iterator();
		FenetreLivraison fenetreLivraisonCourante;
		Livraison livraisonTrouvee;
		while(itFL.hasNext()){
			fenetreLivraisonCourante = itFL.next();
			livraisonTrouvee = fenetreLivraisonCourante.chercheLivraison(x0, y0, rayon);
			if(livraisonTrouvee!=null) return livraisonTrouvee;
		}
		return null;
		
	}
	
	/**
	 * définie l'attribut selectionnee de l'objet Livraison livraison à la valeur du booleen selectionnee passé en paramètre
	 * @param livraison est la livraison que l'on doit modifier
	 * @param selectionnee est la nouvelle valeur de l'attribut de la livraison
	 */
	public void setLivraisonSelectionnee(Livraison livraison, boolean selectionnee) {
		Iterator<FenetreLivraison> itFL = this.fenetresLivraisons.iterator();
		FenetreLivraison fenetreLivraison;
		while(itFL.hasNext()){
			fenetreLivraison = itFL.next();
			if( fenetreLivraison.getLivraisons().contains(livraison) ) fenetreLivraison.setLivraisonSelectionnee(livraison, selectionnee);
		}
	}
	
}

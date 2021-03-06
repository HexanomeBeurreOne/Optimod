/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Set;

import model.tsp.Graphe;
import model.tsp.GrapheOptimod;
import model.tsp.TSP;
import model.tsp.TSP2;

/**
 * @author Adrien Menella
 *
 */
public class Plan extends Observable {

	/**
	 * Attributs
	 */
	private String nom;
	private List<Adresse> adresses;
	private List<Troncon> troncons;
	private DemandeLivraisons demandeLivraisons;
	// Hashmap better than Hashtable in single threaded environment.
	// The key of the outer Hashtable is the id of the starting Adresse, 
	// The key of the inner Hashtable is the id of the ending Adresse, 
	private Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins;
	private Tournee tournee;
	
	/**
	 * Constructeur
	 */
	public Plan() {
		this.nom = "";
		this.adresses = new ArrayList<Adresse>();
		this.troncons = new ArrayList<Troncon>();
		this.demandeLivraisons = new DemandeLivraisons();
		this.plusCourtsChemins = new Hashtable<Integer,Hashtable<Integer,Chemin>>();
		this.tournee = new Tournee();
	}
	
	/**
	 * @return the plusCourtsChemins
	 */
	public Hashtable<Integer, Hashtable<Integer, Chemin>> getPlusCourtsChemins() {
		return plusCourtsChemins;
	}

	/**
	 * @param plusCourtsChemins the plusCourtsChemins to set
	 */
	public void setPlusCourtsChemins(Hashtable<Integer, Hashtable<Integer, Chemin>> plusCourtsChemins) {
		this.plusCourtsChemins = plusCourtsChemins;
	}

	/**
	 * @return the noeudsNoirs
	 */
	public HashSet<Adresse> getnoeudsNoirs() {
		return noeudsNoirs;
	}

	/**
	 * @param noeudsNoirs the noeudsNoirs to set
	 */
	public void setnoeudsNoirs(HashSet<Adresse> noeudsNoirs) {
		this.noeudsNoirs = noeudsNoirs;
	}

	/**
	 * @return the noeudsGris
	 */
	public HashSet<Adresse> getnoeudsGris() {
		return noeudsGris;
	}

	/**
	 * @param noeudsGris the noeudsGris to set
	 */
	public void setnoeudsGris(HashSet<Adresse> noeudsGris) {
		this.noeudsGris = noeudsGris;
	}

	/**
	 * @return the distance
	 */
	public HashMap<Adresse, Double> getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(HashMap<Adresse, Double> distance) {
		this.distance = distance;
	}

	/**
	 * @return the predecessors
	 */
	public HashMap<Adresse, Adresse> getPredecessors() {
		return predecessors;
	}

	/**
	 * @param predecessors the predecessors to set
	 */
	public void setPredecessors(HashMap<Adresse, Adresse> predecessors) {
		this.predecessors = predecessors;
	}

	/**
	 * @return the tournee
	 */
	public Tournee getTournee() {
		return tournee;
	}

	public DemandeLivraisons getDemandeLivraisons() {
		return demandeLivraisons;
	}

	public void setDemandeLivraisons(DemandeLivraisons demandeLivraisons) {
		this.demandeLivraisons = demandeLivraisons;
		setChanged();
		notifyObservers();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
		setChanged();
		notifyObservers();
	}

	public List<Adresse> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Adresse> adresses) {
		this.adresses = adresses;
		setChanged();
		notifyObservers();
	}

	public List<Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
		setChanged();
		notifyObservers();
	}

	/**
	 * Ajoute un objet Adresse a la liste adresses
	 * @param newAdresse est l'adresse a ajouter
	 */
	public void addAdresse(Adresse newAdresse) {
		this.adresses.add(newAdresse);
	}
	
	/**
	 * Enleve un objet Adresse a la liste adresses
	 * @param adresseToRemove est l'adresse a enlever
	 */
	public void removeAdresse(Adresse adresseToRemove) {
		if(this.adresses.contains(adresseToRemove)) this.adresses.remove(adresseToRemove);
	}
	
	 /**
	  * Ajoute un objet Troncon a la liste troncons
	  * @param newTroncon est le troncon a ajouter
	  */
	public void addTroncon(Troncon newTroncon) {
		this.troncons.add(newTroncon);
	}
	
	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * definie l'attribut selectionnee de l'objet objet a la valeur du booleen selectionnee passe en parametre
	 * @param objet
	 * @param selectionne
	 */
	public void setObjetSelectionne(Object objet, boolean selectionne) {
		if (objet.getClass().getName() == "model.Adresse") {
			Adresse adresse = (Adresse)objet;
			this.getAdresseById(adresse.getId()).setSelectionnee(selectionne);
		} else if (objet.getClass().getName() == "model.Livraison") {
			Livraison livraison = (Livraison) objet;
			this.demandeLivraisons.setLivraisonSelectionnee(livraison, selectionne);
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Calcule une nouvelle tournee pour la demande de livraisons 
	 */
	public void calculTournee()	{
		calculPlusCourtsChemins();
		Integer[] ordreLivraisons = calculOrdreLivraisons();
		tournee = new Tournee(demandeLivraisons, ordreLivraisons, plusCourtsChemins);
		this.setTournee(tournee);
		//System.out.println(tournee);
	}
	
	/**
	 * algorithme TSP
	 * @return indices des livraisons dans demandeLivraisons dans l'ordre trouve par l'algorithme
	 * du voyageur de commerce 
	 */
	private Integer[] calculOrdreLivraisons() {
		TSP tsp = new TSP2();
		Graphe g = new GrapheOptimod(demandeLivraisons, plusCourtsChemins);
//		long tempsDebut = System.currentTimeMillis();
		tsp.chercheSolution(60000, g);
//		System.out.print("Solution de longueur "+tsp.getCoutSolution()+" trouvee en "
//				+(System.currentTimeMillis() - tempsDebut)+"ms : ");
//		Integer[] solution = tsp.getSolution();
//		for (Integer i : solution){
//			System.out.print(i + " ");
//		}
//		System.out.println();
		return tsp.getSolutions();
	}

	/**
	 * Calcul le plus court chemin entre les points de livraisons d'une meme fenetre et ceux de la suivante
	 */
	private void calculPlusCourtsChemins()	{
		//The list is ordered
		List<FenetreLivraison> fenetres = demandeLivraisons.getFenetresLivraisons();
		//Get entrepot
		Adresse entrepot = demandeLivraisons.getEntrepot();
		//Liste contenant l'entrepot, qui est le depart et l'arrivee
		List<Adresse> entrepotList = new ArrayList<Adresse>(); 
		entrepotList.add(entrepot);
		//Liste de sous-listes d'Adresses correspondants aux adresses des points de livraison par fenetre
		List<List<Adresse>> adressesFenList = new ArrayList<List<Adresse>>();
		adressesFenList.add(entrepotList);
		for(FenetreLivraison fen : fenetres)
		{
			List<Adresse> adressesFen = new ArrayList<Adresse>();
			for(Livraison liv:fen.getLivraisons()){
				adressesFen.add(liv.getAdresse());
			}
			adressesFenList.add(adressesFen);
		}
		adressesFenList.add(entrepotList);
		
		Adresse departDijkstra;
		List<Adresse> ciblesDijkstra;

		for(int i = 1; i < adressesFenList.size(); i++)
		{
			for(int j = 0; j < adressesFenList.get(i-1).size(); j++)
			{
				//On recupere une adresse d'une premiere fenetre de livraison
				departDijkstra = adressesFenList.get(i-1).get(j);
				Integer departId = departDijkstra.getId();
				//On met dans ciblesDijkstra les adresses de cette premiere fenetre de livraison et de la suivante
				ciblesDijkstra = new ArrayList<Adresse>(adressesFenList.get(i));
				ciblesDijkstra.addAll(adressesFenList.get(i-1));
				//On retire de ciblesDijkstra le departDijkstra
				ciblesDijkstra.remove(departDijkstra);
				Hashtable<Integer, Chemin> resDijkstra = dijkstra(departDijkstra, ciblesDijkstra);
				plusCourtsChemins.put(departId, resDijkstra);
			}
		}
		//System.out.println(plusCourtsChemins);
	}
	
	private HashSet<Adresse> noeudsNoirs;
	private HashSet<Adresse> noeudsGris;
	private HashMap<Adresse, Double> distance;
	private HashMap<Adresse, Adresse> predecessors;
	
	/**
	 * Dijkstra() trouve les plus courts chemins de depart jusqu'aux adresses cibles et retourne le resultat
	 * dans une HashTable composoe d'entites cle-valeur ou la cle est l'id de l'adresse et la valeur le chemin de
	 * depart a cette adresse.
	 * @param depart
	 * @param cibles
	 * @return
	 */
	public Hashtable<Integer, Chemin> dijkstra(Adresse depart, List<Adresse> cibles) {
		noeudsNoirs = new HashSet<Adresse>();
		noeudsGris = new HashSet<Adresse>();
		distance = new HashMap<Adresse, Double>();
		predecessors = new HashMap<Adresse, Adresse>();
	    distance.put(depart, 0.);
	    noeudsGris.add(depart);
	    while (noeudsGris.size() > 0) {
	      Adresse node = getMinimum(noeudsGris);
	      noeudsNoirs.add(node);
	      noeudsGris.remove(node);
	      trouveTempsMinimal(node);
	    }
	    
	    Hashtable<Integer, Chemin> result = new Hashtable<Integer, Chemin>();
	    if(noeudsNoirs.containsAll(cibles))	{
		    for(int i=0; i < cibles.size(); i++)	{
		    	Adresse precede = cibles.get(i);
		    	Integer id = precede.getId();
		    	Chemin chemin = new Chemin();
		    	chemin.setTempsDeParcours(distance.get(cibles.get(i)));
		    	//Iterate to get each troncon from cibles[i] to depart
		    	while(precede.getId() != depart.getId())	{
		    		Adresse adressePrec = predecessors.get(precede);
		    		List<Troncon> tronconsSortants = adressePrec.getTronconsSortants();
		    		for(int j=0; j < tronconsSortants.size(); j++)	{
		    			if(tronconsSortants.get(j).getDestination().getId() == precede.getId()) {
		    				chemin.addTroncon(tronconsSortants.get(j));
		    				break;
		    			}
		    		}
		    		precede = adressePrec;
		    	}
		    	result.put(id, chemin);
		    }
		    
		    return result;
		} else {
			System.out.println("This is not a connex graph !");
			return null;
		}
	  }
	
	/**
	 * trouveTempsMinimal() trouve les temps minimaux
	 * @param node
	 */
	private void trouveTempsMinimal(Adresse noeud) {
	    List<Adresse> adjacentNodes = trouveSuivants(noeud);
	    for (Adresse cible : adjacentNodes) {
	      if (trouverPlusPetitTemps(cible) > trouverPlusPetitTemps(noeud)
	          + getTemps(noeud, cible)) {
	        distance.put(cible, trouverPlusPetitTemps(noeud)
	            + getTemps(noeud, cible));
	        predecessors.put(cible, noeud);
	        noeudsGris.add(cible);
	      }
	    }

	  }

	/**
	 * getTemps() retourne la valeur des arcs du graphe en temps
	 * @param node
	 * @param target
	 * @return
	 */
	  private double getTemps(Adresse noeud, Adresse cible) {
	    for (Troncon arrete : troncons) {
	      if (arrete.getOrigine().equals(noeud)
	          && arrete.getDestination().equals(cible)) {
	        return arrete.getTempsTroncon();
	      }
	    }
	    throw new RuntimeException("Should not happen");
	  }
	  
	  /**
	   * 
	   * @param node
	   * @return
	   */
	  private List<Adresse> trouveSuivants(Adresse noeud) {
		    List<Adresse> suivants = new ArrayList<Adresse>();
		    for (Troncon edge : troncons) {
		      if (edge.getOrigine().equals(noeud)
		          && !estNoir(edge.getDestination())) {
		    	  suivants.add(edge.getDestination());
		      }
		    }
		    return suivants;
		  }
	  
	  private boolean estNoir(Adresse node) {
		    return noeudsNoirs.contains(node);
	  }
	  
	  private Adresse getMinimum(Set<Adresse> adresseSet) {
	    Adresse minimum = null;
	    for (Adresse ad : adresseSet) {
	      if (minimum == null) {
	        minimum = ad;
	      } else {
	        if (trouverPlusPetitTemps(ad) < trouverPlusPetitTemps(minimum)) {
	          minimum = ad;
	        }
	      }
	    }
	    return minimum;
	  }
	  
	  /**
	   * trouverPlusPetitTemps() trouve le plus petit cout d'un noeud
	   * @param destination
	   * @return
	   */
	  private Double trouverPlusPetitTemps(Adresse destination) {
	    Double d = (Double) distance.get(destination);
	    if (d == null) {
	      return Double.MAX_VALUE;
	    } else {
	      return d;
	    }
	  }
	
	/**
	 * Get the Adresse which id is corresponding to the given parameter, return null if it does not contain
	 * @param id
	 * @return
	 */
	public Adresse getAdresseById(int id) {
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		while(adressesIterator.hasNext()) {
			Adresse currentAdresse = (Adresse) adressesIterator.next();
			if(currentAdresse.getId()==id) return currentAdresse;
		}
		return null;
	}
	
	/**
	 * retourne la coordonnee x minimale du plan
	 * @return minX
	 */
	public int getMinX() {
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		Adresse currentAdresse;
		int minX = 9999999;
		while(adressesIterator.hasNext()) {
			currentAdresse = (Adresse) adressesIterator.next();
			if(currentAdresse.getCoordX()<=minX) minX = currentAdresse.getCoordX();
		}
		return minX;
	}
	
	/**
	 * retourne la coordonnee x maximale du plan
	 * @return
	 */
	public int getMaxX() {
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		Adresse currentAdresse;
		int maxX = -1;
		while(adressesIterator.hasNext()) {
			currentAdresse = (Adresse) adressesIterator.next();
			if(currentAdresse.getCoordX()>=maxX) maxX = currentAdresse.getCoordX();
		}
		return maxX;
	}
	
	/**
	 * retourne la coordonnee y minimale du plan
	 * @return
	 */
	public int getMinY() {
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		Adresse currentAdresse;
		int minY = 9999999;
		while(adressesIterator.hasNext()) {
			currentAdresse = (Adresse) adressesIterator.next();
			if(currentAdresse.getCoordY()<=minY) minY = currentAdresse.getCoordY();
		}
		return minY;
	}
	
	/**
	 * retourne la coordonnee y maximale du plan
	 * @return
	 */
	public int getMaxY() {
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		Adresse currentAdresse;
		int maxY = -1;
		while(adressesIterator.hasNext()) {
			currentAdresse = (Adresse) adressesIterator.next();
			if(currentAdresse.getCoordY()>=maxY) maxY = currentAdresse.getCoordY();
		}
		return maxY;
	}
	
	/**
	 * nouveauxPlusCourtsChemins() trouve les nouveaux plus court chemin entre l'adresse de depart et toutes les adresses
	 * de la fenetre de l'adresse d'arrivee
	 * @param depart
	 * @param arrivee
	 */
	public void nouveauxPlusCourtsChemins(Adresse depart, Adresse arrivee) {
		List<Adresse> cibles = new ArrayList<Adresse>();
		if(arrivee == tournee.getEntrepot()) {
			cibles.add(arrivee);
		}
		else {
			List<Livraison> livraisonsMemeFenetre = demandeLivraisons.getLivraison(arrivee).getFenetreLivraison().getLivraisons();
			for(Livraison liv : livraisonsMemeFenetre) {
				cibles.add(liv.getAdresse());
			}
		}
		Hashtable<Integer, Chemin> resDijkstra = dijkstra(depart, cibles);
		Set<Entry<Integer, Chemin>> entrySet = resDijkstra.entrySet();
		Iterator<Entry<Integer, Chemin>> it = entrySet.iterator();
		while(it.hasNext())	{
			Entry<Integer, Chemin> entry = it.next();
			int idAdresseCible = entry.getKey();
			Chemin dureePlusCourtChemin = entry.getValue();
			System.out.println("Calcul plus court chemin de "+depart.getId()
								+" a "+idAdresseCible);
			if(plusCourtsChemins.get(depart.getId()) == null) {
				Hashtable<Integer, Chemin> innerHashtableVide = new Hashtable<Integer, Chemin>();
				plusCourtsChemins.put(depart.getId(), innerHashtableVide);
			}
			plusCourtsChemins.get(depart.getId()).put(idAdresseCible, dureePlusCourtChemin);
		}
	}
	
	/**
	 * Verifie que la livraison existe, puis calcul, si besoin, le plus court chemin dont on aura besoin pour corriger la tournee apres suppression
	 * @param Livraison a supprimer
	 * @return Indice de l'etape a supprimer, -1 si la livraison ne fais pas partie de la tournee
	 */
	public int testSuppression(Adresse livraisonAdresse){		
		int indiceEtape = tournee.trouverIndiceEtape(livraisonAdresse);
		// On verifie si on a suffisamment de plus courts chemins uniquement si la livraison fait partie et n'est pas la seule livraison de la tournee
		if(indiceEtape != -1 && tournee.getEtapes().size() != 1) {
			Adresse depart;
			Adresse arrivee;
			if(indiceEtape == tournee.getEtapes().size()-1) {
				// On souhaite supprimer la derniere etape, le chemin retourEntrepot va etre mis a jour
				depart = tournee.getEtapes().get(indiceEtape-1).getLivraison().getAdresse();
				arrivee = tournee.getEntrepot();
			}
			else {
				if(indiceEtape == 0){
					// On souhaite supprimer la premiere etape, on met a jour le chemin de la nouvelle premiere etape en partant de l'entrepot
					depart = tournee.getEntrepot();
				}
				else {
					depart = tournee.getEtapes().get(indiceEtape-1).getLivraison().getAdresse();
				}
				arrivee = tournee.getEtapes().get(indiceEtape+1).getLivraison().getAdresse();
			}
			// Calcul de nouveaux plus courts chemins a l'aide de dijkstra si celui dont on a besoin n'a pas ete precedemment
			if(plusCourtsChemins.get(depart.getId()).get(arrivee.getId()) == null) {
				nouveauxPlusCourtsChemins(depart, arrivee);
			}
		}
		return indiceEtape;
	}
	
	/**
	 * supprimerLivraison() supprime une livraison et l'etape associee
	 * @param adresseLivraison adresse a supprimer
	 */
	public void supprimerLivraison(Adresse adresseLivraison) {
		int indiceEtape = testSuppression(adresseLivraison);
		if(indiceEtape != -1) {
			// Suppression de l'etape dans tournee
			tournee.supprimerEtape(indiceEtape, plusCourtsChemins);
			// Suppression de la livraison dans demandeLivraisons
			demandeLivraisons.supprimerLivraison(adresseLivraison);
			setChanged();
			notifyObservers();
		}
		else {
			System.out.println("La livraison ne fait pas partie de la tournee.");
		}
	}
	
	/**
	 * Ajoute une livraison a la tournee sans demander la fenetre dans laquelle la livraison doit etre ajoutee.
	 * Seule des objets Adresses sont passees en parametres en plus du client, et non des objets Livraison
	 * 
	 * @param client
	 * @param precLivraisonAdresse
	 * @param nouvellelivraisonAdresse
	 * @return
	 */
	public FenetreLivraison ajouterLivraison(int client, Adresse precLivraisonAdresse, Adresse nouvellelivraisonAdresse) {
		Livraison livPrec = demandeLivraisons.getLivraison(precLivraisonAdresse);
		FenetreLivraison fenetreDeNouvelleLiv = null;
		if(livPrec != null)	{
			fenetreDeNouvelleLiv = livPrec.getFenetreLivraison();
			//TODO: (Optionnel) Gerer les bords de fenetre
			
			ajouterLivraisonAvecFenetre(client, precLivraisonAdresse, nouvellelivraisonAdresse, fenetreDeNouvelleLiv);
		}
		
		return fenetreDeNouvelleLiv;
	}
	
	/**
	 * Ajoute une livraison a la tournee sans demander la fenetre dans laquelle la livraison doit etre ajoutee.
	 * Seule des objets Adresses sont passees en parametres en plus du client et de la fenetre, et non des objets Livraison
	 * @param client
	 * @param precLivraisonAdresse
	 * @param nouvellelivraisonAdresse
	 * @param fenetre
	 */
	public void ajouterLivraisonAvecFenetre(int client, Adresse adresseLivraisonPrec, Adresse adresseNouvelleLivraison, FenetreLivraison fenetre) {
		Livraison nouvelleLivraison = new Livraison(client, adresseNouvelleLivraison, fenetre);
		
		demandeLivraisons.addLivraison(nouvelleLivraison, fenetre);
		int resTest = testAjout(adresseLivraisonPrec, adresseNouvelleLivraison);
		if(resTest != -2) {
			tournee.ajouterEtape(nouvelleLivraison, adresseLivraisonPrec, plusCourtsChemins);
			setChanged();
			notifyObservers();
		}
	}
	
	/**
	 * testAjout() verifie que les chemins necessaire a la modification de la tournee sont deja existant et dans le
	 * cas contraire les calcule
	 * @param adresseLivraisonPrec
	 * @param adresseLivraison
	 * @return
	 */
	public int testAjout(Adresse adresseLivraisonPrec, Adresse adresseLivraison)	{
		Adresse arrivee;
		int indiceEtapePrec;
		// Gestion du cas ou la livraison precedente est en fait l'entrepot 
		if(adresseLivraisonPrec == demandeLivraisons.getEntrepot()){
			indiceEtapePrec = -1;
		}
		// Il y a une etape precedente
		else {
			indiceEtapePrec = tournee.trouverIndiceEtape(adresseLivraisonPrec);
			//Si on ne trouve pas l'etape
			if(indiceEtapePrec == -1) return -2;
		}
		// Si il n'y a pas d'etape suivante
		if(indiceEtapePrec + 1 == tournee.getEtapes().size()) {
			// On souhaite ajouter apr�s la derniere etape, le chemin retourEntrepot va etre mis a jour
			arrivee = tournee.getEntrepot();
		}
		else {
			arrivee = tournee.getEtapes().get(indiceEtapePrec+1).getLivraison().getAdresse();
		}

		// Calcul de nouveaux plus courts chemins a l'aide de dijkstra si celui dont on a besoin n'a pas ete precedemment
		if(plusCourtsChemins.get(adresseLivraisonPrec.getId()).get(adresseLivraison.getId()) == null) {
			nouveauxPlusCourtsChemins(adresseLivraisonPrec, adresseLivraison);
		}
		if(plusCourtsChemins.get(adresseLivraison.getId()) == null || plusCourtsChemins.get(adresseLivraison.getId()).get(arrivee.getId()) == null) {
			nouveauxPlusCourtsChemins(adresseLivraison, arrivee);
		}
		return indiceEtapePrec;
	}

	/**
	 * chercheAdresse() cherche des adresses dans un disque de centre (x0,y0) et de rayon rayon et retourne
	 * la distance l'adresse la plus proche du centre ou null si aucune adresse ne se trouve dans ce disque
	 * @param x0
	 * @param y0
	 * @param rayon
	 * @return
	 */
	public Adresse chercheAdresse(int x0, int y0, int rayon) {
		Iterator<Adresse> itA = this.adresses.iterator();
		Adresse adresseCourante;
		int x, y;
		double dist;
		Hashtable<Double,Adresse> adressesTrouvees = new Hashtable<Double, Adresse>();
		while(itA.hasNext()){
			adresseCourante = itA.next();
			x = adresseCourante.getCoordX();
			y = adresseCourante.getCoordY();
			dist = Math.sqrt( ((x0-x)*(x0-x)+(y0-y)*(y0-y)) );
			adressesTrouvees.put(dist, adresseCourante);
		}
		
		Enumeration<Double> listeDistances = adressesTrouvees.keys();
		Double minDist = Double.MAX_VALUE;
		while(listeDistances.hasMoreElements()) {
			double distanceSuivante = listeDistances.nextElement();
			minDist = distanceSuivante<minDist ? distanceSuivante : minDist;
		}
		
		if(minDist<=rayon) return adressesTrouvees.get(minDist);
		
		return null;
	}
	
	/**
	 * cherche() cherche une adresse ou une livraison du plan dans un disque de centre (x0,y0) et de rayon rayon
	 *  et la retourne, null si rien.
	 * @param x0
	 * @param y0
	 * @param rayon
	 * @return Livraison, Adresse or null
	 */
	public Object cherche(int x0, int y0, int rayon) {
		Livraison livraisonTrouvee = this.demandeLivraisons.chercheLivraison(x0, y0, rayon);
		
		if(livraisonTrouvee != null) return livraisonTrouvee;
		
		Adresse adresseTrouvee = this.chercheAdresse(x0, y0, rayon);
		
		// adresseTrouvee est null si aucune adresse ne correspond 
		return adresseTrouvee;
	}

	@Override
	public String toString() {
		String str = "Plan : "+this.nom;
		str += "\nListe adresses : ";
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		while(adressesIterator.hasNext()) {
			Adresse adresseCourante = (Adresse) adressesIterator.next();
			str += "\n   ";
			str += adresseCourante.toString();
		}
		return str;
	}

}

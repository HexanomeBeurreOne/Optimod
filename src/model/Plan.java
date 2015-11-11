/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import model.tsp.TSP1;

/**
 * @author Adrien Menella
 *
 */
public class Plan extends Observable {

	/**
	 * Attributes
	 */
	private String nom;
	private List<Adresse> adresses;
	private List<Troncon> troncons;
	private DemandeLivraisons demandeLivraisons;
	// Hashmap better than Hashtable in single threaded environment.
	// The Key of the outer Hashtable is the id of the starting Adresse, 
	// The Key of the inner Hashtable is the id of the ending Adresse, 
	private Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins;
	private Tournee tournee;
	
	/**
	 * Constructor
	 */
	public Plan() {
		this.nom = "";
		this.adresses = new ArrayList<Adresse>();
		this.troncons = new ArrayList<Troncon>();
		this.demandeLivraisons = new DemandeLivraisons();
		this.plusCourtsChemins = new Hashtable<Integer,Hashtable<Integer,Chemin>>();
		this.tournee = new Tournee();
	}
	
	public DemandeLivraisons getDemandeLivraisons() {
		return demandeLivraisons;
	}

	public void setDemandeLivraisons(DemandeLivraisons demandeLivraisons) {
		this.demandeLivraisons = demandeLivraisons;
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Add a new Livraison to the specified fenetreLivraison passed in parameters
	 * @param newLivraison
	 * @param fenetreLivraison
	 */
	public void addLivraison(Livraison newLivraison, FenetreLivraison fenetreLivraison) {
		List<FenetreLivraison> fenetresLivraisonsList = this.demandeLivraisons.getFenetresLivraisons();
		if (fenetresLivraisonsList.contains(fenetreLivraison)) {
			FenetreLivraison fenetreLivraisonFounded = fenetresLivraisonsList.get(fenetresLivraisonsList.indexOf(fenetreLivraison));
			fenetreLivraisonFounded.addLivraison(newLivraison);
		}
	}
	
	/**
	 * Remove a Livraison from the specified fenetreLivraison passed in parameters
	 * @param oldLivraison
	 * @param fenetreLivraison
	 */
	public void removeLivraison(Livraison oldLivraison, FenetreLivraison fenetreLivraison) {
		List<FenetreLivraison> fenetresLivraisonsList = this.demandeLivraisons.getFenetresLivraisons();
		if (fenetresLivraisonsList.contains(fenetreLivraison)) {
			FenetreLivraison fenetreLivraisonFounded = fenetresLivraisonsList.get(fenetresLivraisonsList.indexOf(fenetreLivraison));
			fenetreLivraisonFounded.removeLivraison(oldLivraison);
		}
	}
	
	/**
	 * add a new FenetreLivraison to the list fenetresLivraisons
	 * @param newFenetreLivraison
	 */
	public void addFenetreLivraison(FenetreLivraison newFenetreLivraison) {
		this.demandeLivraisons.getFenetresLivraisons().add(newFenetreLivraison);
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
	 * Add an Adresse to the ArrayList adresses
	 * @param newAdresse
	 */
	public void addAdresse(Adresse newAdresse) {
		this.adresses.add(newAdresse);
	}
	
	/**
	 * Remove an Adresse to the ArrayList adresses
	 * @param adresseToRemove
	 */
	public void removeAdresse(Adresse adresseToRemove) {
		if(this.adresses.contains(adresseToRemove)) this.adresses.remove(adresseToRemove);
	}
	
	 /**
	  * Add a Troncon to the ArrayList troncons
	  * @param newTroncon
	  */
	public void addTroncon(Troncon newTroncon) {
		this.troncons.add(newTroncon);
	}

	
	public Tournee getTournee() {
		return tournee;
	}
	
	public void setTournee(Tournee tournee) {
		this.tournee = tournee;
		setChanged();
		notifyObservers();
	}
	
	public void calculTournee()	{
		calculPlusCourtsChemins();

		Integer[] ordreLivraisons = calculOrdreLivraisons();
		tournee = new Tournee(demandeLivraisons, ordreLivraisons, plusCourtsChemins);
		this.setTournee(tournee);
		//System.out.println(tournee);
	}
	
	private Integer[] calculOrdreLivraisons() {
		TSP tsp = new TSP1();
		Graphe g = new GrapheOptimod(demandeLivraisons, plusCourtsChemins);
		long tempsDebut = System.currentTimeMillis();
		tsp.chercheSolution(60000, g);
		//System.out.print("Solution de longueur "+tsp.getCoutSolution()+" trouvee en "
				//+(System.currentTimeMillis() - tempsDebut)+"ms : ");
		Integer[] solution = tsp.getSolution();
		for (Integer i : solution){
			//System.out.print(i + " ");
		}
		//System.out.println();
		return tsp.getSolution();
	}

	/**
	 * Calculate the shortest paths between the delivery points
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
		//TODO : put it in dispatcher #multithread
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
				//On retire des ciblesDijkstra le departDijkstra
				ciblesDijkstra.remove(departDijkstra);
				Hashtable<Integer, Chemin> resDijkstra = dijkstra(departDijkstra, ciblesDijkstra);
				plusCourtsChemins.put(departId, resDijkstra);
			}
		}
		//System.out.println(plusCourtsChemins);
	}
	
	private HashSet<Adresse> settledNodes;
	private HashSet<Adresse> unSettledNodes;
	private HashMap<Adresse, Double> distance;
	private HashMap<Adresse, Adresse> predecessors;
	
	public Hashtable<Integer, Chemin> dijkstra(Adresse depart, List<Adresse> cibles) {
		settledNodes = new HashSet<Adresse>();
		unSettledNodes = new HashSet<Adresse>();
		distance = new HashMap<Adresse, Double>();
		predecessors = new HashMap<Adresse, Adresse>();
	    distance.put(depart, 0.);
	    unSettledNodes.add(depart);
	    while (unSettledNodes.size() > 0) {
	      Adresse node = getMinimum(unSettledNodes);
	      settledNodes.add(node);
	      unSettledNodes.remove(node);
	      findMinimalDistances(node);
	    }
	    
	    Hashtable<Integer, Chemin> result = new Hashtable<Integer, Chemin>();
	    if(settledNodes.containsAll(cibles))	{
		    for(int i=0; i < cibles.size(); i++)	{
		    	Adresse precede = cibles.get(i);
		    	Integer id = precede.getId();
		    	Chemin chemin = new Chemin();
		    	chemin.setTempsDeParcours(distance.get(cibles.get(i)));
		    	//Iterate to get each troncon from cibles[i] to depart
		    	while(precede.getId() != depart.getId())	{
		    		Adresse adressePrec = predecessors.get(precede);
		    		List<Troncon> tronconsSortants = adressePrec.getTroncons();
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
	
	private void findMinimalDistances(Adresse node) {
	    List<Adresse> adjacentNodes = getNeighbors(node);
	    for (Adresse target : adjacentNodes) {
	      if (getShortestTime(target) > getShortestTime(node)
	          + getTime(node, target)) {
	        distance.put(target, getShortestTime(node)
	            + getTime(node, target));
	        predecessors.put(target, node);
	        unSettledNodes.add(target);
	      }
	    }

	  }

	  private double getTime(Adresse node, Adresse target) {
	    for (Troncon edge : troncons) {
	      if (edge.getOrigine().equals(node)
	          && edge.getDestination().equals(target)) {
	        return edge.getTempsTroncon();
	      }
	    }
	    throw new RuntimeException("Should not happen");
	  }
	  
	  private List<Adresse> getNeighbors(Adresse node) {
		    List<Adresse> neighbors = new ArrayList<Adresse>();
		    for (Troncon edge : troncons) {
		      if (edge.getOrigine().equals(node)
		          && !isSettled(edge.getDestination())) {
		        neighbors.add(edge.getDestination());
		      }
		    }
		    return neighbors;
		  }
	  
	  private boolean isSettled(Adresse node) {
		    return settledNodes.contains(node);
	  }
	  
	  private Adresse getMinimum(Set<Adresse> adresseSet) {
	    Adresse minimum = null;
	    for (Adresse ad : adresseSet) {
	      if (minimum == null) {
	        minimum = ad;
	      } else {
	        if (getShortestTime(ad) < getShortestTime(minimum)) {
	          minimum = ad;
	        }
	      }
	    }
	    return minimum;
	  }

	  private Double getShortestTime(Adresse destination) {
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
	 * Verifie que la livraison existe, puis calcul, si besoin, le plus court chemin dont on aura besoin pour corriger la tournee apres suppression
	 * @param Livraison a supprimer
	 * @return Indice de l'etape a supprimer, -1 si la livraison ne fais pas partie de la tournee
	 */
	public int testSuppression(Livraison livraison){
		
		// TODO : On n'utilise pas la demande de livraisons ici que la tournee 
		
		int indiceEtape = tournee.findIndiceEtape(livraison);
		if(indiceEtape != -1) {
			if(tournee.getEtapes().size() == 1)	{
				// On souhaite supprimer l'unique livraison de la tournee
				return indiceEtape;
			}
			Adresse depart;
			Adresse arrivee;
			if(indiceEtape == tournee.getEtapes().size()-1) {
				// On souhaite supprimer la derniere etape, le chemin retourEntrepot va etre mis a jour
				depart = tournee.getEtapes().get(indiceEtape-1).getLivraison().getAdresse();
				arrivee = demandeLivraisons.getEntrepot();
			}
			else {
				if(indiceEtape == 0){
					// On souhaite supprimer la premiere etape, on met a jour le chemin de la nouvelle premiere etape en partant de l'entrepot
					depart = demandeLivraisons.getEntrepot();
				}
				else {
					depart = tournee.getEtapes().get(indiceEtape-1).getLivraison().getAdresse();
				}
				arrivee = tournee.getEtapes().get(indiceEtape+1).getLivraison().getAdresse();
			}
			// Calcul 
			if(plusCourtsChemins.get(depart.getId()).get(arrivee.getId()) == null) {
				List<Adresse> cibles = new ArrayList<Adresse>();
				if(arrivee == demandeLivraisons.getEntrepot()) {
					cibles.add(arrivee);
				}
				else {
					for(Livraison liv : demandeLivraisons.getLivraison(arrivee).getFenetreLivraison().getLivraisons()) {
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
					plusCourtsChemins.get(depart.getId()).put(idAdresseCible, dureePlusCourtChemin);
				}
			}
		}
		return indiceEtape;
	}	
	
	public void supprimerLivraison(Livraison livraison) {
		int indiceEtape = testSuppression(livraison);
		if(indiceEtape != -1) {
			tournee.supprimerEtape(indiceEtape, plusCourtsChemins);
		}
		else {
			System.out.println("La livraison ne fait pas partie de la tournee.");
		}
	}
	
	public void affichePlan() {
		System.out.println("Plan : "+this.nom);
		System.out.println("Liste adresses : ");
		Iterator<Adresse> adressesIterator = this.adresses.iterator();
		while(adressesIterator.hasNext()) {
			Adresse currentAdresse = (Adresse) adressesIterator.next();
			System.out.print("   ");
			currentAdresse.afficheAdresse();
		}
	}
	
	public Adresse chercheAdresse(int x0, int y0) {
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
		double minDist = 9999;
		while(listeDistances.hasMoreElements()) {
			double nextDist = listeDistances.nextElement();
			minDist = nextDist<minDist ? nextDist : minDist;
		}
		
		if(minDist<=10) return adressesTrouvees.get(minDist);
		
		return null;
	}
	
	public Object cherche(int x0, int y0) {
		Livraison livraisonTrouvee = this.demandeLivraisons.chercheLivraison(x0, y0);
		Adresse adresseTrouvee = this.chercheAdresse(x0, y0);
		
		if(livraisonTrouvee != null) return livraisonTrouvee;
		
		// adresseTrouvee est null si aucune adresse ne correspond 
		return adresseTrouvee;
	}


}

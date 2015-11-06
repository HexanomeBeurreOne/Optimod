/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Adrien Menella
 *
 */
public class Plan {

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
	private Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemin;
	
	/**
	 * Constructor
	 */
	public Plan() {
		this.nom = "";
		this.adresses = new ArrayList<Adresse>();
		this.troncons = new ArrayList<Troncon>();
		this.demandeLivraisons = new DemandeLivraisons();
	}
	
	public DemandeLivraisons getDemandeLivraisons() {
		return demandeLivraisons;
	}

	public void setDemandeLivraisons(DemandeLivraisons demandeLivraisons) {
		this.demandeLivraisons = demandeLivraisons;
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
	}

	public List<Adresse> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Adresse> adresses) {
		this.adresses = adresses;
	}

	public List<Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}

	/**
	 * Add an Adresse to the ArrayList adresses
	 * @param newAdresse
	 */
	public void addAdresse(Adresse newAdresse) {
		this.adresses.add(newAdresse);
	}
	 /**
	  * Add a Troncon to the ArrayList troncons
	  * @param newTroncon
	  */
	public void addTroncon(Troncon newTroncon) {
		this.troncons.add(newTroncon);
	}
	
	public void calculTournee()	{
		calculPlusCourtsChemins();
		//Create graph with TSP
	}
	
	/**
	 * Calculate the shortest paths between the delivery points
	 */
	private void calculPlusCourtsChemins()	{
		//The list is ordered
		List<FenetreLivraison> fenetres = demandeLivraisons.getFenetresLivraisons();
		
		//Get entrepot
		Adresse entrepot = getAdresseById(demandeLivraisons.getIdEntrepot());
		//Liste contenant l'entrepot, qui est le d�part et l'arriv�e
		List<Adresse> entrepotList = new ArrayList<Adresse>(); 
		entrepotList.add(entrepot);
		///Liste des listes des Adresses de livraison de la fenetre
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
		
		//TODO : put it in dispatcher #multithread
		for(int i=1; i < adressesFenList.size(); i++)
		{
			for(int j=0; j< adressesFenList.get(i-1).size(); j++)
			{
				List<Adresse> cibles = new ArrayList<Adresse>(adressesFenList.get(i));
				cibles.addAll(adressesFenList.get(i-1));
				Adresse depart = adressesFenList.get(i-1).get(j);
				cibles.remove(depart);
				plusCourtsChemin.put(depart.getId(), dijkstra(depart, cibles));
			}
		}	
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
		    System.out.println(result);
		    
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
	 * Get the Adresse which id is corresponding, return null if it does not contain
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

}

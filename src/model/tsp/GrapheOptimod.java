package model.tsp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import model.Adresse;
import model.Chemin;
import model.DemandeLivraisons;
import model.Livraison;

public class GrapheOptimod implements Graphe {
	
	int nbSommets;
	//private LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> cout;
	int[][] cout;
	
	/**
	 * 
	 * @param nbSommets
	 */
	public GrapheOptimod(Integer idEntrepot, DemandeLivraisons demandeLivraison, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins){
		this.nbSommets = demandeLivraison.getAllLivraisons().size() + 2;
		this.cout = new int[nbSommets][nbSommets];
		List<Integer> idAdressesLivraisons = new ArrayList<Integer>();
		for(Livraison liv : demandeLivraison.getAllLivraisons()){
			idAdressesLivraisons.add(liv.getAdresse().getId());
		}
		
		Set<Integer> keySet = plusCourtsChemins.keySet();
		Iterator<Integer> keySetIterator = keySet.iterator();
		Integer key;
		while ( keySetIterator.hasNext() ) {
		   key = keySetIterator.next();
		   Set<Integer> innerKeySet = plusCourtsChemins.get(key).keySet();
		   Iterator<Integer> innerKeySetIterator = innerKeySet.iterator();
		   Integer innerKey;
		   while ( innerKeySetIterator.hasNext()) {
			   innerKey = keySetIterator.next();
			   if(key == idEntrepot){
				   //Ajout sur cout[0][]
			   }
			   if(innerKey == idEntrepot){
				   //Ajout sur cout[][nbSommets - 1]
			   }
		   }
		}

		
		/*
		this.cout = new LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>>();
		for (int idDepart : plusCourtsChemins.keySet()) {
			Hashtable<Integer,Chemin> innerHashTable = plusCourtsChemins.get(idDepart);
			LinkedHashMap<Integer, Integer> innerHashMap = new LinkedHashMap<Integer, Integer>();
			for (int idArrivee : innerHashTable.keySet()) {
				innerHashMap.put(idArrivee, innerHashTable.get(idArrivee).getTempsDeParcours().intValue());
			}
			cout.put(idDepart, innerHashMap);
		}*/

	}

	@Override
	public int getNbSommets() {
		return nbSommets;
	}

	@Override
	public int getCout(int i, int j) {
		if (i<0 || i>=nbSommets || j<0 || j>=nbSommets)
			return -1;
		/*
		Set<Integer> keySet = cout.keySet();
		Iterator<Integer> keySetIterator = keySet.iterator();
		Integer key = 0;
		for (int a = 0; keySetIterator.hasNext() && a < i; a++) {
		   key = keySetIterator.next();
		}
		Set<Integer> innerKeySet = cout.get(key).keySet();
		Iterator<Integer> innerKeySetIterator = innerKeySet.iterator();
		Integer innerKey = 0;
		for (int b = 0; innerKeySetIterator.hasNext() && b < j; b++) {
		    innerKey = keySetIterator.next();
		}*/
		return cout[i][j];
	}

	@Override
	public boolean estArc(int i, int j) {
		if (i<0 || i>=nbSommets || j<0 || j>=nbSommets)
			return false;
		return i != j;
	}

}

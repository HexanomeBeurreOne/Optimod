package model.tsp;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import model.Chemin;

public class GrapheOptimod implements Graphe {
	
	int nbSommets;
	int[][] cout;
	
	/**
	 * Cree un graphe complet dont les aretes ont un cout compris entre COUT_MIN et COUT_MAX
	 * @param nbSommets
	 */
	public GrapheOptimod(Hashtable<Integer,Hashtable<Integer,Chemin>> hashTableGraph, Integer nombreAdresses){
		this.nbSommets = nombreAdresses;
		this.cout = new int[nbSommets][nbSommets];
		// Get a set of all the entries (key - value pairs) contained in the Hashtable
		Set<Entry<Integer, Hashtable<Integer, Chemin>>> entrySet = hashTableGraph.entrySet();
		// Obtain an Iterator for the entries Set
		Iterator<Entry<Integer, Hashtable<Integer, Chemin>>> itDeparts = entrySet.iterator();
		// Iterate through Hashtable entries
		while(itDeparts.hasNext()){
			//itDeparts.next().getValue();
		}

	}

	@Override
	public int getNbSommets() {
		return nbSommets;
	}

	@Override
	public int getCout(int i, int j) {
		if (i<0 || i>=nbSommets || j<0 || j>=nbSommets)
			return -1;
		return cout[i][j];
	}

	@Override
	public boolean estArc(int i, int j) {
		if (i<0 || i>=nbSommets || j<0 || j>=nbSommets)
			return false;
		return i != j;
	}

}

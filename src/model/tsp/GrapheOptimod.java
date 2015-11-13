package model.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Chemin;
import model.DemandeLivraisons;
import model.Livraison;

public class GrapheOptimod implements Graphe {
	
	int nbSommets;
	int[][] cout;
	
	/**
	 * 
	 * @param demandeLivraison
	 * @param plusCourtsChemins
	 */
	public GrapheOptimod(DemandeLivraisons demandeLivraison, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins){
		int idEntrepot = demandeLivraison.getEntrepot().getId();
		List<Integer> idAdressesLivraisons = new ArrayList<Integer>();
		for(Livraison liv : demandeLivraison.getAllLivraisons())
		{
			idAdressesLivraisons.add(liv.getAdresse().getId());
		}
		this.nbSommets = idAdressesLivraisons.size() + 1;
		this.cout = new int[nbSommets][nbSommets];
		for(int i = 0; i < cout.length; i++)
		{
			Arrays.fill(this.cout[i], -1);
		}
		Set<Integer> keySet = plusCourtsChemins.keySet();
		Iterator<Integer> keySetIterator = keySet.iterator();
		Integer key;
		while ( keySetIterator.hasNext() ) 
		{
		   key = keySetIterator.next();
		   Set<Integer> innerKeySet = plusCourtsChemins.get(key).keySet();
		   Iterator<Integer> innerKeySetIterator = innerKeySet.iterator();
		   Integer innerKey;
		   while ( innerKeySetIterator.hasNext()) 
		   {
			   innerKey = innerKeySetIterator.next();
			   int indiceDepart, indiceArrivee;
			   indiceDepart = idAdressesLivraisons.indexOf(key)+1;
			   indiceArrivee = idAdressesLivraisons.indexOf(innerKey)+1;
			   if(key == idEntrepot)
			   {
				   indiceDepart = 0;
			   }
			   if(innerKey == idEntrepot)
			   {
				   indiceArrivee = 0;
			   }
			   this.cout[indiceDepart][indiceArrivee] = plusCourtsChemins.get(key).get(innerKey).getTempsDeParcours().intValue();
		   }
		}
		displayCout();
	}

	private void displayCout() {
		for(int i = 0; i < cout.length; i++)
		{
			for(int j = 0; j < cout[i].length; j++)
			{
				System.out.print(this.cout[i][j]+" ");
			}
			System.out.println("");
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

		return cout[i][j] != -1;
	}

}

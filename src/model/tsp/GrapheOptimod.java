package model.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Chemin;
import model.DemandeLivraisons;
import model.FenetreLivraison;
import model.Livraison;

public class GrapheOptimod implements Graphe {
	
	int nbSommets;
	int[][] cout;
	
	/**
	 * 
	 * @param 
	 */
	public GrapheOptimod(DemandeLivraisons demandeLivraison, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins){
		int idEntrepot = demandeLivraison.getEntrepot().getId();
		List<Integer> idsAdressesLivraisons = new ArrayList<Integer>();
		for(Livraison livraison : demandeLivraison.getAllLivraisons()){
			idsAdressesLivraisons.add(livraison.getAdresse().getId());
		}
		this.nbSommets = idsAdressesLivraisons.size() + 1;
		this.cout = new int[nbSommets][nbSommets];
		for(int i = 0; i < cout.length; i++)
		{
			Arrays.fill(this.cout[i], -1);
		}
		// TODO : Gerer le cas d'une demande sans livraison ?
		int indiceDepart = 0;
		int idDepart = idEntrepot;
		List<FenetreLivraison> fenetresLiv = demandeLivraison.getFenetresLivraisons();
		// On met a jour le cout entre l'entrepot et les livraisons de la premiere fenetre
		
		// Pour chaque fenetre, sauf la derniere, on met a jour le cout entre chaque livraison et celles de la meme fenetre et de la suivante
		for(int i = 0 ; i < fenetresLiv.size() - 1; i++)
		{
			List<Livraison> livraisonsFen = fenetresLiv.get(i).getLivraisons();
			for(int j = 0 ; j < livraisonsFen.size() ; j++)
			{
				idsAdressesLivraisons.indexOf(o)
				int idLiv = livraisonsFen.get(j).getAdresse().getId();
				this.cout[indiceDepart][indiceArrivee] = plusCourtsChemins.get(key).get(innerKey).getTempsDeParcours().intValue();
			}
			
		}
		// On met a jour le cout entre les livraisons de la derniere fenetre et l'entrepot
		
		/*
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
		}*/
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

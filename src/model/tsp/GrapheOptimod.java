package model.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import model.Adresse;
import model.Chemin;
import model.DemandeLivraisons;
import model.FenetreLivraison;
import model.Livraison;

public class GrapheOptimod implements Graphe {
	
	private int nbSommets;
	private int[][] cout;
	private Adresse[] adresses;
	private double vitesseMaximum;
	
	/**
	 * 
	 * @param demandeLivraison
	 * @param plusCourtsChemins
	 */
	public GrapheOptimod(DemandeLivraisons demandeLivraison, Hashtable<Integer,Hashtable<Integer,Chemin>> plusCourtsChemins, double vitesseMaximum){
		Adresse entrepot = demandeLivraison.getEntrepot();
		List<Livraison> livraisons = demandeLivraison.getAllLivraisons();
		this.nbSommets = livraisons.size() + 1;
		this.cout = new int[nbSommets][nbSommets];
		this.adresses = new Adresse[nbSommets];
		this.adresses[0] = entrepot;
		List<Integer> idAdressesLivraisons = new ArrayList<Integer>();
		for(int i = 0 ; i < livraisons.size() ; i++)
		{
			Livraison liv = livraisons.get(i);
			// On recupere les ids des adresses des livraisons
			idAdressesLivraisons.add(liv.getAdresse().getId());
			// On recupere les adresses des livraisons
			this.adresses[i+1] = liv.getAdresse();
		}		
		// Initialisation des couts a -1
		for(int i = 0; i < cout.length; i++)
		{
			Arrays.fill(this.cout[i], -1);
		}
		
		int idAdresseDepart, idAdresseArrivee;
		int indiceDepart, indiceArrivee;
		List<FenetreLivraison> fenetres = demandeLivraison.getFenetresLivraisons();
		// Mise a jour des couts depuis l'entrepot
		indiceDepart = 0;
		idAdresseDepart = entrepot.getId();
		List<Livraison> livraisonsPremiereFenetre = fenetres.get(0).getLivraisons();
		for(int i = 0 ; i < livraisonsPremiereFenetre.size() ; i++)
		{
			for(Livraison liv : livraisonsPremiereFenetre){
				idAdresseArrivee = liv.getAdresse().getId();
				indiceArrivee = idAdressesLivraisons.indexOf(liv.getAdresse().getId())+1;
				this.cout[indiceDepart][indiceArrivee] = plusCourtsChemins.get(idAdresseDepart).get(idAdresseArrivee).getTempsDeParcours().intValue();
			}

		}
		// Mise a jour des couts depuis chaque fenetres;
		for(int i = 0 ; i < fenetres.size() ; i++)
		{
			for(int j = 0 ; j < fenetres.get(i).getLivraisons().size() ; j++)
			{
				idAdresseDepart = fenetres.get(i).getLivraisons().get(j).getAdresse().getId();
				indiceDepart = idAdressesLivraisons.indexOf(idAdresseDepart)+1;
				// Pour la fenetre actuelle et la suivante (sauf si l'actuelle est la derniere) on met a jour les couts
				for(int k = i ; k <= i+1 ; k++) 
				{
					for(int l = 0 ; l < ((k == fenetres.size()) ? 1 : fenetres.get(k).getLivraisons().size()) ; l++) 
					{
						// On ne met pas a jour d'une adresse a elle meme
						if(k != i || j != l) {
							if(k == fenetres.size()) {
								// Mise a jour du cout jusqu'a l'entrepot
								indiceArrivee = 0;
								idAdresseArrivee = entrepot.getId();
							}
							else {
								idAdresseArrivee = fenetres.get(k).getLivraisons().get(l).getAdresse().getId();
								indiceArrivee = idAdressesLivraisons.indexOf(idAdresseArrivee)+1;
							}
							this.cout[indiceDepart][indiceArrivee] = plusCourtsChemins.get(idAdresseDepart).get(idAdresseArrivee).getTempsDeParcours().intValue();
						}
					}
				}
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
	
	public Adresse[] getAdresses() {
		return adresses;
	}
}

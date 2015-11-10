package model.tsp;

import java.util.List;

public interface TSP {
	
	/**
	 * Cherche une solution au TSP pour le graphe <code>g</code> dans la limite de <code>tpsLimite</code> millisecondes
	 * Attention : la solution calculee commence necessairement par le sommet 0
	 * @param tpsLimite
	 * @param g
	 */
	public void chercheSolution(int tpsLimite, Graphe g);
	
	/**
	 * @param i
<<<<<<< HEAD
	 * @return l'ordre des sommets visites dans la solution calculee par <code>chercheSolution</code> 
	 * (null si <code>chercheSolution</code> n'a pas encore ete appele)
=======
	 * @return la liste ordonee des indices des sommets visites dans la solution calculee par <code>chercheSolution</code> 
	 * (-1 si <code>chercheSolution</code> n'a pas encore ete appele, ou si i < 0 ou i >= g.getNbSommets())
>>>>>>> tmp
	 */
	public Integer[] getSolution();
	
	/**
	 * @param i
	 * @return le ieme sommet visite dans la solution calculee par <code>chercheSolution</code> 
	 * (-1 si <code>chercheSolution</code> n'a pas encore ete appele, ou si i < 0 ou i >= g.getNbSommets())
	 */
	public Integer getSolution(int i);
	
	/** 
	 * @return la somme des couts des arcs de la solution calculee par <code>chercheSolution</code> 
	 * (-1 si <code>chercheSolution</code> n'a pas encore ete appele).
	 */
	public int getCoutSolution();
}

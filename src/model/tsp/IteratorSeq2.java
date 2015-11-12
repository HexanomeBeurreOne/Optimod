package model.tsp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class IteratorSeq2 implements Iterator<Integer> {

	private Integer[] candidats;
	private int indice;

	/**
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus qui sont successeurs de sommetCrt dans le graphe g,
	 * dans l'odre d'apparition dans <code>nonVus</code>
	 * @param nonVus
	 * @param sommetCrt
	 * @param g
	 */
	public IteratorSeq2(Collection<Integer> nonVus, int sommetCrt, Graphe g){
		TreeMap<Integer, Integer> treeMapCandidats = new TreeMap<Integer, Integer>();
		Iterator<Integer> it = nonVus.iterator();
		while (it.hasNext()){
			Integer s = it.next();
			if (g.estArc(sommetCrt, s))
				treeMapCandidats.put(g.getCout(sommetCrt, s), s);
		}
		Collection<Integer> collectionCandidats = treeMapCandidats.values();
		this.candidats = collectionCandidats.toArray(new Integer[collectionCandidats.size()]);
	}
	
	@Override
	public boolean hasNext() {
		return indice < candidats.length;
	}

	@Override
	public Integer next() {
		return candidats[indice++];
	}

	@Override
	public void remove() {}
}

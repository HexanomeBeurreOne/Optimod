package model.tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP2 extends TSP1 {

	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, Collection<Integer> nonVus, Graphe g) {
		return new IteratorMinFirst(nonVus, sommetCrt, g);
	}

}

package model.tsp;

import java.util.Collection;
import java.util.Iterator;

public class TSP2 extends TemplateTSP {

	@Override
	protected int bound(Integer sommetCourant, Collection<Integer> nonVus) {
		return 0;
	}

	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, Collection<Integer> nonVus, Graphe g) {
		return new IteratorMinFirst(nonVus, sommetCrt, g);
	}

}

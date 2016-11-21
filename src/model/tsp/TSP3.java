package model.tsp;

import java.util.Collection;
import java.util.Iterator;

import model.Adresse;

public class TSP3 extends TSP2 {

	@Override
	protected int bound(Integer sommetCourant, Collection<Integer> nonVus) {
		Adresse[] adresses = ((GrapheOptimod) g).getAdresses();
		Adresse adresseSommetCourant = adresses[sommetCourant];
		return (int) adresseSommetCourant.tempsMinimum(adresses[0], 1);
	}

}

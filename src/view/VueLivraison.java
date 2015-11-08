package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.FenetreLivraison;
import model.Livraison;
import model.Plan;

public class VueLivraison extends JPanel implements Observer {

	private Plan plan;
	private ArrayList<JButton> livraisons;
	
	public VueLivraison(Plan plan, Fenetre fenetre) {
		super();
		
		this.plan = plan;
		
		plan.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.plan = (Plan)arg;
		plan.getDemandeLivraisons().afficheDemandeLivraisons();
		//miseAJourLivraisons();
	}
}

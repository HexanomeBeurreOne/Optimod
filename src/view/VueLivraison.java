package view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import model.FenetreLivraison;
import model.Livraison;
import model.Plan;

public class VueLivraison extends JPanel implements Observer {

	private Plan plan;
	private JList<String> livraisons;
	
	public VueLivraison(Plan plan, Fenetre fenetre) {
		super();
		this.plan = plan;
		
		this.setBackground(Color.WHITE);
		//Couleur des bordures
        this.setBorder(new CompoundBorder(
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY), 
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY)));
		
		String[] tab = {"one", "two", "three"};
		this.livraisons = new JList(tab);
		
		this.add(livraisons);
		fenetre.getContentPane().add(this);
		plan.addObserver(this);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		this.plan = (Plan)arg;
		//plan.getDemandeLivraisons().afficheDemandeLivraisons();
		//miseAJourLivraisons();
	}
}

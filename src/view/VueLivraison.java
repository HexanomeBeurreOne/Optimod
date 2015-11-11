package view;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import model.FenetreLivraison;
import model.Livraison;
import model.Plan;

public class VueLivraison extends JPanel implements Observer {

	private Plan plan;
	private JList<Livraison> livraisons;
	
	public VueLivraison(Plan plan, Fenetre fenetre) {
		super();
		this.plan = plan;
		
		this.setBackground(Color.WHITE);
		//Couleur des bordures
        this.setBorder(new CompoundBorder(
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY), 
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY)));
		
		this.livraisons = new JList();
		this.add(livraisons);
		fenetre.getContentPane().add(this);
		plan.addObserver(this);
	}
	
	private void afficherLivraisons() {
		FenetreLivraison fenetreLivraisonCourante;
		Iterator<FenetreLivraison> itFL = plan.getDemandeLivraisons().getFenetresLivraisons().iterator();
		List<Livraison> livraisons;
		Iterator<Livraison> itL;
		DefaultListModel model = new DefaultListModel();
		
		while (itFL.hasNext()) {
			fenetreLivraisonCourante = itFL.next();
			livraisons = fenetreLivraisonCourante.getLivraisons();
			itL = livraisons.iterator();
			
			while (itL.hasNext()) {
				Livraison livraisonActuelle = itL.next();
				model.addElement(livraisonActuelle.getClient());
			}
		}
		this.livraisons.setModel(model);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg != null) {
			
		}
		afficherLivraisons();
	}
}

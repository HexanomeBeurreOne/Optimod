package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import controller.ControleurApplication;

import model.Adresse;
import model.FactoryPlan;

public class EcouteurSouris extends MouseAdapter {

//------------------------------------------------- ATTRIBUTES	

	private ControleurApplication controleur;
	private VuePlan vueGraphique;
	private Fenetre fenetre;

//------------------------------------------------- CONSTRUCTORS

	public EcouteurSouris(ControleurApplication controleur, VuePlan vueGraphique, Fenetre fenetre){
		this.controleur = controleur;
		this.vueGraphique = vueGraphique;
		this.fenetre = fenetre;
	}

//------------------------------------------------- METHODS
	@Override
	public void mouseClicked(MouseEvent evt) {
		int x=evt.getX();
	    int y=evt.getY();
	    System.out.println(x+","+y);//these co-ords are relative to the component
	}

	public void mouseMoved(MouseEvent evt) {
		// Methode appelee a chaque fois que la souris est bougee
		// Envoie au controleur les coordonnees de la souris.
		// Point p = coordonnees(evt);
		//if (p != null)
			// controleur.sourisBougee(p); 
	}
	/*
	private Adresse coordonnees(MouseEvent evt){
		MouseEvent e = SwingUtilities.convertMouseEvent(fenetre, evt, vueGraphique);
		int x = Math.round((float)e.getX()/(float)vueGraphique.getEchelle());
		int y = Math.round((float)e.getY()/(float)vueGraphique.getEchelle());
		return FactoryPlan.creePoint(x, y);
	}*/


}
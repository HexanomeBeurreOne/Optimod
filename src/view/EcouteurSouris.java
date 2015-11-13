package view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.ControleurApplication;

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
		System.out.println("mouseclicked");
		int x=(int) Math.round(evt.getX()/controleur.getEchelle());
	    int y=(int) Math.round(evt.getY()/controleur.getEchelle());
	    
	    controleur.getObjetSelectionne(x, y);
	}
}

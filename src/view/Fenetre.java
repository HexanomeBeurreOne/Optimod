package view;

import model.*;

import javax.swing.*;

import controller.ControlleurApplication;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by cyrilcanete on 03/11/15.
 */
public class Fenetre extends JFrame{
	
	private EcouteurBoutons ecouteurDeBoutons;
	protected final static String CHARGER_PLAN = "Charger un plan";
	protected static final String CHARGER_LIVRAISONS = "Charger une demande de livraisons";
	protected static final String CALCULER_TOURNEE = "Calculer une tournée";
	private final int hauteurBouton = 40;
	private final int largeurBouton = 150;
	private final int hauteurMessage = 50;
	private final int largeurMessage = 300;
	private final String[] intitulesBoutons = new String[]{CHARGER_PLAN, CHARGER_LIVRAISONS, 
			CALCULER_TOURNEE};
	private ArrayList<JButton> boutons;
	
	private JLabel zoneMessage;
	
	private int hauteurFenetre;
	private int largeurFenetre;
	private int largeurVuePlan;
	private int hauteurVuePlan;

    public Fenetre(Plan plan, double echelle, ControlleurApplication controller) throws HeadlessException {

        //Définit un titre pour notre fenêtre
        this.setTitle("Optimod");
        
        //Définit sa taille : plein ecran
        Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        hauteurFenetre = (int)tailleEcran.getHeight()-100;
        largeurFenetre = (int)tailleEcran.getWidth();
        this.setSize(largeurFenetre, hauteurFenetre);
        
        //Nous demandons maintenant à notre objet de se positionner au centre
        this.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.setLayout(null);
        creerBoutons(controller);

        //Instanciation d'un objet VuePlan
        VuePlan carte = new VuePlan(plan, echelle, this);
        carte.setLocation(0, hauteurBouton+hauteurMessage);
        hauteurVuePlan = hauteurFenetre-hauteurBouton-hauteurMessage-30;
        largeurVuePlan = 2*largeurFenetre/3;
        carte.setSize(largeurVuePlan, hauteurVuePlan);
        
        zoneMessage = new JLabel();
        zoneMessage.setBorder(BorderFactory.createTitledBorder("Messages..."));
		getContentPane().add(zoneMessage);
		zoneMessage.setSize(largeurVuePlan,hauteurMessage);
		zoneMessage.setLocation(0,hauteurBouton);

        //Et enfin, la rendre visible
        this.setVisible(true);
    }

	public void creerBoutons(ControlleurApplication controller) {
    	ecouteurDeBoutons = new EcouteurBoutons(controller);
		boutons = new ArrayList<JButton>();
		for (int i=0; i<intitulesBoutons.length; i++){
			JButton bouton = new JButton(intitulesBoutons[i]);
			boutons.add(bouton);
			
			bouton.setForeground(Color.DARK_GRAY);
			bouton.setSize(largeurBouton,hauteurBouton);
			bouton.setLocation((boutons.size()-1)*largeurBouton+5, 0);
			//bouton.setFocusable(false);
			//bouton.setFocusPainted(false);
			bouton.addActionListener(ecouteurDeBoutons);
			this.getContentPane().add(bouton);	
		}
    }

	public int getHauteurFenetre() {
		return hauteurFenetre;
	}

	public int getLargeurFenetre() {
		return largeurFenetre;
	}

    
}

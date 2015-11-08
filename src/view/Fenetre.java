package view;

import model.*;

import javax.swing.*;

import controller.ControleurApplication;

import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by cyrilcanete on 03/11/15.
 */
public class Fenetre extends JFrame{
	private final int hauteurFenetre = 1000;
	private final int largeurFenetre = 1900;
	
	private VuePlan vuePlan;
	private int largeurVuePlan;
	private int hauteurVuePlan;
	
	private VueLivraison vueLivraison;
	private int largeurVueLivraison;
	private int hauteurLivraison;
	
	private ArrayList<JButton> boutons;
	private final int hauteurBouton = 30;
	private final int largeurBouton = 150;
	protected final static String CHARGER_PLAN = "Charger plan";
	protected static final String CHARGER_LIVRAISONS = "Charger livraisons";
	protected static final String CALCULER_TOURNEE = "Calculer tournée";
	private final String[] intitulesBoutons = new String[]{CHARGER_PLAN, CHARGER_LIVRAISONS, CALCULER_TOURNEE};
	private EcouteurBoutons ecouteurDeBoutons;
	
	private EcouteurSouris ecouteurSouris;
	
	private JLabel zoneMessage;
	private final int hauteurMessage = 50;

    public Fenetre(Plan plan, double echelle, ControleurApplication controller) throws HeadlessException {

        //Fenetre
        this.setTitle("Optimod");
        this.setSize(largeurFenetre, hauteurFenetre);
        this.setResizable(false);
        //Position de la fenetre au centre de l'ecran
        this.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        
        //VuePlan
        vuePlan = new VuePlan(plan, echelle, this);
        vuePlan.setLocation(0, hauteurBouton+hauteurMessage);
        hauteurVuePlan = hauteurFenetre-hauteurBouton-hauteurMessage-22;
        largeurVuePlan = largeurFenetre/2;
        vuePlan.setSize(largeurVuePlan, hauteurVuePlan);
        
        //VueLivraison
        vueLivraison = new VueLivraison(plan, this);
        vueLivraison.setLocation(largeurVuePlan, hauteurBouton+hauteurMessage);
        
        //Boutons
        creerBoutons(controller);
        
        //ZoneMessage
        zoneMessage = new JLabel();
        zoneMessage.setBorder(BorderFactory.createTitledBorder("Message"));
        zoneMessage.setSize(largeurVuePlan,hauteurMessage);
		zoneMessage.setLocation(0,hauteurBouton);
		zoneMessage.setText("Vous pouvez charger un plan");
		getContentPane().add(zoneMessage);
		
		//Souris
        ecouteurSouris = new EcouteurSouris(controller, vuePlan, this);
        vuePlan.addMouseListener(ecouteurSouris);
        
        this.setVisible(true);
    }

    public void creerBoutons(ControleurApplication controller) {
    	ecouteurDeBoutons = new EcouteurBoutons(controller);
		boutons = new ArrayList<JButton>();
		for (int i=0; i<intitulesBoutons.length; i++){
			JButton bouton = new JButton(intitulesBoutons[i]);
			boutons.add(bouton);
			bouton.setForeground(Color.DARK_GRAY);
			bouton.setSize(largeurBouton,hauteurBouton);
			bouton.setLocation((boutons.size()-1)*largeurBouton+5, 0);
			bouton.setFocusable(false);
			bouton.setFocusPainted(false);
			bouton.addActionListener(ecouteurDeBoutons);
			this.getContentPane().add(bouton);	
		}
		
		//On desactive le bouton "charger livraisons"
		boutons.get(1).setEnabled(false);
		//On désactive le bouton "calculer une tournée"
		boutons.get(2).setEnabled(false);
    }

	public int getHauteurFenetre() {
		return hauteurFenetre;
	}

	public int getLargeurFenetre() {
		return largeurFenetre;
	}

	/**
	 * @return the vuePlan
	 */
	public VuePlan getVuePlan() {
		return vuePlan;
	}

	/**
	 * @param vuePlan the vuePlan to set
	 */
	public void setVuePlan(VuePlan vuePlan) {
		this.vuePlan = vuePlan;
	}

	/**
	 * @return the boutons
	 */
	public ArrayList<JButton> getBoutons() {
		return boutons;
	}

	/**
	 * @param boutons the boutons to set
	 */
	public void setBoutons(ArrayList<JButton> boutons) {
		this.boutons = boutons;
	}

	/**
	 * @return the zoneMessage
	 */
	public JLabel getZoneMessage() {
		return zoneMessage;
	}

	/**
	 * @param zoneMessage the zoneMessage to set
	 */
	public void setZoneMessage(JLabel zoneMessage) {
		this.zoneMessage = zoneMessage;
	}

    
}

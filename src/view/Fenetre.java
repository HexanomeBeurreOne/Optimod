package view;

import model.*;

import javax.swing.*;
import javax.swing.border.Border;

import controller.ControleurApplication;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by cyrilcanete on 03/11/15.
 */
public class Fenetre extends JFrame{
	private final int hauteurFenetre = 500;
	private final int largeurFenetre = 950;
	
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
	protected static final String CHANGER = "Calculer tournée";
	protected static final String AJOUTER_LIVRAISON = "Ajouter livraison";
	protected static final String SUPPRIMER_LIVRAISON = "Supprimer livraison";
	protected static final String UNDO = "Undo";
	protected static final String REDO = "Redo";
	private final String[] intitulesBoutons = new String[]{CHARGER_PLAN, CHARGER_LIVRAISONS, CALCULER_TOURNEE, AJOUTER_LIVRAISON, SUPPRIMER_LIVRAISON, UNDO, REDO};
	private EcouteurBoutons ecouteurDeBoutons;
	private EcouteurClavier ecouteurClavier;
	private EcouteurSouris ecouteurSouris;
	
	private JLabel zoneMessage;
	private final int hauteurMessage = 27;
	
	private ArrayList<Color> couleurs;
	private ArrayList<Integer> epaisseursLignes;

    public Fenetre(Plan plan, double echelle, ControleurApplication controller) throws HeadlessException {

    	couleurs = new ArrayList<Color>();
    	epaisseursLignes = new ArrayList<Integer>();
    	
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
        vueLivraison.setSize(largeurFenetre-largeurVuePlan, hauteurVuePlan);
        
        //Boutons
        creerBoutons(controller);
        
        //ZoneMessage
        zoneMessage = new JLabel();
        
        Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
        Border border = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
        zoneMessage.setBorder(BorderFactory.createCompoundBorder(border,paddingBorder));
        
        zoneMessage.setSize(largeurFenetre,hauteurMessage);
		zoneMessage.setLocation(0,hauteurBouton);
		zoneMessage.setText("Vous pouvez charger un plan");
		
		getContentPane().add(zoneMessage);
		
		//Clavier
		ecouteurClavier = new EcouteurClavier(controller);
		this.addKeyListener(ecouteurClavier);
		this.setFocusable(true);
		
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
			bouton.setLocation((boutons.size()-1)*largeurBouton+4, 0);
			bouton.setFocusable(false);
			bouton.setFocusPainted(false);
			bouton.addActionListener(ecouteurDeBoutons);
			this.getContentPane().add(bouton);	
		}
		
		//On desactive le bouton "charger livraisons"
		boutons.get(1).setEnabled(false);
		//On désactive le bouton "calculer tournée"
		boutons.get(2).setEnabled(false);
		//On désactive le bouton "ajouter livraison"
		boutons.get(3).setEnabled(false);
		//On désactive le bouton "supprimer livraison"
		boutons.get(4).setEnabled(false);
    }
    
    public void genererCouleurs(int infosTroncons) {
    	float R = 0.0f, G = 0.0f, B = 0.0f;
    	
//    	for (int i = 0; i < infosTroncons.size(); i++) {
//    		
//    		
//    		for (int j = 0; j < infosTroncons.get(i); j++) {
//    			couleurs.add(new Color(R, G, B));
//    			epaisseursLignes.add((infosTroncons.size()-i)*3);
//    		}
//    	}
    	for (int i = 0; i < infosTroncons; i++) {
    		R=(float)Math.random();
    		G=(float)Math.random();
    		B=(float)Math.random();
    		couleurs.add(new Color(R, G, B));
    		epaisseursLignes.add((infosTroncons-i)*3);
    	}
    	System.out.println(couleurs.size());
    	System.out.println(epaisseursLignes.size());
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

	/**
	 * @return the couleurs
	 */
	public ArrayList<Color> getCouleurs() {
		return couleurs;
	}

	/**
	 * @param couleurs the couleurs to set
	 */
	public void setCouleurs(ArrayList<Color> couleurs) {
		this.couleurs = couleurs;
	}

	/**
	 * @return the epaisseursLignes
	 */
	public ArrayList<Integer> getEpaisseursLignes() {
		return epaisseursLignes;
	}

	/**
	 * @param epaisseursLignes the epaisseursLignes to set
	 */
	public void setEpaisseursLignes(ArrayList<Integer> epaisseursLignes) {
		this.epaisseursLignes = epaisseursLignes;
	}

	public int getLargeurVuePlan() {
		return largeurVuePlan;
	}

	public int getHauteurVuePlan() {
		return hauteurVuePlan;
	}

	public int saisirClient() {
		String resultat = (String)JOptionPane.showInputDialog(
                this,
                "Veuillez saisir un identifiant de client",
                "Ajouter livraison",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "");
		
		try {
			return Integer.parseInt(resultat);
		} catch (Exception e) {
			return -1;
		}
	}
	
	public String secondeToHeure (int heureEnSeconde) {
		if (heureEnSeconde < 0) return "0";
		return (int)heureEnSeconde/3600 + ":"+ ((int)heureEnSeconde%3600)/60 + ":"+ (int)heureEnSeconde%60;
	}
}

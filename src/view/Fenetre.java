package view;

import model.*;

import javax.swing.*;
import javax.swing.border.Border;

import controller.ControleurApplication;

import java.awt.*;
import java.util.ArrayList;

public class Fenetre extends JFrame{
	private final int hauteurFenetre = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()*0.9);
	private final int largeurFenetre = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()*0.9);
	
	private VuePlan vuePlan;
	private int largeurVuePlan;
	private int hauteurVuePlan;
	
	private VueLivraison vueLivraison;
	private int largeurVueLivraison;
	private int hauteurLivraison;
	
	private ArrayList<JButton> boutons;
	private final int hauteurBouton = (int) (hauteurFenetre*0.05);
	private final int largeurBouton = (int) (largeurFenetre/8)-2;
	protected final static String CHARGER_PLAN = "Charger plan";
	protected static final String CHARGER_LIVRAISONS = "Charger livraisons";
	protected static final String CALCULER_TOURNEE = "Calculer tournee";
	protected static final String AJOUTER_LIVRAISON = "Ajouter livraison";
	protected static final String SUPPRIMER_LIVRAISON = "Supprimer livraison";
	protected static final String UNDO = "Annuler";
	protected static final String REDO = "Retablir";
	protected static final String ENREGISTRER_FEUILLE_ROUTE = "Feuille de Route";
	private final String[] intitulesBoutons = new String[]{CHARGER_PLAN, CHARGER_LIVRAISONS, CALCULER_TOURNEE, AJOUTER_LIVRAISON, SUPPRIMER_LIVRAISON, UNDO, REDO, ENREGISTRER_FEUILLE_ROUTE};
	private EcouteurBoutons ecouteurDeBoutons;
	private EcouteurClavier ecouteurClavier;
	private EcouteurSouris ecouteurSouris;
	
	private JLabel zoneMessage;
	private final int hauteurMessage = (int) (hauteurFenetre*0.05);
	
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
        zoneMessage.setFont(new Font("truetype_font", Font.PLAIN, (int) (hauteurMessage*0.4)));
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

    /**
     * instancie les boutons
     * @param controller
     */
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
		//On desactive le bouton "calculer tournee"
		boutons.get(2).setEnabled(false);
		//On desactive le bouton "ajouter livraison"
		boutons.get(3).setEnabled(false);
		//On desactive le bouton "supprimer livraison"
		boutons.get(4).setEnabled(false);
    }
    
    /**
     * genere aleatoirement autant de couleurs qu'il existe de fenetre de livraison
     * @param infosTroncons
     */
    public void genererCouleurs(int infosTroncons) {
    	float R = 0.0f, G = 0.0f, B = 0.0f;
    	
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
    
    /**
     * @return hauteurFenetre
     */
	public int getHauteurFenetre() {
		return hauteurFenetre;
	}

	/**
	 * @return largeurFenetre
	 */
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

	/**
	 * @return largeurVuePlan
	 */
	public int getLargeurVuePlan() {
		return largeurVuePlan;
	}

	/**
	 * @return hauteurVuePlan
	 */
	public int getHauteurVuePlan() {
		return hauteurVuePlan;
	}

	/**
	 * ouvre la popup de demande de saisie de l'identifiant du client et renvoie cette identifiant si c'est un entier positif
	 * renvoie -1 en cas d'erreur
	 * @return
	 */
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
	
	/**
	 * convertie le nombre de seconde passe en parametre en temps sous la forme d'une chaine de caracteres
	 * @param heureEnSeconde
	 * @return
	 */
	public String secondeToHeure (int heureEnSeconde) {
		if (heureEnSeconde < 0) return "0";
		return (int)heureEnSeconde/3600 + ":"+ ((int)heureEnSeconde%3600)/60 + ":"+ (int)heureEnSeconde%60;
	}
}

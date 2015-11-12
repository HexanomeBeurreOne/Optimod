package view;

import model.*;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.*;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
/**
 * Created by cyrilcanete on 03/11/15.
 */
public class VuePlan extends JPanel implements Observer {

    private double echelle;
    private Plan plan;
    private boolean couleursDefinies;
    private ArrayList<Color> couleurs;
    private Fenetre fenetre;
    
    public VuePlan(Plan plan, double echelle, Fenetre fenetre) {
        super();
        
        plan.addObserver(this);
        setLayout(null);
        
        this.setBackground(Color.WHITE);
        
        //Couleur des bordures
        this.setBorder(new CompoundBorder(
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY), 
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY)));
        
        // Position du plan
        setLocation(5, 40);
        
        fenetre.getContentPane().add(this);
        this.fenetre = fenetre;
        this.plan = plan;
        this.echelle=echelle;
        this.couleursDefinies = false;
        this.couleurs = new ArrayList<Color>();
    }
    

	/**
	 * @return the plan
	 */
	public Plan getPlan() {
		return plan;
	}

	/**
	 * @param plan the plan to set
	 */
	public void setPlan(Plan plan) {
		this.plan = plan;
	}

    @Override
    public void paintComponent(Graphics g) {
    	
    	super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Récupérer troncons du plan
        List<Troncon>  tronconsPlan = plan.getTroncons();
        Iterator<Troncon> itTroncons = tronconsPlan.iterator();
        g2.setColor(Color.LIGHT_GRAY);
        while (itTroncons.hasNext()){
            drawTroncon(g2, itTroncons.next());
        }
        
        // Colorier les chemins
        colorierChemins(g2);
        
        // Récupérer adresses du plan
        List<Adresse>  adressesPlan = plan.getAdresses();
        Iterator<Adresse> itAdresses = adressesPlan.iterator();
        g2.setColor(Color.GRAY);
        while (itAdresses.hasNext()){
        	Adresse temp = itAdresses.next();
        	if(temp.isSelectionnee()) {
        		g2.setColor(Color.BLUE);
        		drawAdresse(g2, temp, 6);
        		g2.setColor(Color.GRAY);
        	}
            drawAdresse(g2, temp, 4);
        }
        
        // Colorier l'entrepot
		colorierEntrepot(g2);
        
        // Colorier les livraisons
        colorierLivraisons(g2);
    }

	private int scaleIt(int coordonnee) {
        return (int) Math.round(coordonnee*this.echelle);
    }
    
	private void drawAdresse(Graphics2D g2, Adresse adresse, int rayon) {
    	int x = scaleIt(adresse.getCoordX());
        int y = scaleIt(adresse.getCoordY());
        g2.fillOval(x - rayon, y - rayon, 2*rayon, 2*rayon);
    }

    private void drawTroncon(Graphics2D g2, Troncon troncon) {
        Adresse origine = troncon.getOrigine();
        Adresse destination = troncon.getDestination();
        int xOrigine = scaleIt(origine.getCoordX());
        int yOrigine = scaleIt(origine.getCoordY());
        int xDestination = scaleIt(destination.getCoordX());
        int yDestination = scaleIt(destination.getCoordY());
        g2.drawLine(xOrigine, yOrigine, xDestination, yDestination);
    }

	private void colorierLivraisons(Graphics2D g2) {
		
		int indiceCouleur=0;
		List<FenetreLivraison> fenetreLivraisons = plan.getDemandeLivraisons().getFenetresLivraisons();
		Iterator<FenetreLivraison> itFL = fenetreLivraisons.iterator();
		Livraison livraisonTemp;
		Adresse adresseTemp;
		
		while (itFL.hasNext()) {
			
			FenetreLivraison fenetreLivraisonActuelle = itFL.next();
			Iterator<Livraison> itL = fenetreLivraisonActuelle.getLivraisons().iterator();
			while (itL.hasNext()) {
				livraisonTemp = itL.next();
				adresseTemp = livraisonTemp.getAdresse();
				g2.setColor(fenetre.getCouleurs().get(indiceCouleur));
				System.out.println(indiceCouleur);
				this.drawAdresse(g2, adresseTemp, 8);
				if(livraisonTemp.isSelectionnee()) {
					g2.setColor(Color.BLUE);
					this.drawAdresse(g2, adresseTemp, 4);
				} else {
					g2.setColor(Color.WHITE);
					this.drawAdresse(g2, adresseTemp, 4);
				}
				
			}
			indiceCouleur++;
			
		}
	}
	
	private void colorierEntrepot(Graphics2D g2) {
		Adresse entrepot = plan.getDemandeLivraisons().getEntrepot();
		if (entrepot!=null) {
			//entrepot.afficheAdresse();
			g2.setColor(Color.RED);
			this.drawAdresse(g2, entrepot, 8);
		}
	}
	
    private void colorierChemins(Graphics2D g2) {
    	
    	int indiceFenetre = 0;
    	List<Troncon> troncons;
		List<Etape> etapes = plan.getTournee().getEtapes();
		
		if (etapes != null) {
			
			FenetreLivraison fenetreL = etapes.get(0).getLivraison().getFenetreLivraison();
			
//			Iterator<Etape> itE = etapes.iterator();
//			
//			while (itE.hasNext()) {
			
			for (int i = 0; i < etapes.size(); i++) {
				
				if(etapes.get(i).getLivraison().getFenetreLivraison() != fenetreL) {
					indiceFenetre++;
					fenetreL = etapes.get(i).getLivraison().getFenetreLivraison();
				}
				
				troncons = etapes.get(i).getChemin().getTroncons();
				Iterator<Troncon> itT = troncons.iterator();
				g2.setColor(fenetre.getCouleurs().get(indiceFenetre));
				g2.setStroke(new BasicStroke(fenetre.getEpaisseursLignes().get(indiceFenetre)));
				
				while (itT.hasNext()) {
					drawTroncon(g2, itT.next());
				}
				
				
			}
			
			g2.setColor(Color.RED);
			troncons = plan.getTournee().getRetourEntrepot().getTroncons();
			Iterator<Troncon> itT = troncons.iterator();
			
			while (itT.hasNext()) {
				drawTroncon(g2, itT.next());
			}
		}
	}
	
    /**
     * @param fromPoint end of the arrow
     * @param rotationDeg rotation angle of line
     * @param length arrow length
     * @param wingsAngleDeg wingspan of arrow
     * @return Path2D arrow shape
     */
    public static Path2D createArrowForLine(
            Point2D fromPoint,
            double rotationDeg,
            double length,
            double wingsAngleDeg) {

        double ax = fromPoint.getX();
        double ay = fromPoint.getY();

        double radB = Math.toRadians(-rotationDeg + wingsAngleDeg);
        double radC = Math.toRadians(-rotationDeg - wingsAngleDeg);

        Path2D resultPath = new Path2D.Double();
        resultPath.moveTo(length * Math.cos(radB) + ax, length * Math.sin(radB) + ay);
        resultPath.lineTo(ax, ay);
        resultPath.lineTo(length * Math.cos(radC) + ax, length * Math.sin(radC) + ay);
        return resultPath;
    }

    @Override
    public void update(Observable o, Object arg) {
//    	Plan plan = (Plan)arg;
//    	this.plan = plan;
    	
    	repaint();
    }
}

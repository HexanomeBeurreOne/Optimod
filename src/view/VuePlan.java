package view;

import model.*;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.*;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
/**
 * Created by cyrilcanete on 03/11/15.
 */
public class VuePlan extends JPanel implements Observer {

    private double echelle;
    private Plan plan;
    
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
        this.plan = plan;
        this.echelle=echelle;
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
        
        // Récupérer adresses du plan
        List<Adresse>  adressesPlan = plan.getAdresses();
        Iterator<Adresse> itAdresses = adressesPlan.iterator();
        g2.setColor(Color.GRAY);
        while (itAdresses.hasNext()){
            drawAdresse(g2, itAdresses.next(), 4);
        }
        
        // colorier l'entrepot
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
		float R, G, B;
		List<FenetreLivraison> fenetreLivraisons = plan.getDemandeLivraisons().getFenetresLivraisons();
		Iterator<FenetreLivraison> itFL = fenetreLivraisons.iterator();
		while (itFL.hasNext()) {
			R=(float)Math.random();
			
			G=(float)Math.random();
			
			B=(float)Math.random();
			
			
			FenetreLivraison fenetreLivraisonActuelle = itFL.next();
			Iterator<Livraison> itL = fenetreLivraisonActuelle.getLivraisons().iterator();
			while (itL.hasNext()) {
				g2.setColor(new Color(R, G, B));
				Adresse adresseTemp = itL.next().getAdresse();
				this.drawAdresse(g2, adresseTemp, 8);
				g2.setColor(Color.WHITE);
				this.drawAdresse(g2, adresseTemp, 4);
			}
		}
	}
	
	private void colorierEntrepot(Graphics2D g2) {
		Adresse entrepot = plan.getAdresseById(plan.getDemandeLivraisons().getIdEntrepot());
		if (entrepot!=null) {
			//entrepot.afficheAdresse();
			g2.setColor(Color.RED);
			this.drawAdresse(g2, entrepot, 8);
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
    	Plan plan = (Plan)arg;
    	this.plan = plan;
    	repaint();
    }
}

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
            drawAdresse(g2, itAdresses.next());
        }
    }

    private void drawAdresse(Graphics2D g2, Adresse adresse) {
    	int x = scaleIt(adresse.getCoordX());
        int y = scaleIt(adresse.getCoordY());
        g2.fillOval(x - 3, y - 3, 6, 6);
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

    private int scaleIt(int coordonnee) {
        return (int) Math.round(coordonnee*this.echelle);
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
    	this.plan = (Plan)arg;
    	repaint();
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
}

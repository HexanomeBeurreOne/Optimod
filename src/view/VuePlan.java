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
    private int hauteurVuePlan;
    private int largeurVuePlan;
    private Plan plan;
    


    public VuePlan(Plan plan, double echelle, Fenetre fenetre) {
        super();
        //plan.addObserver(this);
        setLayout(null);
        
        hauteurVuePlan = fenetre.getHauteurFenetre()-70;
        largeurVuePlan = 2*fenetre.getLargeurFenetre()/3;
        
        //Taille du plan
        setSize(largeurVuePlan, hauteurVuePlan);
        
        this.setBackground(Color.LIGHT_GRAY);
        
        //Couleur des bordures
        this.setBorder(new CompoundBorder(
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY), 
        	    BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY)));
        
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
        g2.setColor(Color.WHITE);
        while (itTroncons.hasNext()){
            drawTroncon(g2, itTroncons.next());
        }
        
        // Récupérer adresses du plan
        List<Adresse>  adressesPlan = plan.getAdresses();
        Iterator<Adresse> itAdresses = adressesPlan.iterator();
        g2.setColor(Color.WHITE);
        while (itAdresses.hasNext()){
            drawAdresse(g2, itAdresses.next());
        }
    }

    private void drawAdresse(Graphics2D g2, Adresse adresse) {
    	
    	
        int x = scaleIt(adresse.getCoordX());
        int y = scaleIt(adresse.getCoordY());
        g2.fillOval(x - 4, y - 4, 8, 8);
    }

    private void drawTroncon(Graphics2D g2, Troncon troncon) {
        Adresse origine = troncon.getOrigine();
        Adresse destination = troncon.getDestination();
        int xOrigine = scaleIt(origine.getCoordX());
        int yOrigine = scaleIt(origine.getCoordY());
        int xDestination = scaleIt(destination.getCoordX());
        int yDestination = scaleIt(destination.getCoordY());
        g2.setStroke(new BasicStroke(3));
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

    }
}

package view;

import model.*;

import java.awt.*;
import java.util.*;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * Created by cyrilcanete on 03/11/15.
 */
public class VuePlan extends JPanel implements Observer {

    private int echelle;
    private int hauteurVue;
    private int largeurVue;
    private Plan plan;


    public VuePlan(Plan plan, int echelle) {
        super();
        //plan.addObserver(this);
        this.plan = plan;

    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Récupérer adresses du plan
        List<Adresse>  adressesPlan = plan.getAdresses();
        Iterator<Adresse> itAdresses = adressesPlan.iterator();
        while (itAdresses.hasNext()){
            drawAdresse(g2, itAdresses.next());
        }
        // Récupérer adresses du plan
        List<Troncon>  tronconsPlan = plan.getTroncons();
        Iterator<Troncon> itTroncons = tronconsPlan.iterator();
        while (itTroncons.hasNext()){
            drawTroncon(g2, itTroncons.next());
        }

        //Font font = new Font("Serif", Font.PLAIN, 96);
        //g2.drawString("Text", 40, 120);
        //g2.setFont(font);
        //g2.drawLine(14, 14, 45, 45);
    }

    private void drawAdresse(Graphics2D g2, Adresse adresse) {
        adresse.afficheAdresse();
        int x = adresse.getCoordX();
        int y = adresse.getCoordY();
        g2.drawLine(x, y, x, y);
    }

    private void drawTroncon(Graphics2D g2, Troncon troncon) {
        troncon.afficheTroncon();
        int x = troncon.
        int y = adresse.getCoordY();
        g2.drawLine(x, y, x, y);
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

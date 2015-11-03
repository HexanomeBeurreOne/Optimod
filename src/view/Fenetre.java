package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by cyrilcanete on 03/11/15.
 */
public class Fenetre extends JFrame implements Observer {

    public Fenetre(Plan plan, int echelle) throws HeadlessException {

        //Définit un titre pour notre fenêtre
        this.setTitle("Optimod");
        //Définit sa taille : 400 pixels de large et 100 pixels de haut
        this.setSize(1200, 800);
        //Nous demandons maintenant à notre objet de se positionner au centre
        this.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Instanciation d'un objet VuePlan
        VuePlan carte = new VuePlan(plan, echelle);
        //On prévient notre JFrame que notre JPanel sera son content pane
        this.setContentPane(carte);

        //Et enfin, la rendre visible
        this.setVisible(true);

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

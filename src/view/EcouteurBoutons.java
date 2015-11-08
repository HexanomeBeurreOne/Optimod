package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.ControleurApplication;

public class EcouteurBoutons implements ActionListener {
//------------------------------------------------- ATTRIBUTES	
	private ControleurApplication controleur;

//------------------------------------------------- CONSTRUCTORS
	public EcouteurBoutons(ControleurApplication controller){
		this.controleur = controller;
	}

//------------------------------------------------- METHODS
	@Override
	public void actionPerformed(ActionEvent e) { 
		// Methode appelee par l'ecouteur de boutons a chaque fois qu'un bouton est clique
		// Envoi au controleur du message correspondant au bouton clique
		switch (e.getActionCommand()){
//		case Fenetre.CHARGER_PLAN: controleur.chargerPlan(); break;
//		case Fenetre.CHARGER_LIVRAISONS: controleur.chargerLivraisons(); break;
//		case Fenetre.CALCULER_TOURNEE: controleur.calculerTournee(); break;
		}
	}
}
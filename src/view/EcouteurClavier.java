package view;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import controller.ControleurApplication;

public class EcouteurClavier implements KeyListener {
	
	private boolean ctrlPressed;
	private ControleurApplication controleur;

	public EcouteurClavier(ControleurApplication controleur){
		this.controleur = controleur;
		ctrlPressed = false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			ctrlPressed = true;
		}
		//UNDO
		if (e.getKeyCode() == KeyEvent.VK_Z && ctrlPressed == true)
		{
			controleur.caractereSaisi("undo");
		}
		
		//REDO
		if (e.getKeyCode() == KeyEvent.VK_Y && ctrlPressed == true)
		{
			controleur.caractereSaisi("redo");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL)
		{
			ctrlPressed = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}

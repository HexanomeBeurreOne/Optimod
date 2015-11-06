package controller;

import model.Plan;
import model.DemandeLivraisons;
import model.Livraison;
import model.FenetreLivraison;
import controller.commands.*;

public class ControlleurApplication 
{
//------------------------------------------------- ATTRIBUTES	
	
	protected PilesEtats undoRedo;

	protected Plan plan;
	protected Livraison livraison;
	protected FenetreLivraison fenetre;
	protected DemandeLivraisons demande;
	
//------------------------------------------------- CONSTRUCTORS
	
	public ControlleurApplication(Plan p)
	{
		plan = p;
		undoRedo = new PilesEtats();
	}
		
//------------------------------------------------- METHODS
	
	public void undo()
	{
		undoRedo.undo();
	}
	
	public void redo()
	{
		undoRedo.redo();
	}
	
	public void addLivraison()
	{
		AjouterLivraison ajout = new AjouterLivraison(livraison, fenetre, demande);
		undoRedo.addCommand(ajout);
	}
	
}

package controller;

import model.Plan;
import model.DemandeLivraisons;

public class ControlleurApplication 
{
//------------------------------------------------- ATTRIBUTES	
	
	protected PilesEtats undoRedo;
	//protected Model model;
	
	protected DemandeLivraisons demande;
	
//------------------------------------------------- CONSTRUCTORS
	
	public ControlleurApplication()
	{
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
		
	}
	
}

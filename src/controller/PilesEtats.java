package controller;

import java.util.Stack;

import controller.commands.Commande;

public class PilesEtats 
{
//------------------------------------------------- ATTRIBUTES	
	protected Stack<Commande> undoStack;
	
	protected Stack<Commande> redoStack;

//------------------------------------------------- CONSTRUCTORS
	public PilesEtats()
	{
		undoStack = new Stack<Commande>();
		redoStack = new Stack<Commande>();
	}
	
//------------------------------------------------- METHODS
	public void addCommand(Commande c)
	{
		undoStack.push(c);
	}
	
	public void undo()
	{
		if(!undoStack.empty())
		{
			Commande lastCommand = undoStack.pop();
			lastCommand.unExecute();
			redoStack.push(lastCommand);
		}
	}
	
	public void redo()
	{
		if(!redoStack.empty())
		{
			Commande lastCommand = redoStack.pop();
			lastCommand.execute();
			undoStack.push(lastCommand);
		}
	}
	
}

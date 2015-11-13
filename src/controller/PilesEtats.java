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
		c.execute();
		undoStack.push(c);
		redoStack.clear();
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
	
	public void clear()
	{
		redoStack.clear();
		undoStack.clear();
	}
	
}

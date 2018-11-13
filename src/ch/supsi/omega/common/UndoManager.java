package ch.supsi.omega.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a generic undo / redo manager. 
 * The class manages the states of a stack (i.e.: List) for a choosen data type.
 * @author Vanni Galli
 * @version 1.0
 * @param <StateType> The data type to be stored / managed in the stack.
 */
public class UndoManager<StateType>
{
	private List<StateType>	undoStack				= null;
	private StateType			initialState			= null;
	private int					currentStackPosition	= -1;
	private boolean			canUndo					= false;
	private boolean			canRedo					= false;

	public List<StateType> getUndoStack()
	{
		return undoStack;
	}

	public boolean isCanUndo()
	{
		return canUndo;
	}

	public boolean isCanRedo()
	{
		return canRedo;
	}

	public UndoManager()
	{
		undoStack = new ArrayList<StateType>();
	}

	public UndoManager(StateType initialState)
	{
		this.initialState = initialState;

		undoStack = new ArrayList<StateType>();
	}

	public StateType getCurrentState()
	{
		if (currentStackPosition < 0)
			return initialState;

		return undoStack.get(currentStackPosition);
	}

	public void addToStack(StateType state)
	{
		currentStackPosition++;
		undoStack.add(currentStackPosition, state);

		if (undoStack.size() > 1)
			undoStack = undoStack.subList(0, currentStackPosition + 1);

		canUndo = true;
		canRedo = false;
	}

	public void undo()
	{
		if (canUndo)
		{
			currentStackPosition--;

			canUndo = (currentStackPosition >= 0) ? true : false;
			canRedo = true;
		}
	}

	public void redo()
	{
		if (canRedo)
		{
			currentStackPosition++;

			canUndo = true;
			canRedo = (currentStackPosition < undoStack.size() - 1) ? true : false;
		}
	}
}

package ch.supsi.omega.common;

import junit.framework.TestCase;

import org.junit.Test;

public class UndoManagerTest extends TestCase
{
	static UndoManager<Integer> um = null;
	
	public void setUp()
	{
		um = new UndoManager<Integer>();
	}
	
	@Test
	public void testUndoManager()
	{
		assertEquals(false, um.isCanUndo());
		assertEquals(false, um.isCanRedo());
		
		// 1
		um.addToStack(new Integer(1));
		assertEquals(true,  um.isCanUndo());
		assertEquals(false, um.isCanRedo());
		assertEquals(new Integer(1), um.getCurrentState());
		
		// (1)
		um.undo();
		assertEquals(null, um.getCurrentState());
		
		// 1
		um.redo();
		assertEquals(new Integer(1), um.getCurrentState());
		
		// (1 - 2 - 3) - 4
		um.addToStack(new Integer(2));
		um.addToStack(new Integer(3));
		um.addToStack(new Integer(4));
		assertEquals(new Integer(4), um.getCurrentState());
		
		// 1 - 2 - 3 (- 4)
		um.undo();
		assertEquals(new Integer(3), um.getCurrentState());
		
		// (1 - 2 - 3) - 4
		um.redo();
		assertEquals(new Integer(4), um.getCurrentState());
		assertEquals(true,  um.isCanUndo());
		assertEquals(false, um.isCanRedo());
		
		// (1 -) 2 (- 3 - 4)
		um.undo();
		um.undo();
		assertEquals(new Integer(2), um.getCurrentState());
		assertEquals("stack: [1-2-3-4]", printStack());
		
		// (1 - 2) - 9
		um.addToStack(new Integer(9));
		assertEquals(new Integer(9), um.getCurrentState());
		assertEquals(3, um.getUndoStack().size());
		
		// (1 -) 2 (- 9)
		um.undo();
		assertEquals(new Integer(2), um.getCurrentState());
		
		// (1 - 2) - 9
		um.redo();
		assertEquals(new Integer(9), um.getCurrentState());
		assertEquals(true,  um.isCanUndo());
		assertEquals(false, um.isCanRedo());
				
		// (1 - 2 - 9)
		um.undo();
		um.undo();
		um.undo();
		um.undo(); um.undo(); um.undo(); // useless ones
		assertEquals(null, um.getCurrentState());
		
		// 1 (- 2 - 9)
		um.redo();
		assertEquals(new Integer(1), um.getCurrentState());
		
		// (1 -) 5
		um.addToStack(new Integer(5));
		assertEquals(new Integer(5), um.getCurrentState());
		assertEquals(2, um.getUndoStack().size());
		
		// (1 - 5) - 9
		um.addToStack(new Integer(9));
		assertEquals(new Integer(9), um.getCurrentState());
		
		um.redo(); um.redo(); um.redo(); // useless ones
		assertEquals(new Integer(9), um.getCurrentState());
		assertEquals("stack: [1-5-9]", printStack());
	}
	
	private static String printStack()
	{
		String t;
		t = "stack: [";
		
		for(int i = 0; i < um.getUndoStack().size()-1; i++)
			t = t + um.getUndoStack().get(i) + "-";
		
		t = t + um.getUndoStack().get(um.getUndoStack().size()-1) + "]";
		
		return t;
	}
}

package vooga.debugger.model;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;

import vooga.debugger.view.GameTreeNode;

import com.golden.gamedev.Game;

/**
 * Model side of the Debugger MVC, handles Fields and instantiates DebuggerParser
 * 
 * @author Troy Ferrell
 * @author Austin Benesh
 */

public class DebuggerModel 
{
	private Collection<GameField> myFields;
	private Game myGame;
	private Field gameField;
	private DebuggerParser myParser;
	
	public boolean showAllVariables;
	
	/**
	 * DebuggerModel constructor
	 * @param game Game to be debugged
	 */
	public DebuggerModel(Game game)
	{
		myGame = game;
		myParser = new XMLParser();
		myFields = new ArrayList<GameField>();
		
		Field [] fields = this.getClass().getDeclaredFields();
		for(Field f : fields)
		{
			if(f.getName().equals("myGame"))
				gameField = f;
		}
	}
	
	// ************
	// TEST CODE
	// ************
	
	/**
	 * Builds a tree of GameFields using recursive helper method getFields
	 * @param showAll 
	 * @return gameRoot
	 */
	public GameTreeNode getGameTree(boolean showAll)
	{
		GameTreeNode gameRoot = new GameTreeNode( gameField );
		getFields(myGame.getClass(),gameRoot,showAll);
		return gameRoot;
	}
	
	private void getFields(Class<?> rootClass,  GameTreeNode root, boolean showAll)
	{
		try 
		{
			Field [] validFields = myParser.getValidFieldsFor(rootClass, showAll);
			for(int i = 0; i < validFields.length; i++)
			{
				Class<?> fieldClass = validFields[i].getType();
				GameTreeNode node = new GameTreeNode( validFields[i] );
				getFields(fieldClass, node, showAll);
				root.add(node);
			}
		  }
		  catch (Throwable e) 
		  {
		     System.err.println(e);
		  }
	}
	/**
	 * Returns if an input GameField already exists to prevent repetition
	 * @param gf Gamefield to check
	 * @return boolean
	 */
	public boolean isFieldAlreadyLive(GameField gf)
	{
		for(GameField gameField : myFields)
			if(gameField.areFieldsEqual(gf))
				return true;
		
		return false;
	}
	/**
	 * Add a GameField
	 * @param gf
	 */
	public void addField(GameField gf)
	{
		if(gf != null)
			myFields.add(gf);
	}
	/**
	 * Remove a GameField
	 * @param gf
	 */
	public void removeField(GameField gf)
	{
		myFields.remove(gf);
	}
	/**
	 * Updates tree and corresponding GameFields
	 */
	public void update()
	{	
		try
		{		
			for(GameField gf : myFields)
			{
				Field [] fieldPath = gf.getPath();
				Object fieldInstance = myGame;
				
				for(int i = 1; i < fieldPath.length; i++)
				{
					fieldPath[i].setAccessible(true);
					if(fieldInstance != null)
						fieldInstance = fieldPath[i].get(fieldInstance);
				}
				gf.update(fieldInstance);
			}
			
		}catch(IllegalAccessException e)
		{
			e.printStackTrace();	
		}
		catch(ConcurrentModificationException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
}

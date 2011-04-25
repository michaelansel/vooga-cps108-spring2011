package vooga.levels.example2;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import vooga.core.VoogaGame;
import vooga.levels.LevelManager;
import vooga.resources.ResourceManager;
import vooga.sprites.improvedsprites.Sprite;
import vooga.sprites.spritegroups.SpriteGroup;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;

public class Example2 extends VoogaGame {

	private LevelManager myLevelManager;
	private ArrayList<Sprite> myPlayers;
	
    public static void main (String[] args)
    {
        GameLoader game = new GameLoader();
        game.setup(new Example2(), new Dimension(640,480), false);
        game.start();
    }
	
	@Override
	public void initResources() {
	    SpriteGroup<Sprite> players = new SpriteGroup<Sprite>("player group");
	    Sprite player = new Sprite(getImage("Ship1.png"));
	    players.addSprites(player);
	    //myResourceManager = new ResourceManager("");
		myLevelManager = new LevelManager(this);
		myLevelManager.loadLevel(0);
	}

	@Override
	public void update(long elapsedTime) {
		myLevelManager.update(elapsedTime);
	}

	@Override
	public void render(Graphics2D g) {
		myLevelManager.render(g);
	}

    @Override
    public void updatePlayField (long elapsedTime)
    {
        myLevelManager.update(elapsedTime);
    }

}

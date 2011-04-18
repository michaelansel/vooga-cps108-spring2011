package games.invaders.game;

import java.awt.Dimension;

import com.golden.gamedev.GameEngine;
import com.golden.gamedev.GameLoader;
import com.golden.gamedev.GameObject;

public class Invaders extends GameEngine {
	{
		distribute = true;
	}

	public static final int TITLE = 0, GAME_MODE = 1;

	@Override
	public void initResources() {
		// nextGameID = TITLE;
		nextGameID = GAME_MODE;
		// nextGameID = GAME_MODE;
	}

	@Override
	public GameObject getGame(int GameID) {
		switch (GameID) {
		case TITLE:
			return new MainMenu(this);
		case GAME_MODE:
			return new InvadersGame(this);
		}
		return null;
	}

	public static void main(String[] args) {
		GameLoader game = new GameLoader();
		// TODO: The Invaders Game has a specific Dimension that it is designed
		// to be played at, so just use that.
		// PlayMusic playMusic = new PlayMusic("theme.wav");
		// playMusic.run();
		game.setup(new Invaders(), new Dimension(800, 600), false);
		game.start();
	}
}
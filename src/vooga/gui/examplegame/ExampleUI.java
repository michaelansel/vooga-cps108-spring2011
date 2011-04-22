package vooga.gui.examplegame;

import java.awt.Dimension;  
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.golden.gamedev.object.Sprite;

import vooga.gui.panes.UIPane;
import vooga.gui.util.NumberDisplay;
import vooga.gui.util.VoogaButton;

public class ExampleUI extends UIPane{
	ExampleGame parent;
	Sprite uiBase;
	NumberDisplay score;
	VoogaButton menuButton;
	
	public ExampleUI(ExampleGame game){
		parent=game;
		init();
	}

	@Override
	public void init() {
		//create the frame
		BufferedImage image = parent.getImage("/vooga/gui/game/images/userInterfaceBase2.gif");
		uiBase=new Sprite(image, 
				parent.getWidth()/2-image.getWidth()/2, 
				parent.getHeight()/2-image.getHeight()/2);
		
		//create score display
		score=new NumberDisplay(240, 460, parent);
		
		//create menu button
		image = parent.getImage("/vooga/gui/game/images/menuButton.gif");
		menuButton=new VoogaButton(image, "menu", 
				new Dimension(parent.getWidth()-image.getWidth()-2, 
						parent.getHeight()-image.getHeight()-2) 
		);
		
		score.showCommas(true);
	}

	
	
	public void sendClick(double mouseX, double mouseY){
		if(menuButton.hitBy(mouseX, mouseY)){
			parent.BSOD=false;
		}
	}

	@Override
	public void update() {
		score.setNumber(parent.getScore());
		
	}

	@Override
	public void render(Graphics2D g) {
		update();
		uiBase.render(g);
		score.render(g);
		menuButton.render(g);
		
	}

}

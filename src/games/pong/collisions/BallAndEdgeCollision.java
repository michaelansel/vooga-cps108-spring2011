package games.pong.collisions;

import games.pong.PongGame;
import games.pong.sprites.Ball;
import vooga.collisions.collisionManager.boundaries.EdgeCollisionGroup;
import vooga.sprites.improvedsprites.Sprite;

public class BallAndEdgeCollision extends EdgeCollisionGroup{     
	    @Override
	    public void collidedTop (Sprite s)
	    {
	        ((Ball) s).bounceDown();
	    }


	    @Override
	    public void collidedRight (Sprite s)
	    {
	        //((Ball) s).bounceLeft();
	        s.setActive(false);
	        PongGame.eventManager.fireEvent(this, "BallExitsRight");
	        PongGame.eventManager.fireEvent(this, "DecreaseAIHealth.bounds");
	        PongGame.eventManager.fireEvent(this, "BallExits");
	    }


	    @Override
	    public void collidedLeft (Sprite s)
	    {
	    	//((Ball) s).bounceRight();
	    	s.setActive(false);
	        PongGame.eventManager.fireEvent(this, "BallExitsLeft");
	        PongGame.eventManager.fireEvent(this, "DecreasePlayerHealth.bounds");
	        PongGame.eventManager.fireEvent(this, "BallExits");	        
	    }


	    @Override
	    public void collidedBottom (Sprite s)
	    {
	       
	    	((Ball) s).bounceUp();
	    	// s.setActive(false);
	        
	       // Breakout.eventManager.fireEvent(this, "Game.BallLost");.
	    }

}
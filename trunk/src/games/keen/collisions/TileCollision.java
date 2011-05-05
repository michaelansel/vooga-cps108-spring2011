package games.keen.collisions;

import java.awt.Point;
import games.keen.sprites.Keen;
import games.keen.sprites.StaticTile;
import vooga.collisions.collisionManager.BasicCollisionGroup;
import vooga.physics.VoogaPhysicsMediator;
import vooga.util.math.Angle;

public class TileCollision extends BasicCollisionGroup<Keen, StaticTile> {
	
	@Override
	public void collided(Keen s1, StaticTile s2) {
		// TODO Auto-generated method stub
	    s1.setVerticalSpeed(-0.01);
	}

}

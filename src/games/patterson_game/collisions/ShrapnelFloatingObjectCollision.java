package games.patterson_game.collisions;

import games.patterson_game.AbstractFloatingObject;
import games.patterson_game.Explosive;
import games.patterson_game.Shrapnel;
import games.patterson_game.refactoredVooga.collisions.collisionManager.BasicCollisionGroup;
import games.patterson_game.refactoredVooga.util.buildable.components.predefined.basic.HealthC;

/**
 * Collides shrapnel with other floating objects.  Note that shrapnel cannot setoff other explosives (gameplay decision)
 * 
 * @author Andrew
 */
public class ShrapnelFloatingObjectCollision extends BasicCollisionGroup<Shrapnel, AbstractFloatingObject>
{
    @Override
    public void collided (Shrapnel shrapnel, AbstractFloatingObject floatingObject)
    {
        if(floatingObject instanceof Explosive) return; // Shrapnel cannot setoff another explosive
        double shrapnelStrength = shrapnel.getComponent(HealthC.class).getCurrent();
        floatingObject.takeDamage(shrapnelStrength);
        shrapnel.setActive(false);    
    }
}

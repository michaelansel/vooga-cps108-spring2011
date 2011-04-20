package vooga.sprites.spritebuilder.components.basic;

import java.awt.Point;
import vooga.physics.engine.NewtonianPhysicsEngine;
import vooga.physics.interfaces.IPhysicsCustomCollide;
import vooga.physics.interfaces.newtonian.INewtonianMovable;
import vooga.physics.interfaces.newtonian.INewtonianCollider;
import vooga.physics.interfaces.newtonian.INewtonianPhysical;
import vooga.physics.mediators.VoogaPhysicsMediator;
import vooga.physics.util.Velocity;
import vooga.sprites.improvedsprites.Sprite;
import vooga.sprites.spritebuilder.components.ISpriteUpdater;
import vooga.util.buildable.components.BasicComponent;
import vooga.util.math.Angle;

/**
 * Physics Component of a Sprite, extends BasicComponent and implements
 * IPhysics.
 * 
 * @author Nathan Klug
 * 
 */
public class PhysicsC extends VelocityC implements INewtonianPhysical, ISpriteUpdater{
 
    private double myMass;
    private boolean isOn;

    @Override
    protected int compareTo(BasicComponent o) {
        // TODO: do we use this to compare whether a component is more specific
        // for physics than another
        return 0;
    }

    @Override
    protected Object[] getFieldValues() {
        return new Object[]{mySprite, myMass, isOn}; //TODO: this is not going to return the field values, just the fields
    }

    @Override
    protected void setFieldValues(Object... fields) {
        super.setFieldValues(fields);
        myMass = (Double) fields[1];
        if (fields.length > 2)
            isOn = (Boolean) fields[2];
        else
            isOn = true;
    }

    @Override
    public double getMass() {
        return myMass;
    }

    @Override
    public Point getCenter() {
        return new Point((int) mySprite.getCenterX(), (int) mySprite.getCenterY());
    }


    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void turnPhysicsOnOff(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public void update(Sprite s, long elapsedTime) {
        if (isOn())//TODO: Get access to engine
            VoogaPhysicsMediator.getInstance().getEngine(null).applyWorldForces(this, elapsedTime);
    }
}

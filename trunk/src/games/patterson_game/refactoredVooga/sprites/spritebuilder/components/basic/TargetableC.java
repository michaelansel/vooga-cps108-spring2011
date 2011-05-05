package games.patterson_game.refactoredVooga.sprites.spritebuilder.components.basic;

import games.patterson_game.refactoredVooga.sprites.improvedsprites.Sprite;
import games.patterson_game.refactoredVooga.sprites.improvedsprites.interfaces.IRenderXY;
import games.patterson_game.refactoredVooga.sprites.util.targetindicators.TargetIndicator;
import games.patterson_game.refactoredVooga.util.buildable.components.BasicComponent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TargetableC extends BasicComponent implements IRenderXY
{
    
    private boolean amTargetted;
    TargetIndicator myIndicator;

    TargetableC(TargetIndicator ti){
        myIndicator = ti;
        amTargetted = false;
    }
    
    public TargetableC (BufferedImage buf, Sprite t)
    {
        this( new TargetIndicator(buf, t));
    }
    
    public TargetableC ()
    {
        super();
    }

    @Override
    protected int compareTo (BasicComponent o)
    {
        // TODO compare boolean?
        return 0;
    }
    
    @Override
    public void render (Graphics2D g)
    {
        render(g, 0, 0);
    }

    @Override
    public void render (Graphics2D g, int x, int y)
    {
        myIndicator.render(g, x, y);
    }


    @Override
    protected Object[] getFieldValues ()
    {
        return new Object[]{myIndicator, amTargetted};
    }


    @Override
    protected void setFieldValues (Object ... fields)
    {
        myIndicator = (TargetIndicator) fields[0];
        amTargetted = (Boolean) fields[1];
        
    }


    public boolean isTargetted ()
    {
        return amTargetted;
    }

    public void detarget ()
    {
        amTargetted = false;
        myIndicator.setActive(false);
    }
    
    public void target ()
    {
        amTargetted = true;
    }
    
}

package vooga.leveleditor.gui;

import java.awt.*;
import javax.swing.*;

/**
 * 
 */
public class Palette extends JPanel
{

    public static final Dimension DEFAULT_SIZE = new Dimension(220, 600);
    
    private DrawingBoard owner;

    private JLayeredPane pane;
    
    public Palette(JLayeredPane pane)
    {
        super();
        this.pane = pane;
        
        this.setPreferredSize(DEFAULT_SIZE);
        
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        this.add(new SpriteButton(pane));
        this.add(new SpriteButton(pane));
        this.add(new SpriteButton(pane));
        this.add(new SpriteButton(pane));
        this.add(new SpriteButton(pane));
    }

}

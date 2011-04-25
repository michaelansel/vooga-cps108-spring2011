package vooga.leveleditor.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

import org.w3c.dom.Element;

/**
 * A button that holds a sprite.
 * 
 */
public class SpriteButton extends JButton
{

    private ImageIcon myIcon;
    private Viewport myPane;
    private String myImageLocation;
    private Element SpriteProperties;

    public SpriteButton(Viewport pane, Element spritedata)
    {
    	myPane = pane;
    	SpriteProperties = spritedata;
    	setImageLocation();
        myIcon = new ImageIcon(myImageLocation);
        this.setIcon(myIcon);
        this.setPreferredSize(new Dimension(100, 100));
        this.addActionListener(new ClickAction());
    }

    private class ClickAction implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
        	/*
        	 * Need to create a sprite button factory
        	 */
            DraggableImage s = new DraggableImage(myIcon, myPane, SpriteProperties);
            myPane.addMouseMotionListener(s);
            myPane.addImage(s);
        }
       

    }
    
    /*
     * This method may be used by the parser in order to set the image for
     * each button as they are being created from the file. If needed, we will
     * replace this with a SpriteButtonFactory
     */
    protected void setImageLocation(){
    	myImageLocation = "src/vooga/leveleditor/images/space_ship.png";
    }

}

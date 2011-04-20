package vooga.arcade.parser;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import vooga.core.event.examples.bubblefishbob.util.resources.ResourceManager;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;

/**
 * Object containing an instance of game and information associated with it.
 * This is used to transport game and its data around
 * 
 * @author KevinWang
 *
 */
public class ArcadeGameObject {

    private Game game;
    private Dimension dimension;    
    private String[] highscores;
    private Map<String, String> dataMap = new HashMap<String, String>();
    private ResourceManager resourceManager = ResourceManager.getInstance();
    private String source = "GameInfo";
    private Image image;
    private Element root;
    private String path;
    private XMLOutputter xmlOutput = new XMLOutputter();
    
    
    public ArcadeGameObject(Game game, String[] data, String[] dimension,
            String[] highscores, Image image, Element root, String path) {
        resourceManager.addResourcesFromFile(source,
                "vooga.arcade.resources");
        this.game = game;
        this.highscores = highscores;
        this.dimension = new Dimension(Integer.parseInt(dimension[0]),
                Integer.parseInt(dimension[1]));
        this.image = image;
        this.root = root;
        this.path = path;
        fillMapWithData(data);
    }

    private void fillMapWithData(String[] data) {
        String defaultName = resourceManager.getString("defaultName");
        for (int i = 0; i < data.length; i++) {
            dataMap.put(resourceManager.getString(defaultName + '.' + i),
                    data[i]);
        }
    }

    /**
     * start the game contained in the game object
     */
    public void start() {
        GameLoader loader = new GameLoader();
        loader.setup(game, dimension, false);
        game.start();
    }

    /**
     * return data from the dataMap
     * 
     * @param name
     * @return return data with given name, or return "" if the data doesn't
     *         exist
     */
    public String getData(String name) {
        String s = dataMap.get(name);
        if (s != null)
            return s;
        return "";
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @return the dimension
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * @return the highscores
     */
    public String[] getHighscores() {
        return highscores;
    }
    
    /**
     * Allow users to dynamically add data into these object
     * @param name
     * @param value
     */
    public void putData(String name, String value){
        //TODO: figure out how to write to xml
        dataMap.put(name, value);
        
        Element modifiedElement =root.getChild(name);
        
        if(modifiedElement!=null){
            modifiedElement.setText(value);
        }
        else{
            modifiedElement = new Element(name);
            root.addContent(modifiedElement);
        }
        try {
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(root, new FileOutputStream(new File(path)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * @return the drawableData
     */
    public Image getImage() {
        return image;
    }
}
package levels.util;

import java.io.*;

import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.tools.MappingTool;
import org.exolab.castor.xml.XMLContext;

import sprites.Bomb;
import sprites.Order;
import sprites.TestSprite;

public class MappingBuilder {

	public MappingBuilder() {
		try {
			XMLContext context = new XMLContext(); 
			MappingTool tool = context.createMappingTool(); 
			tool.addClass(Order.class); 
			OutputStream file = new FileOutputStream("test.xml" ); 
			Writer writer = new OutputStreamWriter(file); 
			tool.write(writer); 
			
//			MappingTool mt = new MappingTool();
//			mt.setInternalContext(new org.castor.xml.BackwardCompatibilityContext());
//			mt.addClass(Order.class);
//			//mt.addClass(Bomb.class);
//			OutputStream file = new FileOutputStream("test.xml");
//			Writer writer = new OutputStreamWriter(file);
//			mt.write(writer);
		} catch (MappingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
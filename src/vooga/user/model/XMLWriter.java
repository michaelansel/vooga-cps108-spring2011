package vooga.user.model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import vooga.user.voogauser.VoogaUser;


public class XMLWriter {
    public static void writeXML(VoogaUser vooga, String filename) throws Exception{
        XMLEncoder encoder =
           new XMLEncoder(
              new BufferedOutputStream(
                new FileOutputStream(filename)));
        encoder.writeObject(vooga);
        encoder.close();
    }

    public static VoogaUser readXML(String filename) throws Exception {
        XMLDecoder decoder =
            new XMLDecoder(new BufferedInputStream(
                new FileInputStream(filename)));
        VoogaUser o = (VoogaUser)decoder.readObject();
        decoder.close();
        return o;
    }
}

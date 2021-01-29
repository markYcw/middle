package keda.common.gui;

import javax.swing.*;
import java.awt.*;

public class GUITools {
    public static Frame getFrame(Container applet) {
        Container owner;
        for (owner = applet; owner != null && !(owner instanceof Frame); owner = owner.getParent())
            ;
        if (owner != null)
            return (Frame) owner;
        else
            return new JFrame();
    }
}

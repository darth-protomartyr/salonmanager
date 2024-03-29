package salonmanager.entidades.graphics;

import javax.swing.*;
import java.awt.*;

public class CustomJMenuBar extends JMenuBar {

    private int preferredHeight;

    public CustomJMenuBar(int preferredHeight) {
        this.preferredHeight = preferredHeight;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.height = preferredHeight;
        return size;
    }
}
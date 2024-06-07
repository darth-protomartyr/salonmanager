package salonmanager.entidades.graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameHalfFlat extends FrameGeneral {

    public int anchoFrameHalf = anchoFrame / 2;

    public FrameHalfFlat() {
        setBounds(0, 0, alturaFrame,anchoFrameHalf);
        setVisible(true);
        setResizable(false);
        frame = this;
    }
}
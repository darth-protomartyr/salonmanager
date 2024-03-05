package salonmanager.entidades.graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameHalf extends FrameFull {

    public int anchoFrameHalf = anchoFrame / 2;

    public FrameHalf() {
        setBounds(3, 3, anchoFrameHalf, alturaFrame);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}

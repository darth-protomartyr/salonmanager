package salonmanager.entidades.graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameThird extends FrameFull {

    public int anchoFrameThird = anchoFrame / 3;

    public FrameThird() {
        setBounds(3, 3, anchoFrameThird, alturaFrame);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}

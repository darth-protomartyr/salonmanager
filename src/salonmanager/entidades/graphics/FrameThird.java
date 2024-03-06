package salonmanager.entidades.graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameThird extends FrameGeneral {

    public int anchoFrameThird = anchoFrame / 3;

    public FrameThird() {
        setVisible(true);
        setResizable(false);
        frame = this;
        setBounds(0, 0, anchoFrameThird, alturaFrame);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}

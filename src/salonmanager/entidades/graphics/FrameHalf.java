package salonmanager.entidades.graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameHalf extends FrameGeneral {

    public int anchoFrameHalf = anchoFrame / 2;

    public FrameHalf() {
        setBounds(0, 0, anchoFrameHalf, alturaFrame);
        setVisible(true);
        setResizable(false);
        frame = this;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
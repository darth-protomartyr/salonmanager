package salonmanager.entidades;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class FrameHalf extends FrameFull {

    public int anchoFrameHalf = anchoFrame / 2;

    public FrameHalf() {
        setBounds(3, 3, anchoFrameHalf, alturaFrame);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }

//            @Override
//            public void windowIconified(WindowEvent e) {
//                setState(JFrame.NORMAL);
//            }
        });
    }
}

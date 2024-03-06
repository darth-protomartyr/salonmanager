package salonmanager.entidades.graphics;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;

public class FrameFull extends FrameGeneral{   
    public FrameFull() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setVisible(true);
    }
}
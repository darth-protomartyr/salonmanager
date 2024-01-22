/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades.graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import salonmanager.SalonManager;

public class FrameFullManager extends FrameGeneral {
    SalonManager sm = new SalonManager();
    public FrameFullManager() {
        setBounds(3, 3, anchoFrame, alturaFrame);
        setVisible(true);

        // Configurar el contenido


        
        
        
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                int option = JOptionPane.showConfirmDialog(FrameFullManager.this, "¿Estás seguro de que quieres cerrar la ventana?");
//                if (option == JOptionPane.YES_OPTION) {
//                    sm.frameCloser();
//                    dispose();
//                }
//            }

//            @Override
//            public void windowIconified(WindowEvent e) {
//                setState(JFrame.NORMAL);
//            }
//        });
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import salonmanager.entidades.FrameGeneral;

public class FrameWindow extends FrameFull {

    public FrameWindow() {
        setBounds(5, 0, 390, 300);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}

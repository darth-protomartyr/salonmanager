/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import salonmanager.SalonManager;

/**
 *
 * @author Gonzalo
 */
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

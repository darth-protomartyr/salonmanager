/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager;

import java.awt.Color;
import salonmanager.entidades.FrameHalf;
import salonmanager.entidades.PanelPpal;
import salonmanager.entidades.User;
import salonmanager.servicios.ServicioItemCarta;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class ItemCartaModificacion extends FrameHalf{
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioItemCarta sic = new ServicioItemCarta();
    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(217, 103, 4);
    Color bluLg = new Color(194, 242, 206);
    Color viol = new Color(242, 29, 41);
    SalonManager sm = new SalonManager();
    User userIn = null;
    
    public ItemCartaModificacion() {        
        setTitle("Modificación Item de Carta");
        PanelPpal panelPpal = new PanelPpal(anchoFrameHalf, alturaFrame);
        add(panelPpal);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.entidades;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Gonzalo
 */
public class Session {
    int id;
    User opener;
    User closer;
    Timestamp openSession;
    Timestamp closeSession;
    ArrayList<Workshift> sessionWorkshifts;
    double totalShift;
    double errorShift;
}

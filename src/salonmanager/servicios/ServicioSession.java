/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.servicios;

import salonmanager.entidades.Session;
import salonmanager.entidades.User;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOSession;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;

/**
 *
 * @author Gonzalo
 */
public class ServicioSession {
    DAOConfig daoC = new DAOConfig();
    DAOSession daoS = new DAOSession();
    DAOUser daoU = new DAOUser(); 
    DAOWorkshift daoW = new DAOWorkshift();
    public Session crearSession(User user) throws Exception {
        Session sess = new Session(user);
        daoS.saveSession(sess);
        
        int id = daoS.findSessionId(sess.getOpenSession());
        sess.setId(id);
        daoU.saveCashierInit(sess);
//        daoW.listarWsByDate(sess);
        daoC.upOpenSession();
        daoC.upOpenSessionId(sess);

        return sess;
    }
    
    
}

package salonmanager.servicios;

import salonmanager.SalonManager;
import salonmanager.persistencia.DAORegister;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class ServicioRegister {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAORegister daoR = new DAORegister();
    SalonManager sm = new SalonManager();

//    public void crearRegistro(String operation, String object, String modification, User user) throws Exception {
//        Register rgo = new Register(user.getMail(), operation, object, modification);
//        daoR.saveTablaRegistros(rgo);
//    }
}

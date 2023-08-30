package salonmanager.servicios;


import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;
import java.util.ArrayList;


public class ServicioUser {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    DAOUser daoU = new DAOUser();

    public boolean repeatMail(String mail) throws Exception {
        boolean repeat = false;
        ArrayList<String> mails = daoU.listarUserMails();
        for (String ma : mails) {
            if (mail.equals(ma)) {
                repeat = true;
            }
        }
        return repeat;
    }
}
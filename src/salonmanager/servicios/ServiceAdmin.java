/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salonmanager.servicios;

import java.util.ArrayList;
import salonmanager.Admin;
import salonmanager.TableAdder;
import salonmanager.WorkshiftEndPanel;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOUser;
import salonmanager.utilidades.UtilidadesGraficasAdmin;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class ServiceAdmin {
    Utilidades utili = new Utilidades();
    ServicioTable st = new ServicioTable();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    DAOUser daoU = new DAOUser();
    DAOTable daoT = new DAOTable();
    
    public void selectUser(Admin admin) {
        String selection = (String) admin.getComboUsers().getSelectedItem();
        if (!selection.equals("")) {
            admin.setUserMod(utili.userSelReturn(selection, admin.getUsers()));
//            int index = 0;            
            admin.getComboRol().setSelectedItem(admin.getUserMod().getRol());
            if (admin.getUserMod().isActiveUser()) {
                admin.getComboAct().setSelectedIndex(1);
            } else {
                admin.getComboAct().setSelectedIndex(2);
            }
            admin.getLabelUserMod().setText("Usuario: " + admin.getUserMod().getName() + " " + admin.getUserMod().getLastName());
        }
    }
    

    public void updateUser(Admin admin, UtilidadesGraficasAdmin uga) throws Exception {
        String userId = admin.getUserMod().getId();
        boolean act = false;
        String rol = (String) admin.getComboRol().getSelectedItem();
        String active = (String) admin.getComboAct().getSelectedItem();
        if (!userId.equals("") && !active.equals("") && !rol.equals("")) {
            if (active.equals("alta")) {
                act = true;
            } else {
                act = false;
            }
            daoU.updateActUser(userId, act);
            daoU.updateRolUser(userId, rol);
            utiliMsg.successUserUpdate(null);
            uga.reset(admin);
        } else {
            utiliMsg.errorDataNull();
        }
    }

    public void selectTab(Admin adm) throws Exception {
        String id1 = (String) adm.getComboTabs().getSelectedItem();
        String id2 = "";
        for (int i = 0; i < adm.getDefer2().size(); i++) {
            if (id1.equals(adm.getDefer2().get(i))) {
                id2 = adm.getDefer1().get(i);
            }
        }
        adm.setTabAux(st.getCompleteTableById(id2));        
        new TableAdder(null, adm.getManager(), adm.getTabAux(), adm);
        adm.setEnabled(false);
    }

    public void selectWs(Admin adm) throws Exception {
        String id1 = (String) adm.getComboWs().getSelectedItem();
        Workshift ws = null;
        for (int i = 0; i < adm.getDefer2Ws().size(); i++) {
            if (id1.equals(adm.getDefer2Ws().get(i))) {
                ws = adm.getDefer1Ws().get(i);
            }
        }
        User cashier = daoU.getCashierByWorkshift(ws.getId());
        ws.setCashierWs(cashier);
                
        ArrayList<Table> tabs = st.listTablesByTs(ws.getOpenDateWs(), ws.getCloseDateWs(), false);
        new WorkshiftEndPanel(null, adm, adm.getManager(), ws, null, tabs, null, null, null, false, 2);
        adm.setEnabled(false);
    }


    public void selectConsumer(Admin admin) {
        String selection = (String) admin.getComboClients().getSelectedItem();
        if (!selection.equals("")) {
            admin.setCmrMod(utili.consumerSelReturn(selection, admin.getConsumers()));
            admin.getLabelConsumerMod().setText("Cliente: " + admin.getCmrMod().getName() + " " + admin.getCmrMod().getLastname());
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.persistencia;

import java.sql.SQLException;
import salonmanager.entidades.Workshift;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class DAOWorkshift extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void guardarWorkshift(Workshift ws) throws Exception {
        boolean error = false;
        String sql = "";

        if (ws.getCashier() == null) {
            error = true;
            utiliMsg.errorCashierNull();
        }

        if (error == false) {
            try {
                if (ws.getCloseShift() == null) {
                    sql = "INSERT INTO workshifts(workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount, workshift_error_mount_real, workshift_total_mount, workshift_total_mount_real) "
                            + "VALUES( '" + ws.getOpenShift() + "'," + ws.getCloseShift() + ", " + ws.isStateWorkshift() + ", " + ws.getTotalMountShiftCash() + ", " + ws.getTotalMountShiftElectronic() + ", " + ws.getErrorMountShiftReal() + ", " + ws.getTotalMountShift() + ", " + ws.getTotalMountShiftReal() + ");";
                } else {
                    sql = "INSERT INTO workshifts(workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount, workshift_error_mount_real, workshift_total_mount, workshift_total_mount_real) "
                            + "VALUES( '" + ws.getOpenShift() + "','" + ws.getCloseShift() + "', " + ws.isStateWorkshift() + ", " + ws.getTotalMountShiftCash() + ", " + ws.getTotalMountShiftElectronic() + ", " + ws.getErrorMountShift() + ", " + ws.getErrorMountShiftReal() + ", " + ws.getTotalMountShift() + ", " + ws.getTotalMountShiftReal() + ");";
                }

                System.out.println(sql);
                insertarModificarEliminar(sql);
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    utiliMsg.errorCargaDB();
                } else {
                    e.printStackTrace();
                }
            } finally {
                desconectarBase();
            }
        }
    }

//    public Workshift consultarOpenWorkshift() {
//        Workshift wsAux = new Workshift(); 
//        
//        
//        
//        
//        return wsAux;
//    }
}

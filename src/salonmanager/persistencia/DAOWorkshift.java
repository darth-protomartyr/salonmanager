/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.entidades.Table;
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
                            + "VALUES( '" + ws.getOpenShift() + "', " + ws.getCloseShift() + ", " + ws.isStateWorkshift() + ", " + ws.getTotalMountShiftCash() + ", " + ws.getTotalMountShiftElectronic() + ", " + ws.getErrorMountShift() + ", " + ws.getErrorMountShiftReal() + ", " + ws.getTotalMountShift() + ", " + ws.getTotalMountShiftReal() + ");";
                } else {
                    sql = "INSERT INTO workshifts(workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount, workshift_error_mount_real, workshift_total_mount, workshift_total_mount_real) "
                            + "VALUES( '" + ws.getOpenShift() + "', '" + ws.getCloseShift() + "', " + ws.isStateWorkshift() + ", " + ws.getTotalMountShiftCash() + ", " + ws.getTotalMountShiftElectronic() + ", " + ws.getErrorMountShift() + ", " + ws.getErrorMountShiftReal() + ", " + ws.getTotalMountShift() + ", " + ws.getTotalMountShiftReal() + ");";
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

    public void updateWorkshiftClose(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_close_shift = '" + ws.getCloseShift() + "' WHERE workshift_id = '" + ws.getId() + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateWorkshiftState(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_state_shift = " + ws.isStateWorkshift() + " WHERE workshift_id = '" + ws.getId() + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateWorkshiftTotal(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_total_mount = '" + ws.getTotalMountShift() + "' WHERE workshift_id = '" + ws.getId() + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateWorkshiftError(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error_mount = '" + ws.getErrorMountShift() + "' WHERE workshift_id = '" + ws.getId() + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateWorkshiftCash(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_mount_cash = '" + ws.getTotalMountShiftCash() + "' WHERE workshift_id = '" + ws.getId() + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public void updateWorkshiftElectronic(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_mount_electronic = '" + ws.getTotalMountShiftElectronic() + "' WHERE workshift_id = '" + ws.getId() + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
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

    public ArrayList<Workshift> consultarTurnoByDate(Timestamp ts1, Timestamp ts2) throws Exception {
        ArrayList<Workshift> wss = new ArrayList<Workshift>();
        if (ts2 == null) {
            ts2 = new Timestamp(new Date().getTime());
        }

        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_open_shift >= '" + ts1 + "' AND workshift_open_shift <= '" + ts2 + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Workshift ws = new Workshift();
                ws.setId(resultado.getInt(1));
                ws.setOpenShift(resultado.getTimestamp(2));
                ws.setCloseShift(resultado.getTimestamp(3));
                ws.setStateWorkshift(resultado.getBoolean(4));
                ws.setTotalMountShiftCash(resultado.getDouble(5));
                ws.setTotalMountShiftElectronic(resultado.getDouble(6));
                ws.setTotalMountShift(resultado.getDouble(7));
                ws.setTotalMountShiftReal(resultado.getDouble(8));
                ws.setErrorMountShift(resultado.getDouble(9));
                ws.setErrorMountShiftReal(resultado.getDouble(10));
                wss.add(ws);
            }
            return wss;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public int findId(Timestamp openShift) throws Exception {
        int id = 0;
        try {
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_open_shift >= '" + openShift + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                id = resultado.getInt(1);
            }
            return id;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }

    }
}

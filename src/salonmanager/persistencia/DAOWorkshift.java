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
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class DAOWorkshift extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveWorkshift(Workshift ws) throws Exception {
        boolean error = false;
        String sql = "";

        if (error == false) {
            try {
                if (ws.getWsClose() == null) {
                    sql = "INSERT INTO workshifts(workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount, workshift_error_mount_real, workshift_total_mount, workshift_total_mount_real, workshift_active) "
                            + "VALUES( '" + ws.getWsOpen() + "', " + ws.getWsClose() + ", " + ws.isWsState() + ", " + ws.getWsTotalMountCash() + ", " + ws.getWsTotalMountElectronic() + ", " + ws.getWsErrorMount() + ", " + ws.getWsErrorMountReal() + ", " + ws.getWsTotalMount() + ", " + ws.getWsTotalMountReal() + ", " + true + ");";
                } else {
                    sql = "INSERT INTO workshifts(workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount, workshift_error_mount_real, workshift_total_mount, workshift_total_mount_real, workshift_active) "
                            + "VALUES( '" + ws.getWsOpen() + "', '" + ws.getWsClose() + "', " + ws.isWsState() + ", " + ws.getWsTotalMountCash() + ", " + ws.getWsTotalMountElectronic() + ", " + ws.getWsErrorMount() + ", " + ws.getWsErrorMountReal() + ", " + ws.getWsTotalMount() + ", " + ws.getWsTotalMountReal() + true +");";
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

    public void updateWorkshiftClose(Workshift ws, boolean isTabs) throws Exception {
        try {
            if (isTabs = false) {
                downWorkshiftActive(ws);
            }
            
            String sql1 = "UPDATE workshifts SET workshift_close_shift = '" + ws.getWsClose() + "' WHERE workshift_id = '" + ws.getWsId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_state_shift = " + ws.isWsState() + " WHERE workshift_id = '" + ws.getWsId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_total_mount = '" + ws.getWsTotalMount() + "' WHERE workshift_id = '" + ws.getWsId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_error_mount = '" + ws.getWsErrorMount() + "' WHERE workshift_id = '" + ws.getWsId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_mount_cash = '" + ws.getWsTotalMountCash() + "' WHERE workshift_id = '" + ws.getWsId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_mount_electronic = '" + ws.getWsTotalMountElectronic() + "' WHERE workshift_id = '" + ws.getWsId() + "';";
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
    
    public void downWorkshiftActive(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_active = " + false + " WHERE workshift_id = '" + ws.getWsId() + "';";
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

    public ArrayList<Workshift> askWorshiftByDate(Timestamp ts1, Timestamp ts2) throws Exception {
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
                ws.setWsId(resultado.getInt(1));
                ws.setWsOpen(resultado.getTimestamp(2));
                ws.setWsClose(resultado.getTimestamp(3));
                ws.setWsState(resultado.getBoolean(4));
                ws.setWsTotalMountCash(resultado.getDouble(5));
                ws.setWsTotalMountElectronic(resultado.getDouble(6));
                ws.setWsTotalMount(resultado.getDouble(7));
                ws.setWsTotalMountReal(resultado.getDouble(8));
                ws.setWsErrorMount(resultado.getDouble(9));
                ws.setWsErrorMountReal(resultado.getDouble(10));
                ws.setActiveWs(resultado.getBoolean(11));
                wss.add(ws);
            }
            return wss;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
    
    
    public Workshift askWorshiftById(int id) throws Exception {
        Workshift ws = new Workshift();
        
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_id= " + id + ";";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws.setWsId(resultado.getInt(1));
                ws.setWsOpen(resultado.getTimestamp(2));
                ws.setWsClose(resultado.getTimestamp(3));
                ws.setWsState(resultado.getBoolean(4));
                ws.setWsTotalMountCash(resultado.getDouble(5));
                ws.setWsTotalMountElectronic(resultado.getDouble(6));
                ws.setWsTotalMount(resultado.getDouble(7));
                ws.setWsTotalMountReal(resultado.getDouble(8));
                ws.setWsErrorMount(resultado.getDouble(9));
                ws.setWsErrorMountReal(resultado.getDouble(10));
                ws.setActiveWs(resultado.getBoolean(11));
            }
            return ws;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
    

    public int findId(Timestamp openShift) throws Exception {
        int id = 0;
        try {
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_open_shift = '" + openShift + "';";
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
    
    
    
    public int findLastWsID() throws Exception {
        int id = 0;
        try {
            String sql = "SELECT MAX(workshift_id) FROM workshifts;";
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
    
    
    

    public void updateWorkshiftErrorReal(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error_mount_real = '" + ws.getWsErrorMountReal() + "' WHERE workshift_id = '" + ws.getWsId() + "';";
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

    public void updateWorkshiftMountReal(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_total_mount_real = '" + ws.getWsTotalMountReal() + "' WHERE workshift_id = '" + ws.getWsId() + "';";
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}

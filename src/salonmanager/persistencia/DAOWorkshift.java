/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class DAOWorkshift extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveWorkshift(Workshift ws) throws Exception {
        String sql = "";

        try {
            if (ws.getCloseWs() == null) {
                sql = "INSERT INTO workshifts(workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount, workshift_error_mount_real, workshift_total_mount, workshift_total_mount_real, workshift_cash_flow_cash, workshift_cash_flow_elec, workshift_comment, workshift_error, workshift_active) "
                        + "VALUES( '"
                        + ws.getOpenWs() + "', " 
                        + ws.getCloseWs() + ", " 
                        + ws.isStateWs() + ", "
                        + ws.getTotalMountCashWs() + ", " 
                        + ws.getTotalMountElectronicWs() + ", " 
                        + ws.getErrorMountWs() + ", "
                        + ws.getErrorMountRealWs() + ", "
                        + ws.getTotalMountWs() + ", "
                        + ws.getTotalMountRealWs() + ", "
                        + ws.getCashFlowWsCash() + ", " 
                        + ws.getCashFlowWsElec() + ", '" 
                        + ws.getCommentWs() + "', " 
                        + ws.isError() + ", " 
                        + true + ");";
            } else {
                sql = "INSERT INTO workshifts(workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount, workshift_error_mount_real, workshift_total_mount, workshift_total_mount_real, workshift_cash_flow_cash, workshift_cash_flow_elec, workshift_comment, workshift_error, workshift_active) "
                        + "VALUES( '" + ws.getOpenWs() + "', '" + ws.getCloseWs() + "', " + ws.isStateWs() + ", "
                        + ws.getTotalMountCashWs() + ", " + ws.getTotalMountElectronicWs() + ", " + ws.getErrorMountWs() + ", "
                        + ws.getErrorMountRealWs() + ", " + ws.getTotalMountWs() + ", " + ws.getTotalMountRealWs()
                        + ws.getCashFlowWsCash() + ", " + ws.getCashFlowWsElec() + ", '" + ws.getCommentWs() + "', " + ws.isError() + ", " + true + ");";
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

    public void updateWorkshiftClose(Workshift ws, boolean isTabs) throws Exception {
        try {
            if (isTabs == false) {
                downWorkshiftActive(ws);//If WS doesnt have tabs
            }
            String sql1 = "UPDATE workshifts SET workshift_close_shift = '" + ws.getCloseWs() + "' WHERE workshift_id = '" + ws.getId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_state_shift = " + ws.isStateWs() + " WHERE workshift_id = '" + ws.getId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_total_mount = '" + ws.getTotalMountWs() + "' WHERE workshift_id = '" + ws.getId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_error_mount = '" + ws.getErrorMountWs() + "' WHERE workshift_id = '" + ws.getId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_mount_cash = '" + ws.getTotalMountCashWs() + "' WHERE workshift_id = '" + ws.getId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_mount_electronic = '" + ws.getTotalMountElectronicWs() + "' WHERE workshift_id = '" + ws.getId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_active = " + false + " WHERE workshift_id = '" + ws.getId() + "';";
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

    /*
    public ArrayList<Workshift> askWorshiftByDate(Timestamp ts1, Timestamp ts2) throws Exception {
        ArrayList<Workshift> wss = new ArrayList<>();
        if (ts2 == null) {
            ts2 = new Timestamp(new Date().getTime());
        }
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_open_shift >= '" + ts1 + "' AND workshift_open_shift <= '" + ts2 + "' AND workshift_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Workshift ws = new Workshift();
                ws.setId(resultado.getInt(1));
                ws.setOpenWs(resultado.getTimestamp(2));
                ws.setCloseWs(resultado.getTimestamp(3));
                ws.setStateWs(resultado.getBoolean(4));
                ws.setTotalMountCashWs(resultado.getDouble(5));
                ws.setTotalMountElectronicWs(resultado.getDouble(6));
                ws.setTotalMountWs(resultado.getDouble(7));
                ws.setTotalMountRealWs(resultado.getDouble(8));
                ws.setErrorMountWs(resultado.getDouble(9));
                ws.setErrorMountRealWs(resultado.getDouble(10));
                ws.setCashFlowWsCash(resultado.getDouble(11));
                ws.setCashFlowWsElec(resultado.getDouble(12));
                ws.setCommentWs(resultado.getString(13));
                ws.setActiveWs(resultado.getBoolean(14));
                wss.add(ws);
            }
            return wss;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }  
     */
    public Workshift askWorshiftByTabDate(Timestamp ts1) throws Exception {
        Workshift ws = new Workshift();
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_open_shift <= '" + ts1 + "' AND workshift_close_shift >= '" + ts1 + "' AND workshift_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws.setId(resultado.getInt(1));
                ws.setOpenWs(resultado.getTimestamp(2));
                ws.setCloseWs(resultado.getTimestamp(3));
                ws.setStateWs(resultado.getBoolean(4));
                ws.setTotalMountCashWs(resultado.getDouble(5));
                ws.setTotalMountElectronicWs(resultado.getDouble(6));
                ws.setTotalMountWs(resultado.getDouble(7));
                ws.setTotalMountRealWs(resultado.getDouble(8));
                ws.setErrorMountWs(resultado.getDouble(9));
                ws.setErrorMountRealWs(resultado.getDouble(10));
                ws.setCashFlowWsCash(resultado.getDouble(11));
                ws.setCashFlowWsElec(resultado.getDouble(12));
                ws.setCommentWs(resultado.getString(13));
                ws.setError(resultado.getBoolean(14));
                ws.setActiveWs(resultado.getBoolean(15));
            }
            return ws;
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
                ws.setId(resultado.getInt(1));
                ws.setOpenWs(resultado.getTimestamp(2));
                ws.setCloseWs(resultado.getTimestamp(3));
                ws.setStateWs(resultado.getBoolean(4));
                ws.setTotalMountCashWs(resultado.getDouble(5));
                ws.setTotalMountElectronicWs(resultado.getDouble(6));
                ws.setTotalMountWs(resultado.getDouble(7));
                ws.setTotalMountRealWs(resultado.getDouble(8));
                ws.setErrorMountWs(resultado.getDouble(9));
                ws.setErrorMountRealWs(resultado.getDouble(10));
                ws.setCashFlowWsCash(resultado.getDouble(11));
                ws.setCashFlowWsElec(resultado.getDouble(12));
                ws.setCommentWs(resultado.getString(13));
                ws.setActiveWs(resultado.getBoolean(14));
                ws.setActiveWs(resultado.getBoolean(15));
            }
            return ws;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public int askWorshiftActualId() throws Exception {
        int ws = 0;
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_state_shift= true AND workshift_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws = resultado.getInt(1);
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
            String sql1 = "UPDATE workshifts SET workshift_error_mount_real = " + ws.getErrorMountRealWs() + " WHERE workshift_id = '" + ws.getId() + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_total_mount_real = " + ws.getTotalMountRealWs() + " WHERE workshift_id = '" + ws.getId() + "';";
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

    public void updateWorkshiftCashFlowCash(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_cash_flow_cash = " + ws.getCashFlowWsCash() + " WHERE workshift_id = '" + ws.getId() + "';";
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

    public void updateWorkshiftCashFlowElec(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_cash_flow_elec = " + ws.getCashFlowWsElec() + " WHERE workshift_id = '" + ws.getId() + "';";
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

    public void updateWorkshiftComment(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_comment = '" + ws.getCommentWs() + "' WHERE workshift_id = '" + ws.getId() + "';";
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

    public void updateWorkshiftErrorBool(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error = " + ws.isError() + " WHERE workshift_id = " + ws.getId() + ";";
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

    public ArrayList<Integer> listIdByDate(Timestamp tsInit, Timestamp tsEnd) throws Exception {
        ArrayList<Integer> wss = new ArrayList<>();
        try {
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_open_shift >= '" + tsInit + "' AND workshift_open_shift <= '" + tsEnd + "' AND workshift_state_shift = false AND workshift_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int wsId = 0;
                wsId = resultado.getInt(1);
                wss.add(wsId);
            }
            return wss;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Integer> listIdWs() throws Exception {
        ArrayList<Integer> wssId = new ArrayList<>();
        try {
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_state_shift = false AND workshift_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int wsId = resultado.getInt(1);
                wssId.add(wsId);
            }
            return wssId;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Timestamp> listTsIWs() throws Exception {
        ArrayList<Timestamp> wssTs = new ArrayList<>();
        try {
            String sql = "SELECT workshift_open_shift FROM workshifts WHERE workshift_state_shift = false AND workshift_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Timestamp wsTs = resultado.getTimestamp(1);
                wssTs.add(wsTs);
            }
            return wssTs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Integer> listarErrorId() throws Exception {
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_error = true AND workshift_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int wsId = 0;
                wsId = resultado.getInt(1);
                ids.add(wsId);
            }
            return ids;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}

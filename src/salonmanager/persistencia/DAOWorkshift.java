/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOWorkshift extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveWorkshift(Workshift ws) throws Exception {
        String sql = "";
        if (ws.getId() == 0) {
            int id = getNewWorkshiftId();
            ws.setId(id);
        }

        String comment = ws.getCommentWs();
        if (comment.equals("<br>")) {
            ws.setCommentWs("");
        }

        try {
            if (ws.getCloseDateWs() == null) {
                sql = "INSERT INTO workshifts(workshift_id, workshift_open_time_shift, workshift_close_time_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount_tabs, workshift_error_mount_ws, workshift_total_mount_tabs, workshift_total_mount_ws, workshift_cash_flow_cash, workshift_cash_flow_elec, workshift_comment, workshift_error, workshift_active) "
                        + "VALUES( '"
                        + SalonManager.encryptInt(ws.getId()) + "', '"
                        + ws.getOpenDateWs() + "', "
                        + ws.getCloseDateWs() + ", '"
                        + SalonManager.encryptBoolean(ws.isStateWs()) + "', '"
                        + SalonManager.encryptDouble(ws.getTotalMountCashWs()) + "', '"
                        + SalonManager.encryptDouble(ws.getTotalMountElectronicWs()) + "', '"
                        + SalonManager.encryptDouble(ws.getErrorMountTabs()) + "', '"
                        + SalonManager.encryptDouble(ws.getErrorMountWs()) + "', '"
                        + SalonManager.encryptDouble(ws.getTotalMountTabs()) + "', '"
                        + SalonManager.encryptDouble(ws.getTotalMountWs()) + "', '"
                        + SalonManager.encryptDouble(ws.getMoneyFlowWsCash()) + "', '"
                        + SalonManager.encryptDouble(ws.getMoneyFlowWsElec()) + "', '"
                        + SalonManager.encrypt(ws.getCommentWs()) + "', '"
                        + SalonManager.encryptBoolean(ws.isError()) + "', '"
                        + SalonManager.encryptBoolean(true) + "');";
            } else {
                sql = "INSERT INTO workshifts(workshift_open_time_shift, workshift_close_time_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount_tabs, workshift_error_mount_ws, workshift_total_mount_tabs, workshift_total_mount_ws, workshift_cash_flow_cash, workshift_cash_flow_elec, workshift_comment, workshift_error, workshift_active) "
                        + "VALUES( '" + SalonManager.encryptInt(ws.getId()) + "', '" + ws.getOpenDateWs() + "', '" + ws.getCloseDateWs() + "', '" + SalonManager.encryptBoolean(ws.isStateWs()) + "', '"
                        + SalonManager.encryptDouble(ws.getTotalMountCashWs()) + "', '" + SalonManager.encryptDouble(ws.getTotalMountElectronicWs()) + "', '" + SalonManager.encryptDouble(ws.getErrorMountTabs()) + "', '"
                        + SalonManager.encryptDouble(ws.getErrorMountWs()) + "', '" + SalonManager.encryptDouble(ws.getTotalMountTabs()) + "', '" + SalonManager.encryptDouble(ws.getTotalMountWs())
                        + SalonManager.encryptDouble(ws.getMoneyFlowWsCash()) + "', '" + SalonManager.encryptDouble(ws.getMoneyFlowWsElec()) + "', '" + SalonManager.encrypt(ws.getCommentWs()) + "', '" + SalonManager.encryptBoolean(ws.isError()) + "', '" + SalonManager.encryptBoolean(true) + "');";
            }

            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftClose(Workshift ws, boolean isTabs) throws Exception {
        try {
            if (isTabs == false) {
                downWorkshiftActive(ws);//If WS doesnt have tabs
            }
            String sql1 = "UPDATE workshifts SET workshift_close_time_shift = '" + ws.getCloseDateWs() + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftState(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_state_shift = '" + SalonManager.encryptBoolean(ws.isStateWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftTabs(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_total_mount_tabs = '" + SalonManager.encryptDouble(ws.getTotalMountTabs()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftErrorMountTabs(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error_mount_tabs = '" + SalonManager.encryptDouble(ws.getErrorMountTabs()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftError(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error = '" + SalonManager.encryptBoolean(ws.isError()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftCash(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_mount_cash = '" + SalonManager.encryptDouble(ws.getTotalMountCashWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftElectronic(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_mount_electronic = '" + SalonManager.encryptDouble(ws.getTotalMountElectronicWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void downWorkshiftActive(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_active = '" + SalonManager.encryptBoolean(false) + "' WHERE workshift_id = '" + SalonManager.encryptDouble(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public Workshift askWorshiftByTabDate(Timestamp ts1) throws Exception {
        Workshift ws = new Workshift();
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_open_time_shift <= '" + ts1 + "' AND workshift_close_time_shift >= '" + ts1 + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws.setId(SalonManager.decryptInt(resultado.getString(1)));
                ws.setOpenDateWs(resultado.getTimestamp(2));
                ws.setCloseDateWs(resultado.getTimestamp(3));
                ws.setStateWs(SalonManager.decryptBoolean(resultado.getString(4)));
                ws.setTotalMountCashWs(SalonManager.decryptDouble(resultado.getString(5)));
                ws.setTotalMountElectronicWs(SalonManager.decryptDouble(resultado.getString(6)));
                ws.setTotalMountTabs(SalonManager.decryptDouble(resultado.getString(7)));
                ws.setTotalMountWs(SalonManager.decryptDouble(resultado.getString(8)));
                ws.setErrorMountTabs(SalonManager.decryptDouble(resultado.getString(9)));
                ws.setErrorMountWs(SalonManager.decryptDouble(resultado.getString(10)));
                ws.setMoneyFlowWsCash(SalonManager.decryptDouble(resultado.getString(11)));
                ws.setMoneyFlowWsElec(SalonManager.decryptDouble(resultado.getString(12)));
                ws.setCommentWs(SalonManager.decrypt(resultado.getString(13)));
                ws.setError(SalonManager.decryptBoolean(resultado.getString(14)));
                ws.setActiveWs(SalonManager.decryptBoolean(resultado.getString(15)));
            }
            return ws;
        } catch (Exception e) {
            throw e;
        }
    }

    public Workshift askWorshiftById(int id) throws Exception {
        Workshift ws = new Workshift();
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_id= '" + SalonManager.encryptInt(id) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws.setId(SalonManager.decryptInt(resultado.getString(1)));
                ws.setOpenDateWs(resultado.getTimestamp(2));
                ws.setCloseDateWs(resultado.getTimestamp(3));
                ws.setStateWs(SalonManager.decryptBoolean(resultado.getString(4)));
                ws.setTotalMountCashWs(SalonManager.decryptDouble(resultado.getString(5)));
                ws.setTotalMountElectronicWs(SalonManager.decryptDouble(resultado.getString(6)));
                ws.setTotalMountTabs(SalonManager.decryptDouble(resultado.getString(7)));
                ws.setTotalMountWs(SalonManager.decryptDouble(resultado.getString(8)));
                ws.setErrorMountTabs(SalonManager.decryptDouble(resultado.getString(9)));
                ws.setErrorMountWs(SalonManager.decryptDouble(resultado.getString(10)));
                ws.setMoneyFlowWsCash(SalonManager.decryptDouble(resultado.getString(11)));
                ws.setMoneyFlowWsElec(SalonManager.decryptDouble(resultado.getString(12)));
                ws.setCommentWs(SalonManager.decrypt(resultado.getString(13)));
                ws.setError(SalonManager.decryptBoolean(resultado.getString(14)));
                ws.setActiveWs(SalonManager.decryptBoolean(resultado.getString(15)));
            }
            return ws;
        } catch (Exception e) {
            throw e;
        }
    }

    public int askWorshiftActualId() throws Exception {
        int ws = 0;
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_state_shift= '" + SalonManager.encryptBoolean(true) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws = SalonManager.decryptInt(resultado.getString(1));
            }
            return ws;
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateWorkshiftErrorWs(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error_mount_ws = '" + SalonManager.encryptDouble(ws.getErrorMountWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftMountWs(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_total_mount_ws = '" + SalonManager.encryptDouble(ws.getTotalMountWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftMoneyFlowCash(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_cash_flow_cash = '" + SalonManager.encryptDouble(ws.getMoneyFlowWsCash()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftMoneyFlowElec(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_cash_flow_elec = '" + SalonManager.encryptDouble(ws.getMoneyFlowWsElec()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftComment(Workshift ws) throws Exception {
        if (ws.getCommentWs().equals("<br>")) {
            ws.setCommentWs("");
        }
        try {
            String sql1 = "UPDATE workshifts SET workshift_comment = '" + SalonManager.encrypt(ws.getCommentWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void updateWorkshiftErrorBool(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error = '" + SalonManager.encryptBoolean(ws.isError()) + "' WHERE workshift_id = '" + SalonManager.encryptInt(ws.getId()) + "';";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Integer> listIdByDate(Timestamp tsInit, Timestamp tsEnd) throws Exception {
        ArrayList<Integer> wss = new ArrayList<>();
        try {
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_open_time_shift >= '" + tsInit + "' AND workshift_open_time_shift <= '" + tsEnd + "' AND workshift_state_shift = '" + SalonManager.encryptBoolean(false) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int wsId = 0;
                wsId = SalonManager.decryptInt(resultado.getString(1));
                wss.add(wsId);
            }
            return wss;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Integer> listIdWs() throws Exception {
        ArrayList<Integer> wssId = new ArrayList<>();
        try {
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_state_shift = '" + SalonManager.encryptBoolean(false) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int wsId = SalonManager.decryptInt(resultado.getString(1));
                wssId.add(wsId);
            }
            return wssId;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Timestamp> listTsIWs() throws Exception {
        ArrayList<Timestamp> wssTs = new ArrayList<>();
        try {
            String sql = "SELECT workshift_open_time_shift FROM workshifts WHERE workshift_state_shift = '" + SalonManager.encryptBoolean(false) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Timestamp wsTs = resultado.getTimestamp(1);
                wssTs.add(wsTs);
            }
            return wssTs;
        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<Workshift> listarErrorId() throws Exception {
        ArrayList<Workshift> wsError = new ArrayList<>();
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_error = '" + SalonManager.encryptBoolean(true) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Workshift ws = new Workshift();
                ws.setId(SalonManager.decryptInt(resultado.getString(1)));
                ws.setOpenDateWs(resultado.getTimestamp(2));
                ws.setCloseDateWs(resultado.getTimestamp(3));
                ws.setStateWs(SalonManager.decryptBoolean(resultado.getString(4)));
                ws.setTotalMountCashWs(SalonManager.decryptDouble(resultado.getString(5)));
                ws.setTotalMountElectronicWs(SalonManager.decryptDouble(resultado.getString(6)));
                ws.setTotalMountTabs(SalonManager.decryptDouble(resultado.getString(7)));
                ws.setTotalMountWs(SalonManager.decryptDouble(resultado.getString(8)));
                ws.setErrorMountTabs(SalonManager.decryptDouble(resultado.getString(9)));
                ws.setErrorMountWs(SalonManager.decryptDouble(resultado.getString(10)));
                ws.setMoneyFlowWsCash(SalonManager.decryptDouble(resultado.getString(11)));
                ws.setMoneyFlowWsElec(SalonManager.decryptDouble(resultado.getString(12)));
                ws.setCommentWs(SalonManager.decrypt(resultado.getString(13)));
                ws.setError(SalonManager.decryptBoolean(resultado.getString(14)));
                ws.setActiveWs(SalonManager.decryptBoolean(resultado.getString(15)));
                wsError.add(ws);
            }
            return wsError;
        } catch (Exception e) {
            throw e;
        }
    }

    public int getNewWorkshiftId() throws Exception {
        try {
            int id = 0;
            String sql = "SELECT COUNT(*) AS cantidad_filas FROM workshifts;";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<String> cmrs = new ArrayList<>();
            while (resultado.next()) {
                id = resultado.getInt(1) + 1;
            }
            return id;
        } catch (Exception e) {
            throw e;
        }
    }

    public Timestamp askInitWsDateById(int openIdWs) throws Exception {
        try {
            String sql = "SELECT workshift_open_time_shift FROM workshifts WHERE workshift_id = '" + SalonManager.encryptInt(openIdWs) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            Timestamp ts = null;
            while (resultado.next()) {
                ts = resultado.getTimestamp(1);
            }
            return ts;
        } catch (Exception e) {
            throw e;
        }
    }
}

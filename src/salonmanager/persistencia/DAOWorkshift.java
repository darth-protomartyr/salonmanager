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
            int id = getWorkshiftId();
            ws.setId(id);
        }
        
        String comment = ws.getCommentWs();
        if (comment.equals("<br>")) {
            ws.setCommentWs("");
        }

        try {
            if (ws.getCloseWs() == null) {
                sql = "INSERT INTO workshifts(workshift_id, workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount_tabs, workshift_error_mount_ws, workshift_total_mount_tabs, workshift_total_mount_ws, workshift_cash_flow_cash, workshift_cash_flow_elec, workshift_comment, workshift_error, workshift_active) "
                        + "VALUES( '"
                        + SalonManager.encryptInteger(ws.getId()) + "', '"
                        + SalonManager.encryptTs(ws.getOpenWs()) + "', '"
                        + SalonManager.encryptTs(ws.getCloseWs()) + "', '"
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
                sql = "INSERT INTO workshifts(workshift_open_shift, workshift_close_shift, workshift_state_shift, workshift_mount_cash, workshift_mount_electronic, workshift_error_mount_tabs, workshift_error_mount_ws, workshift_total_mount_tabs, workshift_total_mount_ws, workshift_cash_flow_cash, workshift_cash_flow_elec, workshift_comment, workshift_error, workshift_active) "
                        + "VALUES( '" + SalonManager.encryptTs(ws.getOpenWs()) + "', '" + SalonManager.encryptTs(ws.getCloseWs()) + "', '" + SalonManager.encryptBoolean(ws.isStateWs()) + "', '"
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
        } finally {
            desconectarBase();
        }
    }

    public void updateWorkshiftClose(Workshift ws, boolean isTabs) throws Exception {
        try {
            if (isTabs == false) {
                downWorkshiftActive(ws);//If WS doesnt have tabs
            }
            String sql1 = "UPDATE workshifts SET workshift_close_shift = '" + SalonManager.encryptTs(ws.getCloseWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_state_shift = '" + SalonManager.encryptBoolean(ws.isStateWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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

    public void updateWorkshiftTabs(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_total_mount_tabs = '" + SalonManager.encryptDouble(ws.getTotalMountTabs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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

    public void updateWorkshiftErrorMountTabs(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error_mount_tabs = '" + SalonManager.encryptDouble(ws.getErrorMountTabs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_error = '" + SalonManager.encryptBoolean(ws.isError()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_mount_cash = '" + SalonManager.encryptDouble(ws.getTotalMountCashWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_mount_electronic = '" + SalonManager.encryptDouble(ws.getTotalMountElectronicWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_active = '" + SalonManager.encryptBoolean(false) + "' WHERE workshift_id = '" + SalonManager.encryptDouble(ws.getId()) + "';";
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

    public Workshift askWorshiftByTabDate(Timestamp ts1) throws Exception {
        Workshift ws = new Workshift();
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_open_shift <= '" + SalonManager.encryptTs(ts1) + "' AND workshift_close_shift >= '" + SalonManager.encryptTs(ts1) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws.setId(SalonManager.decryptInteger(resultado.getString(1)));
                String create1 = resultado.getString(2);
                if (create1 == null) {
                    create1 = "";
                }
                ws.setOpenWs(SalonManager.decryptTs(create1));
                String create2 = resultado.getString(3);
                if (create2 == null) {
                    create2 = "";
                }
                ws.setCloseWs(SalonManager.decryptTs(create2));
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
        } finally {
            desconectarBase();
        }
    }

    public Workshift askWorshiftById(int id) throws Exception {
        Workshift ws = new Workshift();
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_id= '" + SalonManager.encryptInteger(id) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws.setId(SalonManager.decryptInteger(resultado.getString(1)));
                String create1 = resultado.getString(2);
                if (create1 == null) {
                    create1 = "";
                }
                ws.setOpenWs(SalonManager.decryptTs(create1));
                String create2 = resultado.getString(3);
                if (create2 == null) {
                    create2 = "";
                }
                ws.setCloseWs(SalonManager.decryptTs(create2));
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
        } finally {
            desconectarBase();
        }
    }

    public int askWorshiftActualId() throws Exception {
        int ws = 0;
        try {
            String sql = "SELECT * FROM workshifts WHERE workshift_state_shift= '" + SalonManager.encryptBoolean(true) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                ws = SalonManager.decryptInteger(resultado.getString(1));
            }
            return ws;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

//    public int findId(Timestamp openShift) throws Exception {
//        int id = 0;
//        try {
//            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_open_shift = '" + SalonManager.encryptTs(openShift) + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            while (resultado.next()) {
//                id = SalonManager.decryptInteger(resultado.getString(1));
//            }
//            return id;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            desconectarBase();
//        }
//    }

//    public int findLastWsID() throws Exception {
//        int id = 0;
//        try {
//            String sql = "SELECT MAX(workshift_id) FROM workshifts;";
//            System.out.println(sql);
//            consultarBase(sql);
//            while (resultado.next()) {
//                id = resultado.getInt(1);
//            }
//            return id;
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            desconectarBase();
//        }
//    }

    public void updateWorkshiftErrorWs(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_error_mount_ws = '" + SalonManager.encryptDouble(ws.getErrorMountWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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

    public void updateWorkshiftMountWs(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_total_mount_ws = '" + SalonManager.encryptDouble(ws.getTotalMountWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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

    public void updateWorkshiftMoneyFlowCash(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_cash_flow_cash = '" + SalonManager.encryptDouble(ws.getMoneyFlowWsCash()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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

    public void updateWorkshiftMoneyFlowElec(Workshift ws) throws Exception {
        try {
            String sql1 = "UPDATE workshifts SET workshift_cash_flow_elec = '" + SalonManager.encryptDouble(ws.getMoneyFlowWsElec()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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
        if (ws.getCommentWs().equals("<br>")) {
            ws.setCommentWs("");
        }
        try {
            String sql1 = "UPDATE workshifts SET workshift_comment = '" + SalonManager.encrypt(ws.getCommentWs()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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
            String sql1 = "UPDATE workshifts SET workshift_error = '" +SalonManager.encryptBoolean(ws.isError()) + "' WHERE workshift_id = '" + SalonManager.encryptInteger(ws.getId()) + "';";
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
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_open_shift >= '" + SalonManager.encryptTs(tsInit) + "' AND workshift_open_shift <= '" + SalonManager.encryptTs(tsEnd) + "' AND workshift_state_shift = '" + SalonManager.encryptBoolean(false) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int wsId = 0;
                wsId = SalonManager.decryptInteger(resultado.getString(1));
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
            String sql = "SELECT workshift_id FROM workshifts WHERE workshift_state_shift = '" + SalonManager.encryptBoolean(false) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int wsId = SalonManager.decryptInteger(resultado.getString(1));
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
            String sql = "SELECT workshift_open_shift FROM workshifts WHERE workshift_state_shift = '" + SalonManager.encryptBoolean(false) + "' AND workshift_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                String create1 = resultado.getString(1);
                if (create1 == null) {
                    create1 = "";
                }
                Timestamp wsTs = SalonManager.decryptTs(create1);
                wssTs.add(wsTs);
            }
            return wssTs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
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
                ws.setId(SalonManager.decryptInteger(resultado.getString(1)));
                String create1 = resultado.getString(2);
                if (create1 == null) {
                    create1 = "";
                }
                ws.setOpenWs(SalonManager.decryptTs(create1));
                String create2 = resultado.getString(3);
                if (create2 == null) {
                    create2 = "";
                }
                ws.setCloseWs(SalonManager.decryptTs(create2));
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
        } finally {
            desconectarBase();
        }
    }

    public int getWorkshiftId() throws Exception {
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
        } finally {
            desconectarBase();
        }
    }
}

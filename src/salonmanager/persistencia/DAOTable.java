package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.Table;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOTable extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public boolean saveTable(Table tab, Timestamp ts) throws Exception {
        boolean done = false;
        if (tab.getComments().equals("<br>")) {
            tab.setComments("");
        }
        boolean error = false;
        if (ts != null) {
            Timestamp close = new Timestamp(new Date().getTime());
            ArrayList<Timestamp> wsTs = listarTabsTsActive();
            if (!wsTs.isEmpty()) {
                wsTs = utili.tsFilter(wsTs, ts, close);
                for (int i = 0; i < wsTs.size(); i++) {
                    if (wsTs.get(i).equals(tab.getOpenTime())) {
                        error = true;
//                        done = true;
                    }
                }
            }
        } else {
            error = true;
        }

        if (tab.getNum() == 0) {
            utiliMsg.errorIngresoNum();
            error = true;
        }

        if (tab.getPos().equals("") || tab.getPos() == null) {
            utiliMsg.errorIngresoPos();
            error = true;
        }

        if (tab.getOpenTime() == null) {
            utiliMsg.errorIngresoOpenTime();
            error = true;
        }

        if (tab.getId().equals("")) {
            utiliMsg.errorIngresoId();
            error = true;
        }

        if (error == false) {
            String sql = "";
            try {
                if (!tab.getId().equals("") && tab.getId() != null) {
                    if (tab.getCloseTime() == null) {
                        sql = "INSERT INTO tabs(table_num, table_pos, table_open_time, table_close_time, table_id, table_open, table_bill, table_to_pay, table_discount, table_error, table_price_correction, table_amount_cash, table_amount_electronic, table_total, table_comments, table_active) "
                                + "VALUES( '" + SalonManager.encryptInt(tab.getNum()) + "', '" + SalonManager.encrypt(tab.getPos()) + "', '" + SalonManager.encryptTs(tab.getOpenTime()) + "', '" + SalonManager.encryptTs(tab.getCloseTime()) + "', '" + SalonManager.encrypt(tab.getId()) + "', '" + SalonManager.encryptBoolean(tab.isOpen()) + "', '" + SalonManager.encryptBoolean(tab.isBill()) + "', '" + SalonManager.encryptBoolean(tab.isToPay()) + "', '" + SalonManager.encryptInt(tab.getDiscount()) + "', '" + SalonManager.encryptDouble(tab.getError()) + "', '" + SalonManager.encryptDouble(tab.getPriceCorrection()) + "', '" + SalonManager.encryptDouble(tab.getAmountCash()) + "', '" + SalonManager.encryptDouble(tab.getAmountElectronic()) + "', '" + SalonManager.encryptDouble(tab.getTotal()) + "', '" + SalonManager.encrypt(tab.getComments()) + "', '" + SalonManager.encryptBoolean(tab.isActiveTable()) + "');";
                    } else {
                        sql = "INSERT INTO tabs(table_num, table_pos, table_open_time, table_close_time, table_id, table_open, table_bill, table_to_pay, table_discount, table_error, table_price_correction, table_amount_cash, table_amount_electronic, table_total, table_comments, table_active) "
                                + "VALUES( '" + SalonManager.encryptInt(tab.getNum()) + "', '" + SalonManager.encrypt(tab.getPos()) + "', '" + SalonManager.encryptTs(tab.getOpenTime()) + "', '" + SalonManager.encryptTs(tab.getCloseTime()) + "', '" + SalonManager.encrypt(tab.getId()) + "', '" + SalonManager.encryptBoolean(tab.isOpen()) + "', '" + SalonManager.encryptBoolean(tab.isBill()) + "', '" + SalonManager.encryptBoolean(tab.isToPay()) + "', '" + SalonManager.encryptInt(tab.getDiscount()) + "', '" + SalonManager.encryptDouble(tab.getError()) + "', '" + SalonManager.encryptDouble(tab.getPriceCorrection()) + "', '" + SalonManager.encryptDouble(tab.getAmountCash()) + "', '" + SalonManager.encryptDouble(tab.getAmountElectronic()) + "', '" + SalonManager.encryptDouble(tab.getTotal()) + "', '" + SalonManager.encrypt(tab.getComments()) + "', '" + SalonManager.encryptBoolean(tab.isActiveTable()) + "');";
                    }
                    done = true;
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
        
        return done;
    }

    public void updateTableTotal(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_total = '" + SalonManager.encryptDouble(tab.getTotal()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateTableBill(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_bill = '" + SalonManager.encryptBoolean(tab.isBill()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateTableOpen(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_open = '" + SalonManager.encryptBoolean(tab.isOpen()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateTableDiscount(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_discount = '" + SalonManager.encryptInt(tab.getDiscount()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateTableMountCash(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_amount_cash = '" + SalonManager.encryptDouble(tab.getAmountCash()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateTableMountElectronic(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_amount_electronic = '" + SalonManager.encryptDouble(tab.getAmountElectronic()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateToPay(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_to_pay = '" + SalonManager.encryptBoolean(tab.isToPay()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateError(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_error = '" + SalonManager.encryptDouble(tab.getError()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateCloseTime(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_close_time = '" + SalonManager.encryptTs(tab.getCloseTime()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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

    public void updateComments(Table tab) throws Exception {
        if (tab.getComments().equals("<br>")) {
            tab.setComments("");
        }
        if (!tab.getComments().equals("Ingrese un comentario(opcional): ") || !tab.getComments().equals("Causa del Error (obligatorio): ")) {
            try {
                String sql1 = "UPDATE tabs SET table_comments = '" + SalonManager.encrypt(tab.getComments()) + "' WHERE table_id = '" + SalonManager.encrypt(tab.getId()) + "';";
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
    }

//    public ArrayList<Table> listarTablesByWorkshift(Workshift ws) throws Exception {
//        ArrayList<Table> tables = new ArrayList<>();
//        Timestamp open = ws.getOpenDateWs();
//        Timestamp close = ws.getCloseDateWs();
//        if (close == null) {
//            close = new Timestamp(new Date().getTime());
//        }
//        
//        try {
//            String sql = "SELECT * FROM tabs WHERE table_open_time >= '" + SalonManager.encryptTs(open) + "' AND table_open_time <= '" + SalonManager.encryptTs(close) + "' AND table_active = '" + SalonManager.encryptBoolean(true) + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            while (resultado.next()) {
//                Table tab = new Table();
//                tab.setNum(SalonManager.decryptInt(resultado.getString(1)));
//                tab.setPos(SalonManager.decrypt(resultado.getString(2)));
//                tab.setOpenTime(SalonManager.decryptTs(resultado.getString(3)));
//                tab.setCloseTime(SalonManager.decryptTs(resultado.getString(4)));
//                tab.setId(SalonManager.decrypt(resultado.getString(5)));
//                tab.setOpen(SalonManager.decryptBoolean(resultado.getString(6)));
//                tab.setBill(SalonManager.decryptBoolean(resultado.getString(7)));
//                tab.setToPay(SalonManager.decryptBoolean(resultado.getString(8)));
//                tab.setDiscount(SalonManager.decryptInt(resultado.getString(9)));
//                tab.setError(SalonManager.decryptDouble(resultado.getString(10)));
//                tab.setPriceCorrection(SalonManager.decryptDouble(resultado.getString(11)));
//                tab.setAmountCash(SalonManager.decryptDouble(resultado.getString(12)));
//                tab.setAmountElectronic(SalonManager.decryptDouble(resultado.getString(13)));
//                tab.setTotal(SalonManager.decryptDouble(resultado.getString(14)));
//                tab.setComments(SalonManager.decrypt(resultado.getString(15)));
//                tab.setActiveTable(SalonManager.decryptBoolean(resultado.getString(16)));
//                tables.add(tab);
//            }
//            return tables;
//        } catch (Exception e) {
//            throw e;
//        }  finally {
//            desconectarBase();
//        }
//    }
//    public ArrayList<Table> listarTablesOpenByWorkshift(Workshift ws) throws Exception {
//        ArrayList<Table> tables = new ArrayList<>();
//        Timestamp open = ws.getOpenDateWs();
//        Timestamp close = ws.getCloseDateWs();
//        if (close == null) {
//            close = new Timestamp(new Date().getTime());
//        }
//
//        try {
//            String sql = "SELECT * FROM tabs WHERE table_open_time >= '" + SalonManager.encryptTs(open) + "' AND table_open_time <= '" + SalonManager.encryptTs(close) + "' AND table_open = '" + SalonManager.encryptBoolean(true) + "' AND table_active = '" + SalonManager.encryptBoolean(true) + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            while (resultado.next()) {
//                Table tab = new Table();
//                tab.setNum(SalonManager.decryptInt(resultado.getString(1)));
//                tab.setPos(SalonManager.decrypt(resultado.getString(2)));
//                tab.setOpenTime(SalonManager.decryptTs(resultado.getString(3)));
//                tab.setCloseTime(SalonManager.decryptTs(resultado.getString(4)));
//                tab.setId(SalonManager.decrypt(resultado.getString(5)));
//                tab.setOpen(SalonManager.decryptBoolean(resultado.getString(6)));
//                tab.setBill(SalonManager.decryptBoolean(resultado.getString(7)));
//                tab.setToPay(SalonManager.decryptBoolean(resultado.getString(8)));
//                tab.setDiscount(SalonManager.decryptInt(resultado.getString(9)));
//                tab.setError(SalonManager.decryptDouble(resultado.getString(10)));
//                tab.setPriceCorrection(SalonManager.decryptDouble(resultado.getString(11)));
//                tab.setAmountCash(SalonManager.decryptDouble(resultado.getString(12)));
//                tab.setAmountElectronic(SalonManager.decryptDouble(resultado.getString(13)));
//                tab.setTotal(SalonManager.decryptDouble(resultado.getString(14)));
//                tab.setComments(SalonManager.decrypt(resultado.getString(15)));
//                tab.setActiveTable(SalonManager.decryptBoolean(resultado.getString(16)));
//                tables.add(tab);
//            }
//            return tables;
//        } catch (Exception e) {
//            throw e;
//        }  finally {
//            desconectarBase();
//        }
//    }
    public Table getTableById(String st) throws Exception {
        Table tab = new Table();
        try {
            String sql = "SELECT * FROM tabs WHERE table_id = '" + SalonManager.encrypt(st) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                tab.setNum(SalonManager.decryptInt(resultado.getString(1)));
                tab.setPos(SalonManager.decrypt(resultado.getString(2)));
                tab.setOpenTime(SalonManager.decryptTs(resultado.getString(3)));
                tab.setCloseTime(SalonManager.decryptTs(resultado.getString(4)));
                tab.setId(SalonManager.decrypt(resultado.getString(5)));
                tab.setOpen(SalonManager.decryptBoolean(resultado.getString(6)));
                tab.setBill(SalonManager.decryptBoolean(resultado.getString(7)));
                tab.setToPay(SalonManager.decryptBoolean(resultado.getString(8)));
                tab.setDiscount(SalonManager.decryptInt(resultado.getString(9)));
                tab.setError(SalonManager.decryptDouble(resultado.getString(10)));
                tab.setPriceCorrection(SalonManager.decryptDouble(resultado.getString(11)));
                tab.setAmountCash(SalonManager.decryptDouble(resultado.getString(12)));
                tab.setAmountElectronic(SalonManager.decryptDouble(resultado.getString(13)));
                tab.setTotal(SalonManager.decryptDouble(resultado.getString(14)));
                tab.setComments(SalonManager.decrypt(resultado.getString(15)));
                tab.setActiveTable(SalonManager.decryptBoolean(resultado.getString(16)));
            }
            return tab;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public int getMaxTab(String st, Timestamp ds) throws Exception {
        int i = 0;
        try {
            String sql = "SELECT COUNT(*) AS cantidad FROM tabs WHERE table_pos = '" + SalonManager.encrypt(st) + "' AND table_open_time > '" + SalonManager.encryptTs(ds) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                i = resultado.getInt(1);
            }
            return i;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void downActiveTable(Table t) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id = '" + SalonManager.encrypt(t.getId()) + "';";
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

    public void upActiveTable(Table t) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id = '" + SalonManager.encrypt(t.getId()) + "';";
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

//    public ArrayList<Table> listarTablesByDat(Timestamp open, Timestamp close) throws Exception {
//        ArrayList<Table> tables = new ArrayList<>();
//        try {
//            String sql = "SELECT * FROM tabs WHERE table_open_time >= '" + SalonManager.encryptTs(open) + "' AND table_open_time <= '" + SalonManager.encryptTs(close) + "' AND table_active = '" + SalonManager.encryptBoolean(true) + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            while (resultado.next()) {
//                Table tab = new Table();
//                tab.setNum(SalonManager.decryptInt(resultado.getString(1)));
//                tab.setPos(SalonManager.decrypt(resultado.getString(2)));
//                tab.setOpenTime(SalonManager.decryptTs(resultado.getString(3)));
//                tab.setCloseTime(SalonManager.decryptTs(resultado.getString(4)));
//                tab.setId(SalonManager.decrypt(resultado.getString(5)));
//                tab.setOpen(SalonManager.decryptBoolean(resultado.getString(6)));
//                tab.setBill(SalonManager.decryptBoolean(resultado.getString(7)));
//                tab.setToPay(SalonManager.decryptBoolean(resultado.getString(8)));
//                tab.setDiscount(SalonManager.decryptInt(resultado.getString(9)));
//                tab.setError(SalonManager.decryptDouble(resultado.getString(10)));
//                tab.setPriceCorrection(SalonManager.decryptDouble(resultado.getString(11)));
//                tab.setAmountCash(SalonManager.decryptDouble(resultado.getString(12)));
//                tab.setAmountElectronic(SalonManager.decryptDouble(resultado.getString(13)));
//                tab.setTotal(SalonManager.decryptDouble(resultado.getString(14)));
//                tab.setComments(SalonManager.decrypt(resultado.getString(15)));
//                tab.setActiveTable(SalonManager.decryptBoolean(resultado.getString(16)));
//                tables.add(tab);
//            }
//            return tables;
//        } catch (Exception e) {
//            throw e;
//        }  finally {
//            desconectarBase();
//        }
//    }
//    public ArrayList<String> listarIdByWorkshift(Timestamp ts) throws Exception {
//        ArrayList<String> ids = new ArrayList<>();
//        Timestamp open = ts;
//        Timestamp close = new Timestamp(new Date().getTime());
//        try {
//            String sql = "SELECT table_id FROM tabs WHERE table_open_time >= '" + SalonManager.encryptTs(open) + "' AND table_open_time <= '" + SalonManager.encryptTs(close) + "' AND table_active = '" + SalonManager.encryptBoolean(true) + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            while (resultado.next()) {
//                String st = "";
//                st = SalonManager.decrypt(resultado.getString(1));
//                ids.add(st);
//            }
//            return ids;
//        } catch (Exception e) {
//            throw e;
//        }  finally {
//            desconectarBase();
//        }
//    }
    public ArrayList<String> getActiveIds() throws Exception {
        ArrayList<String> tables = new ArrayList<>();
        try {
            String sql = "SELECT table_id FROM tabs WHERE table_open = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                String tab = SalonManager.encrypt(resultado.getString(1));
                tables.add(tab);
            }
            return tables;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Integer> activeTabIcMod(int idItem, String idTab) throws Exception {
        ArrayList<Integer> idsTabsIc = new ArrayList<>();
        try {
            String sql = "SELECT item_card_order_id_fkey FROM item_card_order_tabs WHERE item_card_order_id_fkey = " + SalonManager.encryptInt(idItem) + " AND table_id_fkey = '" + SalonManager.encrypt(idTab) + "' ;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int ic = SalonManager.decryptInt(resultado.getString(1));
                idsTabsIc.add(ic);
            }
            return idsTabsIc;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public String askComment(Table tabAux) throws Exception {
        String comment = "";
        try {
            String sql = "SELECT table_comment FROM tabs WHERE table_id = '" + SalonManager.encrypt(tabAux.getId()) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                comment = SalonManager.decrypt(resultado.getString(1));
            }
            return comment;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

//    public int maxBarrTab(Workshift ws) throws Exception {
//        Integer id = 0;
//        Timestamp ts2 = ws.getCloseDateWs();
//        if (ts2 == null) {
//            ts2 = new Timestamp(new Date().getTime());
//        }
//        try {
////            String sql = "SELECT MAX(table_num) FROM tabs WHERE table_pos = '" + SalonManager.encrypt("barra") + "'  AND table_open_time >= '" + SalonManager.encryptTs(ws.getOpenDateWs()) + "' AND table_open_time<= '" + SalonManager.encryptTs(ts2) + "' AND table_active = '" + SalonManager.encryptBoolean(true) + "';";
//            System.out.println(sql);
//            consultarBase(sql);
//            while (resultado.next()) {
//                id = SalonManager.decryptInt(resultado.getString(1));
//                if (id == null) {
//                    id = 0;
//                }
//            }
//            return id;
//        } catch (Exception e) {
//            throw e;
//        }  finally {
//            desconectarBase();
//        }
//    }
    public ArrayList<Timestamp> listarTabsTsActive() throws Exception {
        ArrayList<Timestamp> tabsTs = new ArrayList<>();
        try {
            String sql = "SELECT table_open_time FROM tabs WHERE table_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Timestamp ts = SalonManager.decryptTs(resultado.getString(1));
                tabsTs.add(ts);
            }
            return tabsTs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Timestamp> listarTabsTsOpen() throws Exception {
        ArrayList<Timestamp> tabsTs = new ArrayList<>();
        try {
            String sql = "SELECT table_open_time FROM tabs WHERE table_open = '" + SalonManager.encryptBoolean(true) + "' AND table_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Timestamp ts = SalonManager.decryptTs(resultado.getString(1));
                tabsTs.add(ts);
            }
            return tabsTs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Timestamp> listarTabsTsActiveBarr() throws Exception {
        ArrayList<Timestamp> tabsTs = new ArrayList<>();
        try {
            String sql = "SELECT table_open_time FROM tabs WHERE table_active = '" + SalonManager.encryptBoolean(true) + "' AND table_pos = '" + SalonManager.encrypt("barra") + "' ;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Timestamp ts = SalonManager.decryptTs(resultado.getString(1));
                tabsTs.add(ts);
            }
            return tabsTs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Timestamp> listarTabsTsActiveDeli() throws Exception {
        ArrayList<Timestamp> tabsTs = new ArrayList<>();
        try {
            String sql = "SELECT table_open_time FROM tabs WHERE table_active = '" + SalonManager.encryptBoolean(true) + "' AND table_pos = '" + SalonManager.encrypt("delivery") + "' ;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Timestamp ts = SalonManager.decryptTs(resultado.getString(1));
                tabsTs.add(ts);
            }
            return tabsTs;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public Table getTableByTs(Timestamp ts) throws Exception {
        Table tab = new Table();
        try {
            String sql = "SELECT * FROM tabs WHERE table_open_time = '" + SalonManager.encryptTs(ts) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                tab.setNum(SalonManager.decryptInt(resultado.getString(1)));
                tab.setPos(SalonManager.decrypt(resultado.getString(2)));
                tab.setOpenTime(SalonManager.decryptTs(resultado.getString(3)));
                tab.setCloseTime(SalonManager.decryptTs(resultado.getString(4)));
                tab.setId(SalonManager.decrypt(resultado.getString(5)));
                tab.setOpen(SalonManager.decryptBoolean(resultado.getString(6)));
                tab.setBill(SalonManager.decryptBoolean(resultado.getString(7)));
                tab.setToPay(SalonManager.decryptBoolean(resultado.getString(8)));
                tab.setDiscount(SalonManager.decryptInt(resultado.getString(9)));
                tab.setError(SalonManager.decryptDouble(resultado.getString(10)));
                tab.setPriceCorrection(SalonManager.decryptDouble(resultado.getString(11)));
                tab.setAmountCash(SalonManager.decryptDouble(resultado.getString(12)));
                tab.setAmountElectronic(SalonManager.decryptDouble(resultado.getString(13)));
                tab.setTotal(SalonManager.decryptDouble(resultado.getString(14)));
                tab.setComments(SalonManager.decrypt(resultado.getString(15)));
                tab.setActiveTable(SalonManager.decryptBoolean(resultado.getString(16)));
            }
            return tab;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}

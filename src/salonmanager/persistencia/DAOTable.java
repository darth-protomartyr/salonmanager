package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOTable extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveTable(Table tab, Timestamp ts) throws Exception {
        if (tab.getComments().equals("<br>")) {
            tab.setComments("");
        }
        
        boolean error = false;
        if (ts != null) {
            ArrayList<String> idTabs = listarIdByWorkshift(ts);
            for (int i = 0; i < idTabs.size(); i++) {
                if (idTabs.get(i).equals(tab.getId())) {
                    error = true;
                }
            }
        }

        if (tab.getNum() == 0) {
            utiliMsg.errorIngresoNum();
            error = true;
        }

        if (tab.getPos().equals("")) {
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
                if (tab.getCloseTime() == null) {
                    sql = "INSERT INTO tabs(table_num, table_pos, table_open_time, table_close_time, table_id, table_open, table_bill, table_to_pay, table_discount, table_error, table_price_correction, table_amount_cash, table_amount_electronic, table_total, table_comments, table_active) "
                            + "VALUES( " + tab.getNum() + ",'" + tab.getPos() + "','" + tab.getOpenTime() + "'," + tab.getCloseTime() + ",'" + tab.getId() + "'," + tab.isOpen() + ", " + tab.isBill() + ", " + tab.isToPay() + ", " + tab.getDiscount() + ", " + tab.getError() + "," + tab.getPriceCorrection() + ", " + tab.getAmountCash() + ", " + tab.getAmountElectronic() + ", " + tab.getTotal() + ",'" + tab.getComments() + "', " + tab.isActiveTable() + ");";
                } else {
                    sql = "INSERT INTO tabs(table_num, table_pos, table_open_time, table_close_time, table_id, table_open, table_bill, table_to_pay, table_discount, table_error, table_price_correction, table_amount_cash, table_amount_electronic, table_total, table_comments, table_active) "
                            + "VALUES( " + tab.getNum() + ",'" + tab.getPos() + "','" + tab.getOpenTime() + "','" + tab.getCloseTime() + "','" + tab.getId() + "'," + tab.isOpen() + ", " + tab.isBill() + ", " + tab.isToPay() + ", " + tab.getDiscount() + ", " + tab.getError() + "," + tab.getPriceCorrection() + ", " + tab.getAmountCash() + ", " + tab.getAmountElectronic() + ", " + tab.getTotal() + ",'" + tab.getComments() + "', " + tab.isActiveTable() + ");";
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

    public void updateTableTotal(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_total = " + tab.getTotal() + " WHERE table_id = '" + tab.getId() + "';";
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

    public void updateTableBill(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_bill = " + tab.isBill() + " WHERE table_id = '" + tab.getId() + "';";
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

    public void updateTableOpen(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_open = " + tab.isOpen() + " WHERE table_id = '" + tab.getId() + "';";
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

    public void updateTableDiscount(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_discount = " + tab.getDiscount() + " WHERE table_id = '" + tab.getId() + "';";
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

    public void updateTableMountCash(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_amount_cash = " + tab.getAmountCash() + " WHERE table_id = '" + tab.getId() + "';";

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

    public void updateTableMountElectronic(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_amount_electronic = " + tab.getAmountElectronic() + " WHERE table_id = '" + tab.getId() + "';";

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

    public void updateToPay(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_to_pay = " + tab.isToPay() + " WHERE table_id = '" + tab.getId() + "';";
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

    public void updateError(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_error = " + tab.getError() + " WHERE table_id = '" + tab.getId() + "';";
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

    public void updateCloseTime(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_close_time = '" + tab.getCloseTime() + "' WHERE table_id = '" + tab.getId() + "';";
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

    public void updateComments(Table tab) throws Exception {
        if (tab.getComments().equals("<br>")) {
            tab.setComments("");
        }
        if (!tab.getComments().equals("Ingrese un comentario(opcional): ") || !tab.getComments().equals("Causa del Error (obligatorio): ")) {
            try {
                String sql1 = "UPDATE tabs SET table_comments = '" + tab.getComments() + "' WHERE table_id = '" + tab.getId() + "';";
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

    public ArrayList<Table> listarTablesByWorkshift(Workshift ws) throws Exception {
        ArrayList<Table> tables = new ArrayList<>();
        Timestamp open = ws.getOpenWs();
        Timestamp close = ws.getCloseWs();
        if (close == null) {
            close = new Timestamp(new Date().getTime());
        }

        try {
            String sql = "SELECT * FROM tabs WHERE table_open_time >= '" + open + "' AND table_open_time <= '" + close + "' AND table_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Table tab = new Table();
                tab.setNum(resultado.getInt(1));
                tab.setPos(resultado.getString(2));
                tab.setOpenTime(resultado.getTimestamp(3));
                tab.setCloseTime(resultado.getTimestamp(4));
                tab.setId(resultado.getString(5));
                tab.setOpen(resultado.getBoolean(6));
                tab.setBill(resultado.getBoolean(7));
                tab.setToPay(resultado.getBoolean(8));
                tab.setDiscount(resultado.getInt(9));
                tab.setError(resultado.getDouble(10));
                tab.setPriceCorrection(resultado.getDouble(11));
                tab.setAmountCash(resultado.getDouble(12));
                tab.setAmountElectronic(resultado.getDouble(13));
                tab.setTotal(resultado.getDouble(14));
                tab.setComments(resultado.getString(15));
                tab.setActiveTable(resultado.getBoolean(16));
                tables.add(tab);
            }
            return tables;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Table> listarTablesOpenByWorkshift(Workshift ws) throws Exception {
        ArrayList<Table> tables = new ArrayList<>();
        Timestamp open = ws.getOpenWs();
        Timestamp close = ws.getCloseWs();
        if (close == null) {
            close = new Timestamp(new Date().getTime());
        }

        try {
            String sql = "SELECT * FROM tabs WHERE table_open_time >= '" + open + "' AND table_open_time <= '" + close + "' AND table_open = true AND table_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Table tab = new Table();
                tab.setNum(resultado.getInt(1));
                tab.setPos(resultado.getString(2));
                tab.setOpenTime(resultado.getTimestamp(3));
                tab.setCloseTime(resultado.getTimestamp(4));
                tab.setId(resultado.getString(5));
                tab.setOpen(resultado.getBoolean(6));
                tab.setBill(resultado.getBoolean(7));
                tab.setToPay(resultado.getBoolean(8));
                tab.setDiscount(resultado.getInt(9));
                tab.setError(resultado.getDouble(10));
                tab.setPriceCorrection(resultado.getDouble(11));
                tab.setAmountCash(resultado.getDouble(12));
                tab.setAmountElectronic(resultado.getDouble(13));
                tab.setTotal(resultado.getDouble(14));
                tab.setComments(resultado.getString(15));
                tab.setActiveTable(resultado.getBoolean(16));
                tables.add(tab);
            }
            return tables;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public Table getTableById(String st) throws Exception {
        Table tab = new Table();
        try {
            String sql = "SELECT * FROM tabs WHERE table_id = '" + st + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                tab.setNum(resultado.getInt(1));
                tab.setPos(resultado.getString(2));
                tab.setOpenTime(resultado.getTimestamp(3));
                tab.setCloseTime(resultado.getTimestamp(4));
                tab.setId(resultado.getString(5));
                tab.setOpen(resultado.getBoolean(6));
                tab.setBill(resultado.getBoolean(7));
                tab.setToPay(resultado.getBoolean(8));
                tab.setDiscount(resultado.getInt(9));
                tab.setError(resultado.getDouble(10));
                tab.setPriceCorrection(resultado.getDouble(11));
                tab.setAmountCash(resultado.getDouble(12));
                tab.setAmountElectronic(resultado.getDouble(13));
                tab.setTotal(resultado.getDouble(14));
                tab.setComments(resultado.getString(15));
                tab.setActiveTable(resultado.getBoolean(16));
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
            String sql = "SELECT COUNT(*) AS cantidad FROM tabs WHERE table_pos = '" + st + "' AND table_open_time > '" + ds + "';";
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
            String sql1 = "UPDATE tabs SET table_active = false WHERE table_id = '" + t.getId() + "';";
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

    public void upActiveTable(Table t) throws Exception {
        try {
            String sql1 = "UPDATE tabs SET table_active = true WHERE table_id = '" + t.getId() + "';";
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

    public ArrayList<Table> listarTablesByDate(Timestamp open, Timestamp close) throws Exception {
        ArrayList<Table> tables = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tabs WHERE table_open_time >= '" + open + "' AND table_open_time <= '" + close + "' AND table_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Table tab = new Table();
                tab.setNum(resultado.getInt(1));
                tab.setPos(resultado.getString(2));
                tab.setOpenTime(resultado.getTimestamp(3));
                tab.setCloseTime(resultado.getTimestamp(4));
                tab.setId(resultado.getString(5));
                tab.setOpen(resultado.getBoolean(6));
                tab.setBill(resultado.getBoolean(7));
                tab.setToPay(resultado.getBoolean(8));
                tab.setDiscount(resultado.getInt(9));
                tab.setError(resultado.getDouble(10));
                tab.setPriceCorrection(resultado.getDouble(11));
                tab.setAmountCash(resultado.getDouble(12));
                tab.setAmountElectronic(resultado.getDouble(13));
                tab.setTotal(resultado.getDouble(14));
                tab.setComments(resultado.getString(15));
                tab.setActiveTable(resultado.getBoolean(16));
                tables.add(tab);
            }
            return tables;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<String> listarIdByWorkshift(Timestamp ts) throws Exception {
        ArrayList<String> ids = new ArrayList<>();
        Timestamp open = ts;
        Timestamp close = new Timestamp(new Date().getTime());
        try {
            String sql = "SELECT table_id FROM tabs WHERE table_open_time >= '" + open + "' AND table_open_time <= '" + close + "' AND table_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                String st = "";
                st = resultado.getString(1);
                ids.add(st);
            }
            return ids;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<String> getActiveIds() throws Exception {
        ArrayList<String> tables = new ArrayList<>();
        try {
            String sql = "SELECT table_id FROM tabs WHERE table_open = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                String tab = resultado.getString(1);
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
            String sql = "SELECT itemcard_order_id_fkey FROM itemcard_order_tabs WHERE itemcard_order_id_fkey = " + idItem + " AND table_id_fkey = '"+ idTab+"' ;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int ic = resultado.getInt(1);
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
            String sql = "SELECT table_comment FROM tabs WHERE table_id = '" + tabAux.getId() +"';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                comment = resultado.getString(1);
            }
            return comment;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public int maxBarrTab(Workshift ws) throws Exception {
        Integer id = 0;
        Timestamp ts2 = ws.getCloseWs();
        if (ts2 == null) {
            ts2 = new Timestamp(new Date().getTime());
        }
        try {
            String sql = "SELECT MAX(table_num) FROM tabs WHERE table_pos = 'barra' AND table_open_time >= '"+ ws.getOpenWs() +"' AND table_open_time<= '" + ts2 +"' AND table_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                id = resultado.getInt(1);
                if (id == null) {
                    id = 0;
                } 
            }
            return id;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}
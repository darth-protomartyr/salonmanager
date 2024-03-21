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
//    ServicioRegister sr = new ServicioRegister();
//    ServicioSalon ss = new ServicioSalon();

    public void saveTable(Table tab) throws Exception {
        boolean error = false;

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
//            String sql1 = "UPDATE tabs SET table_amount_cash = table_amount_cash + " + tab.getAmountCash() + " WHERE table_id = '" + tab.getId() + "';";
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
//            String sql1 = "UPDATE tabs SET table_amount_electronic = table_amount_electronic + " + tab.getAmountElectronic() + " WHERE table_id = '" + tab.getId() + "';";
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
        ArrayList<Table> tables = new ArrayList<Table>();
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
        ArrayList<Table> tables = new ArrayList<Table>();
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
}

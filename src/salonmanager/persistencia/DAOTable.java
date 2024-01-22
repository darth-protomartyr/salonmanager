package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
            try {
                String sql = "INSERT INTO tabs(table_num, table_pos, table_open_time, table_id, table_open, table_bill, table_to_pay, table_discount, table_error, table_price_correction, table_amount_cash, table_amount_electronic, table_total, table_comments, table_active) "
                        + "VALUES( " + tab.getNum() + ",'" + tab.getPos() + "','" + tab.getOpenTime() + "','" + tab.getId() + "'," + tab.isOpen() + ", " + tab.isBill() + ", " + tab.isToPay() + ", " + tab.getDiscount() + ", " + tab.getError() + "," + tab.getPriceCorrection() + ", " + tab.getAmountCash() + ", " + tab.getAmountElectronic() + ", " + tab.getTotal() + ",'" + tab.getComments() + "', " + tab.isActiveTable() + ");";
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

    public ArrayList<Table> listarTablesByTimestamp(Workshift ws) throws Exception {
        ArrayList<Table> tables = new ArrayList<Table>();
        Timestamp open = ws.getWsOpen();
        Timestamp close = ws.getWsClose();

        try {
            String sql = "SELECT * FROM tabs WHERE table_open_time >= '" + open + "' AND table_open_time <= '" + close + "' AND table_active = true;";
            if (ws.getWsClose() == null) {
                sql = "SELECT * FROM tabs WHERE table_open_time >= '" + open + "' AND table_open_time <= " + close + " AND table_active = true;";
            }
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                Table tab = new Table();
                tab.setNum(resultado.getInt(1));
                tab.setPos(resultado.getString(2));
                tab.setOpenTime(resultado.getTimestamp(3));
                tab.setId(resultado.getString(4));
                tab.setOpen(resultado.getBoolean(5));
                tab.setBill(resultado.getBoolean(6));
                tab.setToPay(resultado.getBoolean(7));
                tab.setDiscount(resultado.getInt(8));
                tab.setError(resultado.getDouble(9));
                tab.setPriceCorrection(resultado.getDouble(10));
                tab.setAmountCash(resultado.getDouble(11));
                tab.setAmountElectronic(resultado.getDouble(12));
                tab.setTotal(resultado.getDouble(13));
                tab.setComments(resultado.getString(14));
                tab.setActiveTable(resultado.getBoolean(15));
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
                tab.setId(resultado.getString(4));
                tab.setOpen(resultado.getBoolean(5));
                tab.setBill(resultado.getBoolean(6));
                tab.setToPay(resultado.getBoolean(7));
                tab.setDiscount(resultado.getInt(8));
                tab.setError(resultado.getDouble(9));
                tab.setPriceCorrection(resultado.getDouble(10));
                tab.setAmountCash(resultado.getDouble(11));
                tab.setAmountElectronic(resultado.getDouble(12));
                tab.setTotal(resultado.getDouble(13));
                tab.setComments(resultado.getString(14));
                tab.setActiveTable(resultado.getBoolean(15));
            }
            return tab;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

//    public void deleteTable(Table tab) throws Exception {
//        try {
//            String sql = "DELETE FROM tabs WHERE table_id = '" + tab.getId() + "';";
//            System.out.println(sql);
//            insertarModificarEliminar(sql.trim());
//        } catch (SQLException e) {
//            if (e.getErrorCode() == 1062) {
//                utiliMsg.cargaError();
//            } else {
//                e.printStackTrace();
//            }
//        } finally {
//            desconectarBase();
//        }
//    }

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

    public Table getTableById(Table tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.entidades.Table;
import salonmanager.entidades.Workshift;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOTable extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
//    ServicioRegister sr = new ServicioRegister();
//    ServicioSalon ss = new ServicioSalon();

    public void guardarTable(Table tab) throws Exception {
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
                String sql = "INSERT INTO mesas(table_num, table_pos, table_open_time, table_id, table_open, table_bill, table_discount, table_error, table_price_correction, table_amount_cash, table_amount_electronic, table_total) "
                        + "VALUES( '" + tab.getNum() + "','" + tab.getPos() + "','" + tab.getOpenTime() + "','" + tab.getId() + "'," + tab.isOpen() + ", " + tab.isBill() + ", '" + tab.getDiscount() + "', '" + tab.getError() + "'," + tab.getPriceCorrection() + ", " + tab.getAmountCash() + ", " + tab.getAmountElectronic() + ", " + tab.getTotal() + ");";
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
            String sql1 = "UPDATE mesas SET table_total = " + tab.getTotal() + " WHERE table_id = '" + tab.getId() + "';";
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
            String sql1 = "UPDATE mesas SET table_bill = " + tab.isBill() + " WHERE table_id = '" + tab.getId() + "';";
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
            String sql1 = "UPDATE mesas SET table_open = " + tab.isOpen() + " WHERE table_id = '" + tab.getId() + "';";
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
            String sql1 = "UPDATE mesas SET table_discount = " + tab.getDiscount() + " WHERE table_id = '" + tab.getId() + "';";
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
            String sql1 = "UPDATE mesas SET table_amount_cash = " + tab.getAmountCash() + " WHERE table_id = '" + tab.getId() + "';";
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
            String sql1 = "UPDATE mesas SET table_amount_electronic = " + tab.getAmountElectronic() + " WHERE table_id = '" + tab.getId() + "';";
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

    public ArrayList<Table> listarTablesByTimestamp(Workshift ws) throws Exception {
        ArrayList<Table> tables = new ArrayList<Table>();
        Timestamp open = ws.getOpenShift();
        Timestamp close = ws.getCloseShift();

        try {
            String sql = "SELECT * FROM mesas WHERE table_open_time >= '" + open + "' AND table_open_time <= '" + close + "';";
            if (ws.getCloseShift() == null) {
                sql = "SELECT * FROM mesas WHERE table_open_time >= '" + open + "' AND table_open_time <= " + close + ";";
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
                tab.setDiscount(resultado.getInt(7));
                tab.setError(resultado.getDouble(8));
                tab.setPriceCorrection(resultado.getDouble(9));
                tab.setAmountCash(resultado.getDouble(10));
                tab.setAmountElectronic(resultado.getDouble(11));
                tab.setTotal(resultado.getDouble(12));
                tables.add(tab);
            }
            return tables;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}

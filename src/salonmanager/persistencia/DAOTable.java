package salonmanager.persistencia;

import java.sql.SQLException;
import salonmanager.entidades.Table;
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
                        + "VALUES( '" + tab.getNum() + "','" + tab.getPos() + "','" + tab.getOpenTime() + "','" + tab.getId() + "'," + tab.isOpen() + ", " + tab.isBill() + ", '" + tab.getDiscount() + "', '" + tab.getError() + "'," + tab.getPriceCorrection() + ", " + tab.getAmountCash()+ ", " + tab.getAmountElectronic() + ", " + tab.getTotal() + ");";
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
    
    public void updateTableMountsKind(Table tab) throws Exception {
        try {
            String sql1 = "UPDATE mesas SET table_amount_cash = " + tab.getAmountCash()+ " AND table_amount_electronic = " + tab.getAmountElectronic() + " WHERE table_id = '" + tab.getId() + "';";
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

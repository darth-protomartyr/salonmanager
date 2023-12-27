package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.entidades.Itemcard;
import salonmanager.entidades.Table;
import salonmanager.servicios.ServicioRegister;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOItemcard extends DAO {
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioRegister sr = new ServicioRegister();

    public ArrayList<Itemcard> listarItemsCard() throws Exception {
        ArrayList<Itemcard> items = new ArrayList<Itemcard>();
        try {
            String sql = "SELECT * FROM Itemcards WHERE Itemcard_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            Itemcard ic = new Itemcard();
            while (resultado.next()) {
                int id = resultado.getInt(1);
                String code = resultado.getString(2);
                String name = resultado.getString(3);
                String caption = resultado.getString(4);
                String description = resultado.getString(5);
                double cost = resultado.getDouble(6);
                double price = resultado.getDouble(7);
                int stock = resultado.getInt(8);
                Timestamp dateCreation = resultado.getTimestamp(9);
                Timestamp dateCostUpdate = resultado.getTimestamp(10);
                boolean activeTip = resultado.getBoolean(11);
                boolean activeItem = resultado.getBoolean(12);
                ic = new Itemcard(id, code, name, caption, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
                items.add(ic);
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Itemcard> listItemsByCaption(String capt) throws Exception {
        ArrayList<Itemcard> items = new ArrayList<Itemcard>();
        try {
            String sql = "SELECT * FROM Itemcards WHERE Itemcard_active = true AND Itemcard_caption = '" + capt + "';";
            System.out.println(sql);
            consultarBase(sql);
            Itemcard ic = new Itemcard();
            while (resultado.next()) {
                int id = resultado.getInt(1);
                String code = resultado.getString(2);
                String name = resultado.getString(3);
                String caption = resultado.getString(4);
                String description = resultado.getString(5);
                double cost = resultado.getDouble(6);
                double price = resultado.getDouble(7);
                int stock = resultado.getInt(8);
                Timestamp dateCreation = resultado.getTimestamp(9);
                Timestamp dateCostUpdate = resultado.getTimestamp(10);
                boolean activeTip = resultado.getBoolean(11);
                boolean activeItem = resultado.getBoolean(12);
                ic = new Itemcard(id, code, name, caption, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
                items.add(ic);
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void saveItemcard(Itemcard item) throws Exception {
        boolean error = false;

        if (item == null) {
            utiliMsg.errorIngresoItem();
            error = true;
        }

        if (item.getName() == "") {
            utiliMsg.errorNameItem();
            error = true;
        }

        if (item.getPrice() == 0) {
            utiliMsg.errorPriceItem();
            error = true;
        }

        if (error == false) {
            try {
                String sql = "INSERT INTO Itemcards(Itemcard_code, Itemcard_name, Itemcard_caption, Itemcard_description, Itemcard_cost, Itemcard_price,  Itemcard_stock, Itemcard_dateCreation, Itemcard_tip, Itemcard_active) "
                        + "VALUES( '" + item.getCode() + "','" + item.getName() + "','" + item.getCaption() + "','" + item.getDescription() + "','" + item.getCost() + "','" + item.getPrice() + "','" + item.getStock() + "','" + item.getDateCreation() + "'," + item.isActiveTip() + ", " + item.isActiveItem() + ");";
                System.out.println(sql);
                insertarModificarEliminar(sql);
                String values = item.getCaption() + " - " + item.getDescription() + " - " + item.getCost() + " - " + item.getPrice() + " - " + item.getStock() + " - " + item.getDateCreation() + " - " + " - " + item.isActiveTip() + " - " + item.isActiveItem() + " - ";
                sr.crearRegistro("saveItemcard", item.getName(), values);
                utiliMsg.cargaItem();
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

    
    public void modificarItem(Itemcard itemAux, String name, String caption, String description, double cost, double price, int stock, boolean tipAlta) throws Exception {
        Timestamp upd = new Timestamp(new Date().getTime());
        String sql = "UPDATE Itemcards "
                + "SET Itemcard_name = '" + name + "', Itemcard_caption = '" + caption + "', itemCaption_description = '" + description
                + "', Itemcard_cost = " + cost + " , Itemcard_price = " + price + ", Itemcard_stock = " + stock + ","
                + "', Itemcard_dateUpdate ='" + upd + "', Itemcard_tip = " + tipAlta
                + " WHERE Itemcard_id = " + itemAux.getId() + ";";
        System.out.println(sql);
        insertarModificarEliminar(sql);
        String values = name + " - " + caption + " - " + description + " - " + cost + " - " + price + " - " + stock + " - " + true;
        sr.crearRegistro("modificarItemcard", name, values);
        desconectarBase();
    }

    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    public void saveItemOrderTable(Itemcard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO Itemcardorder_tabs( Itemcardorder_tabs_active, Itemcardorder_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES(" + true + ", " + ic.getId() + ", '" + tab.getId() + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public void upActiveItemOrderTable(Itemcard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE Itemcardorder_tabs SET Itemcardorder_tabs_active = " + true + " WHERE table_id_fkey = '" + t.getId() + "' AND Itemcardorder_id_fkey = " + ic.getId() + " AND Itemcardorder_tabs_active = false LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;

        } finally {
            desconectarBase();
        }
    }

    public void downActiveItemOrderTable(Itemcard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE Itemcardorder_tabs SET Itemcardorder_tabs_active = " + false + " WHERE table_id_fkey = '" + t.getId() + "' AND Itemcardorder_id_fkey = " + ic.getId() + " AND Itemcardorder_tabs_active = true LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void downActiveItemOrderTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE Itemcardorder_tabs SET Itemcardorder_tabs_active = " + false + " WHERE table_id_fkey = '" + t.getId() + "' AND Itemcardorder_tabs_active = true;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Itemcard> listarItemcardOrder(String tabId) throws Exception {
        ArrayList<Itemcard> items = new ArrayList<Itemcard>();
        try {
            String sql = "SELECT Itemcardorder_id_fkey FROM Itemcardorder_tabs WHERE table_id_fkey = '" + tabId + "' AND Itemcardorder_tabs_active = " + true + ";";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<Integer>();
            while (resultado.next()) {
                int idIc = resultado.getInt(1);
                idItems.add(idIc);
            }

            for (int i = 0; i < idItems.size(); i++) {
                ArrayList<Itemcard> itemsCompleta = listarItemsCard();
                for (int j = 0; j < itemsCompleta.size(); j++) {
                    if (idItems.get(i) == itemsCompleta.get(j).getId()) {
                        Itemcard ic = itemsCompleta.get(j);
                        items.add(ic);
                        break;
                    }
                }
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    //Tabla Itemcardgift_tabs-----------------------------------------------------------------------
    //Tabla Itemcardgift_tabs-----------------------------------------------------------------------
    //Tabla Itemcardgift_tabs-----------------------------------------------------------------------
    //Tabla Itemcardgift_tabs-----------------------------------------------------------------------
    public void saveItemGiftTable(Itemcard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO Itemcardgift_tabs(Itemcardgift_tabs_active, ItemcardGift_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES(" + true + ", " + ic.getId() + ", '" + tab.getId() + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public void downActiveItemGiftTable(Itemcard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE Itemcardgift_tabs SET Itemcardgift_tabs_active = " + false + " WHERE table_id_fkey = '" + t.getId() + "' AND ItemcardGift_id_fkey = " + ic.getId() + " AND Itemcardgift_tabs_active = true LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void downActiveItemGiftTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE Itemcardgift_tabs SET Itemcardgift_tabs_active = " + false + " WHERE table_id_fkey = '" + t.getId() + "' AND Itemcardgift_tabs_active = true;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void upActiveItemGiftTable(Table t, Itemcard ic) throws Exception {
        try {
            String sql = "UPDATE Itemcardgift_tabs SET Itemcardgift_tabs_active = " + true + " WHERE table_id_fkey = '" + t.getId() + "' AND ItemcardGift_id_fkey = " + ic.getId() + " AND Itemcardorder_id_fkey = " + ic.getId() + " AND Itemcardgift_tabs_active = false LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Itemcard> listarItemcardGifts(String tabId) throws Exception {
        ArrayList<Itemcard> items = new ArrayList<Itemcard>();
        ArrayList<Integer> idItems = new ArrayList<Integer>();
        try {
            String sql = "SELECT ItemcardGift_id_fkey FROM Itemcardgift_tabs WHERE table_id_fkey = '" + tabId + "' AND Itemcardgift_tabs_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int idIc = resultado.getInt(1);
                idItems.add(idIc);
            }

            for (int i = 0; i < idItems.size(); i++) {
                ArrayList<Itemcard> itemsCompleta = listarItemsCard();
                for (int j = 0; j < itemsCompleta.size(); j++) {
                    if (idItems.get(i) == itemsCompleta.get(j).getId()) {
                        Itemcard ic = itemsCompleta.get(j);
                        items.add(ic);
                        break;
                    }
                }
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    //Tabla Itemcardpayed_tabs-----------------------------------------------------------------------
    //Tabla Itemcardpayed_tabs-----------------------------------------------------------------------
    //Tabla Itemcardpayed_tabs-----------------------------------------------------------------------
    //Tabla Itemcardpayed_tabs-----------------------------------------------------------------------    
    public void saveItemPayedTable(Itemcard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO Itemcardpayed_tabs( Itemcardpayed_tabs_active, ItemcardPayed_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES(" + true + ", " + ic.getId() + ", '" + tab.getId() + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public void downActiveItemPayedTable(Itemcard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE Itemcardpayed_tabs SET Itemcardpayed_tabs_active = " + false + " WHERE table_id_fkey = '" + t.getId() + "' AND ItemcardPayed_id_fkey = " + ic.getId() + " AND Itemcardpayed_tabs_active = true LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void downActiveItemPayedTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE Itemcardpayed_tabs SET Itemcardpayed_tabs_active = " + false + " WHERE table_id_fkey = '" + t.getId() + "' AND Itemcardpayed_tabs_active = true;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void upActiveItemPayedTable(Itemcard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE Itemcardpayed_tabs SET Itemcardpayed_tabs_active = " + true + " WHERE table_id_fkey = '" + t.getId() + "' AND ItemcardPayed_id_fkey = " + ic.getId() + " AND Itemcardpayed_tabs_active = false LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Itemcard> listarItemcardPartialPayed(String tabId) throws Exception {
        ArrayList<Itemcard> items = new ArrayList<Itemcard>();
        try {
            String sql = "SELECT ItemcardPayed_id_fkey FROM Itemcardpayed_tabs WHERE table_id_fkey = '" + tabId + "' AND Itemcardpayed_tabs_active = true;";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<Integer>();
            while (resultado.next()) {
                int idIc = resultado.getInt(1);
                idItems.add(idIc);
            }

            for (int i = 0; i < idItems.size(); i++) {
                ArrayList<Itemcard> itemsCompleta = listarItemsCard();
                for (int j = 0; j < itemsCompleta.size(); j++) {
                    if (idItems.get(i) == itemsCompleta.get(j).getId()) {
                        Itemcard ic = itemsCompleta.get(j);
                        items.add(ic);
                        break;
                    }
                }
            }

            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

//Tabla ItemcardpayedND_tabs-----------------------------------------------------------------------
//Tabla ItemcardpayedND_tabs-----------------------------------------------------------------------
//Tabla ItemcardpayedND_tabs-----------------------------------------------------------------------
//Tabla ItemcardpayedND_tabs-----------------------------------------------------------------------      
    public void saveItemPayedNDTable(Itemcard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO ItemcardpayedND_tabs( ItemcardpayedND_tabs_active,ItemcardPayedND_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES(" + true + ", " + ic.getId() + ", '" + tab.getId() + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }

    public void downActiveItemPayedNDTable(Itemcard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE ItemcardpayedND_tabs SET ItemcardpayedND_tabs_active = " + false + " WHERE table_id_fkey = '" + t.getId() + "' AND ItemcardPayedND_id_fkey = " + ic.getId() + " AND Itemcardpayed_tabs_active = true LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void downActiveItemPayedNDTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE ItemcardpayedND_tabs SET ItemcardpayedND_tabs_active = " + false + " WHERE table_id_fkey = '" + t.getId() + "' AND ItemcardpayedND_tabs_active = true;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Itemcard> listarItemcardPartialPayedND(String tabId) throws Exception {
        ArrayList<Itemcard> items = new ArrayList<Itemcard>();
        try {
            String sql = "SELECT ItemcardPayedND_id_fkey FROM ItemcardpayedND_tabs WHERE table_id_fkey = '" + tabId + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<Integer>();
            while (resultado.next()) {
                int idIc = resultado.getInt(1);
                idItems.add(idIc);
            }
            for (int i = 0; i < idItems.size(); i++) {
                ArrayList<Itemcard> itemsCompleta = listarItemsCard();
                for (int j = 0; j < itemsCompleta.size(); j++) {
                    if (idItems.get(i) == itemsCompleta.get(j).getId()) {
                        Itemcard ic = itemsCompleta.get(j);
                        items.add(ic);
                        break;
                    }
                }
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }


    public Itemcard getItemById(int itemIMon) throws Exception {     
        try {
            String sql = "SELECT * FROM Itemcards WHERE Itemcard_active = true AND Itemcard_id = '" + itemIMon + "';";
            System.out.println(sql);
            consultarBase(sql);
            Itemcard ic = new Itemcard();
            while (resultado.next()) {
                int id = resultado.getInt(1);
                String code = resultado.getString(2);
                String name = resultado.getString(3);
                String caption = resultado.getString(4);
                String description = resultado.getString(5);
                double cost = resultado.getDouble(6);
                double price = resultado.getDouble(7);
                int stock = resultado.getInt(8);
                Timestamp dateCreation = resultado.getTimestamp(9);
                Timestamp dateCostUpdate = resultado.getTimestamp(10);
                boolean activeTip = resultado.getBoolean(11);
                boolean activeItem = resultado.getBoolean(12);
                ic = new Itemcard(id, code, name, caption, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
            }
            return ic;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}

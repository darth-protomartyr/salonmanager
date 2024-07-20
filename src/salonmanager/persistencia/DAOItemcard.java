package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.Itemcard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOItemcard extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    
    public ArrayList<Itemcard> listarItemsCard() throws Exception {
        ArrayList<Itemcard> items = new ArrayList<>();
        try {
            String sql = "SELECT * FROM itemcards WHERE itemcard_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            Itemcard ic = new Itemcard();
            while (resultado.next()) {
                int id = SalonManager.decryptInteger(resultado.getString(1));
                String code = SalonManager.decrypt(resultado.getString(2));
                String name = SalonManager.decrypt(resultado.getString(3));
                String category = SalonManager.decrypt(resultado.getString(4));
                String description = SalonManager.decrypt(resultado.getString(5));
                double cost = SalonManager.decryptDouble(resultado.getString(6));
                ArrayList<Double> price = utili.strToArrayDou(SalonManager.decrypt(resultado.getString(7)));
                int stock = SalonManager.decryptInteger(resultado.getString(8));
                String create1 = resultado.getString(9);
                if (create1 == null) {
                    create1 = "";
                }
                Timestamp dateCreation = SalonManager.decryptTs(create1);
                String create2 = resultado.getString(10);
                if (create2 == null) {
                    create2 = "";
                }
                Timestamp dateCostUpdate = SalonManager.decryptTs(create2);
                boolean activeTip = SalonManager.decryptBoolean(resultado.getString(11));
                boolean activeItem = SalonManager.decryptBoolean(resultado.getString(12));
                ic = new Itemcard(id, code, name, category, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
                items.add(ic);
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Integer> listarItemsCardIds() throws Exception {
        ArrayList<Integer> items = new ArrayList<>();
        try {
            String sql = "SELECT * FROM itemcards WHERE itemcard_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            Itemcard ic = new Itemcard();
            while (resultado.next()) {
                int id = SalonManager.decryptInteger(resultado.getString(1));
                items.add(id);
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Integer> listarItemsCardId() throws Exception {
        ArrayList<Integer> itemsId = new ArrayList<>();
        try {
            String sql = "SELECT itemcard_id FROM itemcards WHERE itemcard_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int id = SalonManager.decryptInteger(resultado.getString(1));
                itemsId.add(id);
            }
            return itemsId;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<Itemcard> listItemsByCategory(String cat) throws Exception {
        ArrayList<Itemcard> items = new ArrayList<>();
        try {
            String sql = "SELECT * FROM itemcards WHERE itemcard_active = '" + SalonManager.encryptBoolean(true) + "' AND itemcard_category = '" + SalonManager.encrypt(cat) + "';";
            System.out.println(sql);
            consultarBase(sql);
            Itemcard ic = new Itemcard();
            while (resultado.next()) {
                int id = SalonManager.decryptInteger(resultado.getString(1));
                String code = SalonManager.decrypt(resultado.getString(2));
                String name = SalonManager.decrypt(resultado.getString(3));
                String category = SalonManager.decrypt(resultado.getString(4));
                String description = SalonManager.decrypt(resultado.getString(5));
                double cost = SalonManager.decryptDouble(resultado.getString(6));
                ArrayList<Double> price = utili.strToArrayDou(SalonManager.decrypt(resultado.getString(7)));
                int stock = SalonManager.decryptInteger(resultado.getString(8));
                String create1 = resultado.getString(9);
                if (create1 == null) {
                    create1 = "";
                }
                Timestamp dateCreation = SalonManager.decryptTs(create1);                
                String create2 = resultado.getString(10);
                if (create2 == null) {
                    create2 = "";
                }
                Timestamp dateCostUpdate = SalonManager.decryptTs(create2);
                boolean activeTip = SalonManager.decryptBoolean(resultado.getString(11));
                boolean activeItem = SalonManager.decryptBoolean(resultado.getString(12));
                ic = new Itemcard(id, code, name, category, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
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

        if (item.getPrice().get(0) == 0) {
            utiliMsg.errorPriceItem();
            error = true;
        }
        
        String prices = SalonManager.encrypt(utili.arrayDouToStr(item.getPrice()));

        if (error == false) {
            try {
                String sql = "INSERT INTO itemcards(itemcard_id, itemcard_code, itemcard_name, itemcard_category, itemcard_description, itemcard_cost, itemcard_price,  itemcard_stock, itemcard_date_creation, itemcard_tip, Itemcard_active) "
                        + "VALUES( '" + SalonManager.encryptInteger(item.getId()) + "', '"+ SalonManager.encrypt(item.getCode()) + "', '" + SalonManager.encrypt(item.getName()) + "', '" + SalonManager.encrypt(item.getCategory()) + "','" + SalonManager.encrypt(item.getDescription()) + "','" + SalonManager.encryptDouble(item.getCost()) + "','" + prices + "','" + SalonManager.encryptInteger(item.getStock()) + "','" + SalonManager.encryptTs(item.getDateCreation()) + "', '" + SalonManager.encryptBoolean(item.isActiveTip()) + "', '" + SalonManager.encryptBoolean(item.isActiveItem()) + "');";
                System.out.println(sql);
                insertarModificarEliminar(sql);
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

    public void modificarItem(int id, String name, String category, String description, double cost, ArrayList<Double> price, int stock, boolean tipAlta) throws Exception {
        Timestamp upd = new Timestamp(new Date().getTime());
        String prices = utili.arrayDouToStr(price);
        try {
            String sql = "UPDATE itemcards "
                    + "SET itemcard_name = '" + SalonManager.encrypt(name) + "', itemcard_category = '" + SalonManager.encrypt(category) + "', itemcard_description = '" + SalonManager.encrypt(description)
                    + "', itemcard_cost = '" + SalonManager.encryptDouble(cost) + "', itemcard_price = '" + SalonManager.encrypt(prices) + "', itemcard_stock = '" + SalonManager.encryptInteger(stock)
                    + "', itemcard_date_creation = '" + SalonManager.encryptTs(upd) + "', itemcard_tip = '" + SalonManager.encryptBoolean(tipAlta)
                    + "' WHERE itemcard_id = '" + SalonManager.encryptInteger(id) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
            utiliMsg.cargaSuccesMod();
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

    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    public void saveItemOrderTable(Itemcard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO itemcard_order_tabs( itemcard_order_tab_active, itemcard_order_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encryptBoolean(true) + "', '" + SalonManager.encryptInteger(ic.getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
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
            String sql = "UPDATE itemcard_order_tabs SET itemcard_order_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND itemcard_order_id_fkey = '" + SalonManager.encryptInteger(ic.getId()) + "' AND itemcard_order_tab_active = '" + SalonManager.encryptBoolean(false) + "' LIMIT 1;";
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
            String sql = "UPDATE itemcard_order_tabs SET itemcard_order_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND itemcard_order_id_fkey = '" + SalonManager.encryptInteger(ic.getId()) + "' AND itemcard_order_tab_active = '" + SalonManager.encryptBoolean(true) + "' LIMIT 1;";
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
            String sql = "UPDATE itemcard_order_tabs SET itemcard_order_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
            desconectarBase();
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void upActiveItemOrderTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE itemcard_order_tabs SET itemcard_order_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
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
        ArrayList<Itemcard> items = new ArrayList<>();
        try {
            String sql = "SELECT itemcard_order_id_fkey FROM itemcard_order_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "' AND itemcard_order_tab_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<>();
            while (resultado.next()) {
                int idIc = SalonManager.decryptInteger(resultado.getString(1));
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

    //Tabla Itemcard_gift_tabs-----------------------------------------------------------------------
    //Tabla Itemcard_gift_tabs-----------------------------------------------------------------------
    //Tabla Itemcard_gift_tabs-----------------------------------------------------------------------
    //Tabla Itemcard_gift_tabs-----------------------------------------------------------------------
    public void saveItemGiftTable(Itemcard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO itemcard_gift_tabs(itemcard_gift_tab_active, itemcard_gift_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encryptBoolean(true) + "', '" + SalonManager.encryptInteger(ic.getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
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
            String sql = "UPDATE itemcard_gift_tabs SET itemcard_gift_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND itemcard_gift_id_fkey = '" + SalonManager.encryptInteger(ic.getId()) + "' AND itemcard_gift_tab_active = '" + SalonManager.encryptBoolean(true) + "' LIMIT 1;";
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
            String sql = "UPDATE itemcard_gift_tabs SET itemcard_gift_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
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
            String sql = "UPDATE itemcard_gift_tabs SET itemcard_gift_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + t.getId() + "' AND itemcard_gift_id_fkey = '" + SalonManager.encryptInteger(ic.getId()) + "' AND itemcard_order_id_fkey = '" + SalonManager.encryptInteger(ic.getId()) + "' AND itemcard_gift_tab_active = '" + SalonManager.encryptBoolean(false) + "' LIMIT 1;";
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
        ArrayList<Itemcard> items = new ArrayList<>();
        ArrayList<Integer> idItems = new ArrayList<>();
        try {
            String sql = "SELECT itemcard_gift_id_fkey FROM itemcard_gift_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "' AND itemcard_gift_tab_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int idIc = SalonManager.decryptInteger(resultado.getString(1));
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
            String sql = "INSERT INTO itemcard_payed_tabs(itemcard_payed_tab_active, itemcard_payed_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encryptBoolean(true) + "', '" + SalonManager.encryptInteger(ic.getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
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
            String sql = "UPDATE itemcard_payed_tabs SET itemcard_payed_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND itemcard_payed_id_fkey = " + SalonManager.encryptInteger(ic.getId()) + " AND itemcard_payed_tab_active = '" + SalonManager.encryptBoolean(true) + "' LIMIT 1;";
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
            String sql = "UPDATE itemcard_payed_tabs SET itemcard_payed_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
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
            String sql = "UPDATE itemcard_payed_tabs SET itemcard_payed_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND itemcard_payed_id_fkey = " + SalonManager.encryptInteger(ic.getId()) + " AND itemcard_payed_tab_active = '" + SalonManager.encryptBoolean(false) + "' LIMIT 1;";
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
        ArrayList<Itemcard> items = new ArrayList<>();
        try {
            String sql = "SELECT itemcard_payed_id_fkey FROM itemcard_payed_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "' AND itemcard_payed_tab_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<>();
            while (resultado.next()) {
                int idIc = SalonManager.decryptInteger(resultado.getString(1));
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
            String sql = "INSERT INTO itemcard_payed_nd_tabs(itemcard_payed_nd_tab_active, itemcard_payed_nd_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encryptBoolean(true) + "', '" + SalonManager.encryptInteger(ic.getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
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
            String sql = "UPDATE itemcard_payed_nd_tabs SET itemcard_payed_nd_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND itemcard_payed_nd_id_fkey = " + SalonManager.encryptInteger(ic.getId()) + " AND itemcard_payed_nd_tab_active = '" + SalonManager.encryptBoolean(true) + "' LIMIT 1;";
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
            String sql = "UPDATE itemcard_payed_nd_tabs SET itemcard_payed_nd_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
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
        ArrayList<Itemcard> items = new ArrayList<>();
        try {
            String sql = "SELECT itemcard_payed_nd_id_fkey FROM itemcard_payed_nd_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<>();
            while (resultado.next()) {
                int idIc = SalonManager.decryptInteger(resultado.getString(1));
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
            String sql = "SELECT * FROM itemcards WHERE itemcard_active = '" + SalonManager.encryptBoolean(true) + "' AND itemcard_id = '" + SalonManager.encryptInteger(itemIMon) + "';";
            System.out.println(sql);
            consultarBase(sql);
            Itemcard ic = new Itemcard();
            while (resultado.next()) {
                int id = SalonManager.decryptInteger(resultado.getString(1));
                String code = SalonManager.decrypt(resultado.getString(2));
                String name = SalonManager.decrypt(resultado.getString(3));
                String category = SalonManager.decrypt(resultado.getString(4));
                String description = SalonManager.decrypt(resultado.getString(5));
                double cost = SalonManager.decryptDouble(resultado.getString(6));
                ArrayList<Double> price = utili.strToArrayDou(SalonManager.decrypt(resultado.getString(7)));
                int stock = SalonManager.decryptInteger(resultado.getString(8));
                String create1 = resultado.getString(9);
                if (create1 == null) {
                    create1 = "";
                }
                Timestamp dateCreation = SalonManager.decryptTs(create1);
                String create2 = resultado.getString(10);
                if (create2 == null) {
                    create2 = "";
                }
                Timestamp dateCostUpdate = SalonManager.decryptTs(create2);
                boolean activeTip = SalonManager.decryptBoolean(resultado.getString(11));
                boolean activeItem = SalonManager.decryptBoolean(resultado.getString(12));
                ic = new Itemcard(id, code, name, category, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
            }
            return ic;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public String getItemNameById(int i) throws Exception {
        String name = "";
        try {
            String sql = "SELECT * FROM itemcards WHERE itemcard_id = " + SalonManager.encryptInteger(i) + " AND itemcard_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            Itemcard ic = new Itemcard();
            while (resultado.next()) {
                name = SalonManager.decrypt(resultado.getString(3));
            }
            return name;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }

    }

    public void updateItemCategory(int id, String newCat) throws Exception {
        String sql = "UPDATE itemcards "
                + "SET itemcard_category = '" + SalonManager.encrypt(newCat) + "' WHERE itemcard_id = '" + SalonManager.encryptInteger(id) + "';";
        System.out.println(sql);
        insertarModificarEliminar(sql);
        desconectarBase();
    }

    public void updateItemStock(int id, int stock) throws Exception {
        String sql = "UPDATE itemcards "
                + "SET itemcard_stock = '" + SalonManager.encryptInteger(stock) + "' WHERE itemcard_id = '" + SalonManager.encryptInteger(id) + "';";
        System.out.println(sql);
        insertarModificarEliminar(sql);
        desconectarBase();
    }

    public void updateItemCost(int id, double cost) throws Exception {
        String sql = "UPDATE itemcards "
                + "SET itemcard_cost = '" + SalonManager.encryptDouble(cost) + "' WHERE itemcard_id = '" + SalonManager.encryptInteger(id) + "';";
        System.out.println(sql);
        insertarModificarEliminar(sql);
        desconectarBase();
    }

    public void updateItemPrice(int id, ArrayList<Double> price) throws Exception {
        String st = utili.arrayDouToStr(price);
        String sql = "UPDATE itemcards "
                + "SET itemcard_price = '" + SalonManager.encrypt(st) + "' WHERE itemcard_id = '" + SalonManager.encryptInteger(id) + "';";
        System.out.println(sql);
        insertarModificarEliminar(sql);
        desconectarBase();
    }
    
    
    public int getItemId() throws Exception {
        try {       
            int id = 0;
            String sql = "SELECT COUNT(*) AS cantidad_filas FROM itemcards;";
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

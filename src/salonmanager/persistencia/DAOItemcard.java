package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.ItemCard;
import salonmanager.entidades.bussiness.Table;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOItemCard extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();

    public ArrayList<ItemCard> listarItemsCard() throws Exception {
        ArrayList<ItemCard> items = new ArrayList<>();
        try {
            String sql = "SELECT * FROM item_cards WHERE item_card_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ItemCard ic = new ItemCard();
            while (resultado.next()) {
                int id = SalonManager.decryptInt(resultado.getString(1));
                String name = SalonManager.decrypt(resultado.getString(2));
                String category = SalonManager.decrypt(resultado.getString(3));
                String description = SalonManager.decrypt(resultado.getString(4));
                double cost = SalonManager.decryptDouble(resultado.getString(5));
                ArrayList<Double> price = utili.strToArrayDou(SalonManager.decrypt(resultado.getString(6)));
                int stock = SalonManager.decryptInt(resultado.getString(7));
                Timestamp dateCreation = SalonManager.decryptTs(resultado.getString(8));
                Timestamp dateCostUpdate = SalonManager.decryptTs(resultado.getString(9));
                boolean activeTip = SalonManager.decryptBoolean(resultado.getString(10));
                boolean activeItem = SalonManager.decryptBoolean(resultado.getString(11));
                ic = new ItemCard(id, name, category, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
                items.add(ic);
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
            String sql = "SELECT item_card_id FROM item_cards WHERE item_card_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int id = SalonManager.decryptInt(resultado.getString(1));
                itemsId.add(id);
            }
            return itemsId;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<ItemCard> listItemsByCategory(String cat) throws Exception {
        ArrayList<ItemCard> items = new ArrayList<>();
        try {
            String sql = "SELECT * FROM item_cards WHERE item_card_active = '" + SalonManager.encryptBoolean(true) + "' AND item_card_category = '" + SalonManager.encrypt(cat) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ItemCard ic = new ItemCard();
            while (resultado.next()) {
                int id = SalonManager.decryptInt(resultado.getString(1));
                String name = SalonManager.decrypt(resultado.getString(2));
                String category = SalonManager.decrypt(resultado.getString(3));
                String description = SalonManager.decrypt(resultado.getString(4));
                double cost = SalonManager.decryptDouble(resultado.getString(5));
                ArrayList<Double> price = utili.strToArrayDou(SalonManager.decrypt(resultado.getString(6)));
                int stock = SalonManager.decryptInt(resultado.getString(7));
                Timestamp dateCreation = SalonManager.decryptTs(resultado.getString(8));
                Timestamp dateCostUpdate = SalonManager.decryptTs(resultado.getString(9));
                boolean activeTip = SalonManager.decryptBoolean(resultado.getString(10));
                boolean activeItem = SalonManager.decryptBoolean(resultado.getString(11));
                ic = new ItemCard(id, name, category, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
                items.add(ic);
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void saveItemcard(ItemCard item) throws Exception {
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
                String sql = "INSERT INTO item_cards(item_card_id, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) "
                        + "VALUES( '" + SalonManager.encryptInt(item.getId()) + "', '" + SalonManager.encrypt(item.getName()) + "', '" + SalonManager.encrypt(item.getCategory()) + "','" + SalonManager.encrypt(item.getDescription()) + "','" + SalonManager.encryptDouble(item.getCost()) + "','" + prices + "', '" + SalonManager.encryptInt(item.getStock()) + "', '" + SalonManager.encryptTs(item.getDateCreation()) + "', '" + SalonManager.encryptBoolean(item.isActiveTip()) + "', '" + SalonManager.encryptBoolean(item.isActiveItem()) + "');";
                System.out.println(sql);
                insertarModificarEliminar(sql);
                utiliMsg.cargaItem();
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    utiliMsg.errorCargaDB();
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    public void modificarItem(int id, String name, String category, String description, double cost, ArrayList<Double> price, int stock, boolean tipAlta) throws Exception {
        Timestamp upd = new Timestamp(new Date().getTime());
        String prices = utili.arrayDouToStr(price);
        try {
            String sql = "UPDATE item_cards "
                    + "SET item_card_name = '" + SalonManager.encrypt(name) + "', item_card_category = '" + SalonManager.encrypt(category) + "', item_card_description = '" + SalonManager.encrypt(description)
                    + "', item_card_cost = '" + SalonManager.encryptDouble(cost) + "', item_card_price = '" + SalonManager.encrypt(prices) + "', item_card_stock = '" + SalonManager.encryptInt(stock)
                    + "', item_card_date_creation = '" + SalonManager.encryptTs(upd) + "', item_card_tip = '" + SalonManager.encryptBoolean(tipAlta)
                    + "' WHERE item_card_id = " + SalonManager.encryptInt(id) + ";";
            System.out.println(sql);
            insertarModificarEliminar(sql);

            utiliMsg.cargaSuccesMod();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        }
    }

    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    //Tabla ItemcardsOrder_tabs-----------------------------------------------------------------------
    public void saveItemOrderTable(ItemCard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO item_card_order_tabs( item_card_order_tab_active, item_card_order_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encryptBoolean(true) + "', '" + SalonManager.encryptInt(ic.getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void upActiveItemOrderTable(ItemCard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_order_tabs SET item_card_order_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND item_card_order_id_fkey = '" + SalonManager.encryptInt(ic.getId()) + "' AND item_card_order_tab_active = '" + SalonManager.encryptBoolean(false) + "' LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void downActiveItemOrderTable(ItemCard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_order_tabs SET item_card_order_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND item_card_order_id_fkey = '" + SalonManager.encryptInt(ic.getId()) + "' AND item_card_order_tab_active = '" + SalonManager.encryptBoolean(true) + "' LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void downActiveItemOrderTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_order_tabs SET item_card_order_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public void upActiveItemOrderTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_order_tabs SET item_card_order_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<ItemCard> listarItemcardOrder(String tabId) throws Exception {
        ArrayList<ItemCard> items = new ArrayList<>();
        try {
            String sql = "SELECT item_card_order_id_fkey FROM item_card_order_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "' AND item_card_order_tab_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<>();
            while (resultado.next()) {
                int idIc = SalonManager.decryptInt(resultado.getString(1));
                idItems.add(idIc);
            }

            for (int i = 0; i < idItems.size(); i++) {
                ArrayList<ItemCard> itemsCompleta = listarItemsCard();
                for (int j = 0; j < itemsCompleta.size(); j++) {
                    if (idItems.get(i) == itemsCompleta.get(j).getId()) {
                        ItemCard ic = itemsCompleta.get(j);
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
    public void saveItemGiftTable(ItemCard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO item_card_gift_tabs(item_card_gift_tab_active, item_card_gift_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encryptBoolean(true) + "', '" + SalonManager.encryptInt(ic.getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void downActiveItemGiftTable(ItemCard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_gift_tabs SET item_card_gift_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND item_card_gift_id_fkey = '" + SalonManager.encryptInt(ic.getId()) + "' AND item_card_gift_tab_active = '" + SalonManager.encryptBoolean(true) + "' LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public void downActiveItemGiftTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_gift_tabs SET item_card_gift_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public void upActiveItemGiftTable(Table t, ItemCard ic) throws Exception {
        try {
            String sql = "UPDATE item_card_gift_tabs SET item_card_gift_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND item_card_gift_id_fkey = '" + SalonManager.encryptInt(ic.getId()) + "' AND item_card_order_id_fkey = '" + SalonManager.encryptInt(ic.getId()) + "' AND item_card_gift_tab_active = '" + SalonManager.encryptBoolean(false) + "' LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<ItemCard> listarItemcardGifts(String tabId) throws Exception {
        ArrayList<ItemCard> items = new ArrayList<>();
        ArrayList<Integer> idItems = new ArrayList<>();
        try {
            String sql = "SELECT item_card_gift_id_fkey FROM item_card_gift_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "' AND item_card_gift_tab_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                int idIc = SalonManager.decryptInt(resultado.getString(1));
                idItems.add(idIc);
            }

            for (int i = 0; i < idItems.size(); i++) {
                ArrayList<ItemCard> itemsCompleta = listarItemsCard();
                for (int j = 0; j < itemsCompleta.size(); j++) {
                    if (idItems.get(i) == itemsCompleta.get(j).getId()) {
                        ItemCard ic = itemsCompleta.get(j);
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
    public void saveItemPayedTable(ItemCard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO item_card_payed_tabs(item_card_payed_tab_active, item_card_payed_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encryptBoolean(true) + "', '" + SalonManager.encryptInt(ic.getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void downActiveItemPayedTable(ItemCard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_payed_tabs SET item_card_payed_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND item_card_payed_id_fkey = '" + SalonManager.encryptInt(ic.getId()) + "' AND item_card_payed_tab_active = '" + SalonManager.encryptBoolean(true) + "' LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public void downActiveItemPayedTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_payed_tabs SET item_card_payed_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public void upActiveItemPayedTable(ItemCard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_payed_tabs SET item_card_payed_tab_active = '" + SalonManager.encryptBoolean(true) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND item_card_payed_id_fkey = '" + SalonManager.encryptInt(ic.getId()) + "' AND item_card_payed_tab_active = '" + SalonManager.encryptBoolean(false) + "' LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<ItemCard> listarItemcardPartialPayed(String tabId) throws Exception {
        ArrayList<ItemCard> items = new ArrayList<>();
        try {
            String sql = "SELECT item_card_payed_id_fkey FROM item_card_payed_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "' AND item_card_payed_tab_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<>();
            while (resultado.next()) {
                int idIc = resultado.getInt(1);
                idItems.add(idIc);
            }

            for (int i = 0; i < idItems.size(); i++) {
                ArrayList<ItemCard> itemsCompleta = listarItemsCard();
                for (int j = 0; j < itemsCompleta.size(); j++) {
                    if (idItems.get(i) == itemsCompleta.get(j).getId()) {
                        ItemCard ic = itemsCompleta.get(j);
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
    public void saveItemPayedNDTable(ItemCard ic, Table tab) throws Exception {
        try {
            String sql = "INSERT INTO item_card_payed_nd_tabs(item_card_payed_nd_tab_active, item_card_payed_nd_id_fkey, table_id_fkey) ";
            String parcialA = "VALUES('" + SalonManager.encryptBoolean(true) + "', '" + SalonManager.encryptInt(ic.getId()) + "', '" + SalonManager.encrypt(tab.getId()) + "');";
            sql += parcialA;
            System.out.println(sql);
            insertarModificarEliminar(sql.trim());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.cargaError();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void downActiveItemPayedNDTable(ItemCard ic, Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_payed_nd_tabs SET item_card_payed_nd_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "' AND item_card_payed_nd_id_fkey = '" + SalonManager.encryptInt(ic.getId()) + "' AND item_card_payed_nd_tab_active = '" + SalonManager.encryptBoolean(true) + "' LIMIT 1;";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public void downActiveItemPayedNDTableAll(Table t) throws Exception {
        try {
            String sql = "UPDATE item_card_payed_nd_tabs SET item_card_payed_nd_tab_active = '" + SalonManager.encryptBoolean(false) + "' WHERE table_id_fkey = '" + SalonManager.encrypt(t.getId()) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);

        } catch (Exception e) {
            throw e;
        }
    }

    public ArrayList<ItemCard> listarItemcardPartialPayedND(String tabId) throws Exception {
        ArrayList<ItemCard> items = new ArrayList<>();
        try {
            String sql = "SELECT item_card_payed_nd_id_fkey FROM item_card_payed_nd_tabs WHERE table_id_fkey = '" + SalonManager.encrypt(tabId) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> idItems = new ArrayList<>();
            while (resultado.next()) {
                int idIc = SalonManager.decryptInt(resultado.getString(1));
                idItems.add(idIc);
            }
            for (int i = 0; i < idItems.size(); i++) {
                ArrayList<ItemCard> itemsCompleta = listarItemsCard();
                for (int j = 0; j < itemsCompleta.size(); j++) {
                    if (idItems.get(i) == itemsCompleta.get(j).getId()) {
                        ItemCard ic = itemsCompleta.get(j);
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

    public ItemCard getItemById(int itemIMon) throws Exception {
        try {
            String sql = "SELECT * FROM item_cards WHERE item_card_active = '" + SalonManager.encryptBoolean(true) + "' AND item_card_id = '" + SalonManager.encryptInt(itemIMon) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ItemCard ic = new ItemCard();
            while (resultado.next()) {
                int id = resultado.getInt(1);
                String name = SalonManager.decrypt(resultado.getString(2));
                String category = SalonManager.decrypt(resultado.getString(3));
                String description = SalonManager.decrypt(resultado.getString(4));
                double cost = SalonManager.decryptDouble(resultado.getString(5));
                ArrayList<Double> price = utili.strToArrayDou(SalonManager.decrypt(resultado.getString(6)));
                int stock = SalonManager.decryptInt(resultado.getString(7));
                Timestamp dateCreation = SalonManager.decryptTs(resultado.getString(8));
                Timestamp dateCostUpdate = SalonManager.decryptTs(resultado.getString(9));
                boolean activeTip = SalonManager.decryptBoolean(resultado.getString(10));
                boolean activeItem = SalonManager.decryptBoolean(resultado.getString(11));
                ic = new ItemCard(id, name, category, description, cost, price, stock, dateCreation, dateCostUpdate, activeTip, activeItem);
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
            String sql = "SELECT item_card_name FROM item_cards WHERE item_card_id = '" + SalonManager.encryptInt(i) + "' AND item_card_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ItemCard ic = new ItemCard();
            while (resultado.next()) {
                name = SalonManager.decrypt(resultado.getString(1));
            }
            return name;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public int getItemStockById(int i) throws Exception {
        int stock = 0;
        try {
            String sql = "SELECT item_card_stock FROM item_cards WHERE item_card_id = '" + SalonManager.encryptInt(i) + "' AND item_card_active = '" + SalonManager.encryptBoolean(true) + "';";
            System.out.println(sql);
            consultarBase(sql);
            ItemCard ic = new ItemCard();
            while (resultado.next()) {
                stock = SalonManager.decryptInt(resultado.getString(1));
            }
            return stock;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }

    }

    public void updateItemCategory(int id, String newCat) throws Exception {
        try {
            String sql = "UPDATE item_cards "
                    + "SET item_card_category = '" + SalonManager.encrypt(newCat) + "' WHERE item_card_id = '" + SalonManager.encryptInt(id) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        }

    }

    public void updateItemStock(int id, int stock) throws Exception {
        try {
            String sql = "UPDATE item_cards "
                    + "SET item_card_stock = '" + SalonManager.encryptInt(stock) + "' WHERE item_card_id = '" + SalonManager.encryptInt(id) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        }

    }

    public void updateItemStockUpDown(ItemCard ic, boolean upDown) throws Exception {
        int id = ic.getId();
        int stock = getItemStockById(id);
        if (upDown) {
            stock += 1;
        } else {
            stock -= 1;
        }

        try {
            String sql = "UPDATE item_cards "
                    + "SET item_card_stock = '" + SalonManager.encryptInt(stock) + "' WHERE item_card_id = '" + SalonManager.encryptInt(id) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateItemCost(int id, double cost) throws Exception {
        try {
            String sql = "UPDATE item_cards "
                    + "SET item_card_cost = '" + SalonManager.encryptDouble(cost) + "' WHERE item_card_id = '" + SalonManager.encryptInt(id) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateItemPrice(int id, ArrayList<Double> price) throws Exception {
        try {
            String st = utili.arrayDouToStr(price);
            String sql = "UPDATE item_cards "
                    + "SET item_card_price = '" + SalonManager.encrypt(st) + "' WHERE item_card_id = '" + SalonManager.encryptInt(id) + "';";
            System.out.println(sql);
            insertarModificarEliminar(sql);
        } catch (Exception e) {
            throw e;
        }
    }

    public int getItemId() throws Exception {
        try {
            int id = 0;
            String sql = "SELECT COUNT(*) AS cantidad_filas FROM item_cards;";
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

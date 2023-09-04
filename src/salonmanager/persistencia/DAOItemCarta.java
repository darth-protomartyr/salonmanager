package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.entidades.ItemCarta;
import salonmanager.servicios.ServicioRegister;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOItemCarta extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServicioRegister sr = new ServicioRegister();
    public ArrayList<ItemCarta> listarItemsCarta() throws Exception {
        ArrayList<ItemCarta> items = new ArrayList<ItemCarta>();
        try {
            String sql = "SELECT * FROM itemscarta recetas WHERE itemcarta_alta = true;";
            System.out.println(sql);
            consultarBase(sql);
            ItemCarta ic = new ItemCarta();
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
                boolean altaTip = resultado.getBoolean(11);
                boolean altaItem = resultado.getBoolean(12);
                ic = new ItemCarta(id, code, name, caption, description, cost, price, stock, dateCreation, dateCostUpdate, altaTip, altaItem);
                items.add(ic);
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public ArrayList<ItemCarta> listItemsByCaption(String capt) throws Exception {
        ArrayList<ItemCarta> items = new ArrayList<ItemCarta>();
        try {
            String sql = "SELECT * FROM itemscarta WHERE itemCarta_alta = true AND itemCarta_caption = '" + capt + "';";
            System.out.println(sql);
            consultarBase(sql);
            ItemCarta ic = new ItemCarta();
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
                boolean altaTip = resultado.getBoolean(11);
                boolean altaItem = resultado.getBoolean(12);
                ic = new ItemCarta(id, code, name, caption, description, cost, price, stock, dateCreation, dateCostUpdate, altaTip, altaItem);
                items.add(ic);
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }

    public void guardarItemCarta(ItemCarta item) throws Exception {
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
                String sql = "INSERT INTO itemsCarta(itemCarta_code, itemCarta_name, itemCarta_caption, itemCarta_description, itemCarta_stock, itemCarta_cost, itemCarta_price, itemCarta_dateCreation, itemCarta_tip, itemCarta_alta) "
                        + "VALUES( '" + item.getCode() + "','" + item.getName() + "','" + item.getCaption() + "','" + item.getDescription() + "','" + item.getStock() + "','" + item.getCost() + "','" + item.getPrice() + "','" + item.getDateCreation() + "'," + item.isAltaTip() + ", " + item.isAltaItem() + ");";
                System.out.println(sql);
                insertarModificarEliminar(sql);
                String values = item.getCaption() + " - " + item.getDescription() + " - " + item.getStock() + " - " + item.getCost() + " - " + item.getPrice() + " - " +  item.getDateCreation() + " - " + " - " + item.isAltaTip() + " - " + item.isAltaItem() + " - ";
                sr.crearRegistro("guardarIngrediente", item.getName(), values);
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
}

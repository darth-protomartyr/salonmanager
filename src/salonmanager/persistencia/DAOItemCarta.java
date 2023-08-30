package salonmanager.persistencia;

import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.entidades.ItemCarta;

public class DAOItemCarta extends DAO {
    public ArrayList<ItemCarta> listarItemsCarta() throws Exception {
        ArrayList<ItemCarta> items = new ArrayList<ItemCarta>();
        try {
            String sql = "SELECT itemscarta * FROM recetas WHERE itemcarta_alta = true";
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
                boolean altaItem = resultado.getBoolean(11);
                ic = new ItemCarta(id, code, name, caption, description, cost, price, stock, dateCreation, dateCostUpdate, altaItem);
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
            String sql = "SELECT itemscarta * FROM recetas WHERE itemcarta_alta = true AND '" + capt + "';"  ;
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
                boolean altaItem = resultado.getBoolean(11);
                ic = new ItemCarta(id, code, name, caption, description, cost, price, stock, dateCreation, dateCostUpdate, altaItem);
                items.add(ic);
            }
            return items;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }
}

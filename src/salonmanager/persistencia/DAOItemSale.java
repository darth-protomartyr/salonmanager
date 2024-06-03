/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.entidades.bussiness.Table;
import salonmanager.utilidades.UtilidadesMensajes;

/**
 *
 * @author Gonzalo
 */
public class DAOItemSale extends DAO {
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveItemSale(ItemSale itemSale) throws Exception {
        try {
            String sql = "INSERT INTO item_sales_statics( item_sale_id, item_sale_waiter_id, item_sale_category, item_sale_tab_pos, item_sale_workshift_id, item_sale_price, item_sale_date, item_sale_active)"
                    + "VALUES(" + itemSale.getItemSaleId() + ", '" + itemSale.getItemSaleWaiterId() + "', '" + itemSale.getItemSaleCategory() + "', '"  + itemSale.getItemSaleTabPos() +  "', "   + itemSale.getItemSaleWorkshiftId() + ", " + itemSale.getItemSalePrice() + ", '" + itemSale.getItemSaleDate() + "', " + true + ");";
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
    
    
    public ArrayList<ItemSale> askItemSaleByWorkshift(int id) throws Exception {
        ArrayList<ItemSale> itemSales = new ArrayList<>();
        ArrayList<Integer> itemIds = new ArrayList<>();
        try {
            String sql = "SELECT item_sale_statics_id FROM item_sales_statics WHERE item_sale_workshift = id";
            System.out.println(sql);
            consultarBase(sql);
            while(resultado.next()) {
                int i = resultado.getInt(1);
                itemIds.add(i);
            }

            for(Integer i : itemIds) {
                ItemSale is = askItemSaleById(i);
                itemSales.add(is);
            }
            
        } catch(Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
        return itemSales;        
    } 
    
    
    public ItemSale askItemSaleById(int id) throws Exception {
        ItemSale iS = new ItemSale();
        try {
            String sql = "SELECT * FROM item_sales_statics WHERE item_sale_statics_id = " + id + ";";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                iS.setSaleId(resultado.getInt(1));
                iS.setItemSaleId(resultado.getInt(2));
                iS.setItemSaleCategory(resultado.getString(3));
                iS.setItemSaleTabPos(resultado.getString(4));
                iS.setItemSaleWaiterId(resultado.getString(5));
                iS.setItemSaleWorkshiftId(resultado.getInt(6));
                iS.setItemSalePrice(resultado.getDouble(7));
                iS.setItemSaleDate(resultado.getTimestamp(8));
                iS.setItemSaleActive(resultado.getBoolean(9));
            }
            return iS;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }
    }


    public ArrayList<ItemSale> listarItemSalesByDate(Timestamp open, Timestamp close) throws Exception {
        ArrayList<ItemSale> listISale = new ArrayList<>();
        try {
            String sql = "SELECT item_sale_statics_id FROM item_sales_statics WHERE item_sale_date >= '" + open + "' AND item_sale_date <= '" + close + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> ids = new ArrayList<>();
            while (resultado.next()) {
                int id = 0; 
                id = resultado.getInt(1);
                ids.add(id);
            }
            
            for (int id : ids) {
                ItemSale is = askItemSaleById(id);
                listISale.add(is);
            }
            
            return listISale;
        } catch (Exception e) {
            throw e;
        } finally {
            desconectarBase();
        }        

    }
}
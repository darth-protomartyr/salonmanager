/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package salonmanager.persistencia;

import java.sql.SQLException;
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
            String sql = "INSERT INTO item_sales_statics( item_sale_id, item_sale_waiter_id, item_sale_caption, item_sale_tab_pos, item_sale_workshift_id, item_sale_price, item_sale_date, item_sale_active)"
                    + "VALUES(" + itemSale.getItemSaleId() + ", '" + itemSale.getItemSaleWaiterId() + "', '" + itemSale.getItemSaleCaption() + "', '"  + itemSale.getItemSaleTabPos() +  "', "   + itemSale.getItemSaleWorkshiftId() + ", " + itemSale.getItemSalePrice() + ", '" + itemSale.getItemSaleDate() + "', " + true + ");";
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
        ArrayList<ItemSale> itemSales = new ArrayList<ItemSale>();
        ArrayList<Integer> itemIds = new ArrayList<Integer>();
        try {
            String sql = "SELECT item_sale_statics_id FROM item_sale_statics WHERE item_sale_workshift = id";
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
            String sql = "SELECT * FROM item_sale_statics WHERE item_sale_statics_id = " + id + ";";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                iS.setSaleId(resultado.getInt(1));
                iS.setItemSaleId(resultado.getInt(2));
                iS.setItemSaleCaption(resultado.getString(3));
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
}
package salonmanager.persistencia;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import salonmanager.SalonManager;
import salonmanager.entidades.bussiness.ItemSale;
import salonmanager.utilidades.UtilidadesMensajes;


public class DAOItemSale extends DAO {
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveItemSale(ItemSale itemSale) throws Exception {
        int id = getItemSaleId();
        itemSale.setSaleId(id);
        try {
            String sql = "INSERT INTO item_sales_statics( item_sale_static_id, item_sale_id, item_sale_waiter_id, item_sale_category, item_sale_tab_pos, item_sale_workshift_id, item_sale_price, item_sale_date, item_sale_active)"
                    + "VALUES('" + SalonManager.encryptInt(id) + "', '" + SalonManager.encryptInt(itemSale.getItemSaleId()) + "', '" + SalonManager.encrypt(itemSale.getItemSaleWaiterId()) + "', '" + SalonManager.encrypt(itemSale.getItemSaleCategory()) + "', '"  + SalonManager.encrypt(itemSale.getItemSaleTabPos()) +  "', '"   + SalonManager.encryptInt(itemSale.getItemSaleWorkshiftId()) + "', '" + SalonManager.encryptDouble(itemSale.getItemSalePrice()) + "', '" + itemSale.getItemSaleDate() + "', '" + SalonManager.encryptBoolean(true) + "');";
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
            String sql = "SELECT item_sale_static_id FROM item_sales_statics WHERE item_sale_workshift = '" + SalonManager.encryptInt(id) +"';";
            System.out.println(sql);
            consultarBase(sql);
            while(resultado.next()) {
                int i = SalonManager.decryptInt(resultado.getString(1));
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
            String sql = "SELECT * FROM item_sales_statics WHERE item_sale_static_id = '" + SalonManager.encryptInt(id) + "';";
            System.out.println(sql);
            consultarBase(sql);
            while (resultado.next()) {
                iS.setSaleId(SalonManager.decryptInt(resultado.getString(1)));
                iS.setItemSaleId(SalonManager.decryptInt(resultado.getString(2)));
                iS.setItemSaleCategory(SalonManager.decrypt(resultado.getString(3)));
                iS.setItemSaleTabPos(SalonManager.decrypt(resultado.getString(4)));
                iS.setItemSaleWaiterId(SalonManager.decrypt(resultado.getString(5)));
                iS.setItemSaleWorkshiftId(SalonManager.decryptInt(resultado.getString(6)));
                iS.setItemSalePrice(SalonManager.decryptDouble(resultado.getString(7)));
                iS.setItemSaleDate(resultado.getTimestamp(8));
                iS.setItemSaleActive(SalonManager.decryptBoolean(resultado.getString(9)));
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
            String sql = "SELECT item_sale_static_id FROM item_sales_statics WHERE item_sale_date >= '" + open + "' AND item_sale_date <= '" + close + "';";
            System.out.println(sql);
            consultarBase(sql);
            ArrayList<Integer> ids = new ArrayList<>();
            while (resultado.next()) {
                int id = 0; 
                id = SalonManager.decryptInt(resultado.getString(1));
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
    
    public int getItemSaleId() throws Exception {
        try {       
            int id = 0;
            String sql = "SELECT COUNT(*) AS cantidad_filas FROM item_sales_statics;";
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
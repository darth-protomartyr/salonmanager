package salonmanager.persistencia;

import salonmanager.utilidades.UtilidadesMensajes;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOInit extends DAO {

    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void createTables() throws SQLException, Exception {
        Statement stmt = null;
        try {
            String sqlCfgGen = "CREATE TABLE IF NOT EXISTS config_general("
                    + "	config_table_total VARCHAR(100)," /*nro total de tabs*/
                    + " config_table_num_panes VARCHAR(100)," /*número de mesas por pane*/
                    + " config_table_name_categories VARCHAR(2000)," /*nombre de rubros*/
                    + "	config_table_name_panes VARCHAR(2000)," /*nombre de cada pane*/
                    + " config_table_chart_panes VARCHAR(200)," /*Inicial de cada pane*/
                    + " config_table_tip VARCHAR(100)," /*porcentaje propina*/
                    + " config_active VARCHAR(100)"
                    + ");";
            insertarModificarEliminar(sqlCfgGen);

                String sqlAct = "CREATE TABLE IF NOT EXISTS config_actual("
                        + " config_open_ws VARCHAR(100),"/*turno actual abierto*/
                        + " config_open_ws_id VARCHAR(100)," /*openWsId*/
                        + " congif_defer_close_ws VARCHAR(5000),"
                        + " congif_unmod_tabs VARCHAR(5000)"
                        + ");";
                insertarModificarEliminar(sqlAct);

                String sqlIC = "CREATE TABLE IF NOT EXISTS item_cards("
                        + " item_card_id VARCHAR(100) PRIMARY KEY,"
                        + " item_card_code VARCHAR(100) UNIQUE,"
                        + " item_card_name VARCHAR(100),"
                        + " item_card_category VARCHAR(100),"
                        + " item_card_description VARCHAR(2500),"
                        + " item_card_cost VARCHAR(100),"
                        + " item_card_price VARCHAR(100),"
                        + " item_card_stock VARCHAR(100),"
                        + " item_card_date_creation TIMESTAMP(3),"
                        + " item_card_date_update TIMESTAMP(3),"
                        + " item_card_tip VARCHAR(100),"
                        + " item_card_active VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlIC);

                String sqlTabs = "CREATE TABLE IF NOT EXISTS tabs("
                        + "	table_num VARCHAR(100),"
                        + " table_pos VARCHAR(100),"
                        + " table_open_time TIMESTAMP(3),"
                        + " table_close_time TIMESTAMP(3),"
                        + " table_id VARCHAR(100) PRIMARY KEY,"
                        + " table_open VARCHAR(100),"
                        + " table_bill VARCHAR(100),"
                        + " table_to_pay VARCHAR(100),"
                        + " table_discount VARCHAR(100),"
                        + " table_error VARCHAR(100),"
                        + " table_price_correction VARCHAR(100),"
                        + " table_amount_cash VARCHAR(100),"
                        + " table_amount_electronic VARCHAR(100),"
                        + " table_total VARCHAR(100),"
                        + " table_comments VARCHAR(500),"
                        + " table_active VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlTabs);

                String sqlWs = "CREATE TABLE IF NOT EXISTS workshifts("
                        + "	workshift_id VARCHAR(100) PRIMARY KEY,"
                        + " workshift_open_time_shift TIMESTAMP(3),"
                        + "	workshift_close_time_shift TIMESTAMP(3),"
                        + "	workshift_state_shift VARCHAR(100),"
                        + "	workshift_mount_cash VARCHAR(100),"
                        + "	workshift_mount_electronic VARCHAR(100),"
                        + "	workshift_total_mount_tabs VARCHAR(100),"
                        + "	workshift_total_mount_ws VARCHAR(100),"
                        + "	workshift_error_mount_tabs VARCHAR(100),"
                        + "	workshift_error_mount_ws VARCHAR(100),"
                        + " workshift_cash_flow_cash VARCHAR(100),"
                        + " workshift_cash_flow_elec VARCHAR(100),"
                        + " workshift_comment VARCHAR(5000),"
                        + " workshift_error VARCHAR(100),"
                        + " workshift_active VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlWs);

                String sqlUsers = "CREATE TABLE IF NOT EXISTS users("
                        + " user_id VARCHAR(200) UNIQUE PRIMARY KEY,"
                        + " user_name VARCHAR(100),"
                        + " user_last_name VARCHAR(100),"
                        + " user_mail VARCHAR(100) UNIQUE,"
                        + " user_role VARCHAR(100),"
                        + " user_image_route VARCHAR(5000),"
                        + " user_image_name VARCHAR(100),"
                        + " user_password VARCHAR(100),"
                        + " user_phone VARCHAR(100) UNIQUE,"
                        + " user_active VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlUsers);

                String sqlCat = "CREATE TABLE IF NOT EXISTS categories("
                        + " category_name VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlCat);

                String sqlSpa = "CREATE TABLE IF NOT EXISTS spaces("
                        + " space_name VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlSpa);

                String sqlCh = "CREATE TABLE IF NOT EXISTS chars("
                        + " char_name VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlCh);

                String sqlDeli = "CREATE TABLE IF NOT EXISTS deliverys("
                        + "	delivery_id VARCHAR(100),"
                        + " delivery_consumer_phone VARCHAR(100),"
                        + " delivery_tab_id VARCHAR(100),"
                        + " delivery_user_id VARCHAR(30),"
                        + " delivery_open VARCHAR(100),"
                        + " delivery_active VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlDeli);

                String sqlDeliCl = "CREATE TABLE IF NOT EXISTS delivery_clients ("
                        + "	delivery_client_id VARCHAR(100) PRIMARY KEY,"
                        + " delivery_client_street VARCHAR(100),"
                        + " delivery_client_street_num VARCHAR(100),"
                        + " delivery_client_dept_floor VARCHAR(100),"
                        + " delivery_client_dept_num VARCHAR(100),"
                        + " delivery_client_district VARCHAR(100),"
                        + " delivery_client_area VARCHAR(100),"
                        + " delivery_client_details VARCHAR(5000),"
                        + " delivery_client_name VARCHAR(100),"
                        + " delivery_client_phone VARCHAR(100),"
                        + " delivery_client_social_network VARCHAR(100),"
                        + " delivery_client_active VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlDeliCl);

                String sqlICOT = "CREATE TABLE IF NOT EXISTS item_card_order_tabs("
                        + " item_card_order_tab_id VARCHAR(100),"
                        + " item_card_order_tab_active VARCHAR(100),"
                        + " item_card_order_id_fkey VARCHAR(100),"
                        + " table_id_fkey VARCHAR(100),"
                        + " FOREIGN KEY (item_card_order_id_fkey) REFERENCES item_cards(item_card_id),"
                        + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                        + ");";
                insertarModificarEliminar(sqlICOT);

                String sqlICGT = "CREATE TABLE IF NOT EXISTS item_card_gift_tabs("
                        + " item_card_gift_tab_id VARCHAR(100), "
                        + " item_card_gift_tab_active VARCHAR(100),"
                        + " item_card_gift_id_fkey VARCHAR(100),"
                        + " table_id_fkey VARCHAR(100),"
                        + " FOREIGN KEY (item_card_gift_id_fkey) REFERENCES item_cards(item_card_id),"
                        + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                        + ");";
                insertarModificarEliminar(sqlICGT);

                String sqlICPT = "CREATE TABLE IF NOT EXISTS item_card_payed_tabs("
                        + " item_card_payed_tab_id VARCHAR(100), "
                        + " item_card_payed_tab_active VARCHAR(100),"
                        + " item_card_payed_id_fkey VARCHAR(100),"
                        + " table_id_fkey VARCHAR(100),"
                        + " FOREIGN KEY (item_card_payed_id_fkey) REFERENCES item_cards(item_card_id),"
                        + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                        + ");";
                insertarModificarEliminar(sqlICPT);

                String sqlICPNT = "CREATE TABLE IF NOT EXISTS item_card_payed_nd_tabs("
                        + " item_card_payed_nd_tab_id VARCHAR(100),"
                        + " item_card_payed_nd_tab_active VARCHAR(100),"
                        + " item_card_payed_nd_id_fkey VARCHAR(100),"
                        + " table_id_fkey VARCHAR(100),"
                        + " FOREIGN KEY (item_card_payed_nd_id_fkey) REFERENCES item_cards(item_card_id),"
                        + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                        + ");";
                insertarModificarEliminar(sqlICPNT);

                String sqlIM = "CREATE TABLE IF NOT EXISTS item_monits("
                        + "	item_monit_id VARCHAR(100),"
                        + " item_monit_table_id VARCHAR(100),"
                        + " item_monit_item_id VARCHAR(100),"
                        + " item_monit_tipe VARCHAR(100),"
                        + " item_monit_init_bool VARCHAR(100),"
                        + " item_monit_init_date DATETIME(3),"
                        + " item_monit_cook_bool VARCHAR(100),"
                        + " item_monit_cook_date DATETIME(3),"
                        + " item_monit_ready_bool VARCHAR(100),"
                        + " item_monit_ready_date DATETIME(3),"
                        + " item_monit_otw_bool VARCHAR(100),"
                        + " item_monit_otw_date DATETIME(3),"
                        + " item_monit_open VARCHAR(100),"
                        + " item_monit_active VARCHAR(100),"
                        + " item_monit_indications VARCHAR(5000)"
                        + ");";
                insertarModificarEliminar(sqlIM);

                String sqlISS = "CREATE TABLE IF NOT EXISTS item_sales_statics("
                        + " item_sale_static_id VARCHAR(100),"
                        + " item_sale_id VARCHAR(100),"
                        + "	item_sale_category VARCHAR(100),"
                        + " item_sale_tab_pos VARCHAR(100),"
                        + " item_sale_waiter_id VARCHAR(100),"
                        + " item_sale_workshift_id VARCHAR(100),"
                        + " item_sale_price VARCHAR(100),"
                        + " item_sale_date DATETIME(3),"
                        + " item_sale_active VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlISS);

                String sqlMF = "CREATE TABLE IF NOT EXISTS money_flows("
                        + " money_flow_id VARCHAR(100) PRIMARY KEY,"
                        + "	money_flow_kind VARCHAR(100),"
                        + " money_flow_m_k VARCHAR(100),"
                        + " money_flow_amount VARCHAR(100),"
                        + "	money_flow_comment VARCHAR(5000),"
                        + " money_flow_date TIMESTAMP(3),"
                        + "	money_flow_ws_id VARCHAR(100),"
                        + " money_flow_active VARCHAR(100)"
                        + ");";
                insertarModificarEliminar(sqlMF);

                String sqlWT = "CREATE TABLE IF NOT EXISTS waiter_tabs("
                        + " waiter_tab_id VARCHAR(100),"
                        + " waiter_tab_active VARCHAR(100),"
                        + " waiter_id_fkey VARCHAR(200),"
                        + " table_id_fkey VARCHAR(100),"
                        + "	FOREIGN KEY (waiter_id_fkey) REFERENCES users(user_id),"
                        + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                        + ");";
                insertarModificarEliminar(sqlWT);

                String sqlCW = "CREATE TABLE IF NOT EXISTS cashier_workshifts("
                        + " cashier_workshift_id INT PRIMARY KEY AUTO_INCREMENT,"
                        + " cashier_workshift_active VARCHAR(100),"
                        + " cashier_id_fkey VARCHAR(200),"
                        + " workshift_id_fkey VARCHAR(100),"
                        + " FOREIGN KEY (cashier_id_fkey) REFERENCES users(user_id),"
                        + " FOREIGN KEY (workshift_id_fkey) REFERENCES workshifts(workshift_id)"
                        + ");";
                insertarModificarEliminar(sqlCW);

                String sqlR = "CREATE TABLE IF NOT EXISTS registers("
                        + "	register_id INT AUTO_INCREMENT PRIMARY KEY,"
                        + " register_ejecution DATETIME(3),"
                        + " register_user VARCHAR(100),"
                        + " register_user_modify VARCHAR(100),"
                        + " register_operation VARCHAR(100),"
                        + " register_object VARCHAR(100),"
                        + " register_modification VARCHAR(2000)"
                        + ");";
                insertarModificarEliminar(sqlR);
                            
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void fullerTables() throws SQLException, Exception {
        Statement stmt = null;
        try {
            /*NEED*/
            String sql1 = "UPDATE config_general SET config_active = 'K992SZTEVF57Ki/fu/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u';";
            insertarModificarEliminar(sql1);

            String sql2 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active) VALUES('c2OuJxQqABPRxKy/kPXW2A==', '5FB3bF4LheVY0H6KFiHt/g==', '+RzQUjtQ3UxomiHSZ0fmuQ==', 'S9FqHlkcN9TEgUKgVz/iTA==', 'M88re0RIWY4NF0w7/z6VRg==', 'CtarrfvT/WbyQwRI4y+J7ZxL3mwfw49jKVkGIX6LMVbnHbXQ/sEcQvTwCi+undDi7qqcr9bQj+WqoYWWXlInl/D4zUgkXSKu/0DOY7ZQzdVy8qSrJzVzAOJjhW90yG67', 'fJ5rBMyacmtSuKFHHH34nA==', 'mfaEjOqNlJsxISmRZ33MdQ==', 'EtvVCnIlzRb1kVZUnbvHxg==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql2);

            /*USERS*/
            String sql3 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)VALUES('TrQ0T6UCPBE08WnIh15NbA==', 'q/vjhIIdVCNUxcdb/mkHFQ==', '24wt53kpAa7qb6njcPoVvA==', 'nRBR0CPOQaoShhtEN1hgUg==', 'DADiihVREcn88R30VPihog==', 'CtarrfvT/WbyQwRI4y+J7ZxL3mwfw49jKVkGIX6LMVbnHbXQ/sEcQvTwCi+undDi7qqcr9bQj+WqoYWWXlInlx+aRV6D1ENTSIOEEnTf1r7NNuwoYVKrY2+0+z5Su9Zu', 'EJl5Rx42MVsOtfOvn7JcRA==', 'mfaEjOqNlJsxISmRZ33MdQ==', 'IFpSS1UrcHbUwRUGURUklw==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql3);
            String sql4 = "UPDATE users SET user_active = 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u' WHERE user_id = 'TrQ0T6UCPBE08WnIh15NbA==';";
            insertarModificarEliminar(sql4);
            String sql5 = "UPDATE users SET user_role = 'bv2ha6jljpAl7dOl6aOVYg==' WHERE user_id = 'TrQ0T6UCPBE08WnIh15NbA==';";
            insertarModificarEliminar(sql5);

            String sql6 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)VALUES('K0hLKXQPl8wnur/1a3hydA==', 'uOxJYnR3WPEDqRfbaUSi/g==', '+RzQUjtQ3UxomiHSZ0fmuQ==', 'y+WfCGTeuSNjZ0fPv1Bn1Q==', 'DADiihVREcn88R30VPihog==', 'CtarrfvT/WbyQwRI4y+J7ZxL3mwfw49jKVkGIX6LMVbnHbXQ/sEcQvTwCi+undDi7qqcr9bQj+WqoYWWXlInlx+aRV6D1ENTSIOEEnTf1r7NNuwoYVKrY2+0+z5Su9Zu', 'EJl5Rx42MVsOtfOvn7JcRA==', 'mfaEjOqNlJsxISmRZ33MdQ==', 'Izm4ou4ZPD7eAq9ThW8sHg==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql6);
            String sql7 = "UPDATE users SET user_active = 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u' WHERE user_id = 'K0hLKXQPl8wnur/1a3hydA==';";
            insertarModificarEliminar(sql7);
            String sql8 = "UPDATE users SET user_role = 'bv2ha6jljpAl7dOl6aOVYg==' WHERE user_id = 'K0hLKXQPl8wnur/1a3hydA==';";
            insertarModificarEliminar(sql8);

            String sql9 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)VALUES('AXCN9bSSGikZtQo2vxsepQ==', 'uBIO47NCLKppSWpS9papig==', 'QPSKtQXU0iqfrsCoFLYpaw==', '0deZa0hq3gro/Q3U32b7Fw==', 'INIKjDsbEsznKLwFmrgNAw==', 'CtarrfvT/WbyQwRI4y+J7ZxL3mwfw49jKVkGIX6LMVbnHbXQ/sEcQvTwCi+undDi7qqcr9bQj+WqoYWWXlInlx+aRV6D1ENTSIOEEnTf1r7NNuwoYVKrY2+0+z5Su9Zu', 'EJl5Rx42MVsOtfOvn7JcRA==', 'Cwshhq0bCO0DfChF6YX97g==', 'vPRLIg7odM0TppoxfAyfyA==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql9);

            /*ITEMCARDS*/
            String sql10 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '2', 't9aQ9FmDqSugvBL4bZpB2g==', 'H9nxzKSUZp4yJ2SMWm6K3g==', 'vJE0iCtYQQkdgPmO1UHFrw==','l5P8mY7HqphqYPRm0tu/IGj00uvJ8U9GLsrSerZLlmL1bDvqGDT6DY2mdA/nJy3z/C9+YFiRbRTGZd+xw4yW2A==','5:11/1','MeNR7CLAjL1EwHjdysz5kNaSwWxOe8gK76eQEl4xcmE=', '1', '2024-07-23 08:52:13.929', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql10);

            String sql11 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '3', '8G+77TicvtTcmbpyVzXr5g==', 'GQAmNfW/AnxBpnhyk1xyZwsLIYatGwjtA3woRemF/e4=', 'vJE0iCtYQQkdgPmO1UHFrw==','GQAmNfW/AnxBpnhyk1xyZwYyn3nenQkYpvT+21/G3pULCyGGrRsI7QN8KEXphf3u','5611/1','c6d6hIuzHCMHUPEfLoGVO9aSwWxOe8gK76eQEl4xcmE=', '1', '2024-07-23 09:01:50.611', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql11);

            String sql12 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '4', 'y3PGsd8HnJxFBx/C2pqCaA==', 'TAOIo5BXifN2Qa3p7LkOsnUCKYlfzkBp2no/TZaR1Kg=', 'oq+ndGGeFoWVRa7z+jTQTg==','2q4Q7ZUWSzfRyhcxBrMIxU4SnvdqNJhJLEulzwXLPWNiD1zCMlj/ALtgxrUbLqfFCwshhq0bCO0DfChF6YX97g==','811/1','48+qcX1tPL/1FORBb9VQoOEpL5+PWR+jV+wNxR0MOIE=', '23', '2024-07-23 09:17:52.86', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql12);

            String sql13 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '5', 'iDom2cUYpxBhZih6kwNJcg==', 'XpucmJPsrBx4+8Jxp5mo/q0Jx8tfKZykJg/cyvIo3bk=', 'oq+ndGGeFoWVRa7z+jTQTg==','XpucmJPsrBx4+8Jxp5mo/g0nsAyWbSkVnIH4w9IO8s0hLnmxsqao/CY1A10qRlYFl+tF6wgTGgJ7CBymChjL9w==','5:11/1','c6d6hIuzHCMHUPEfLoGVO9aSwWxOe8gK76eQEl4xcmE=', '7', '2024-07-23 09:19:41.525', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql13);

            String sql14 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '6', 'NrvATbrJT1n2JF6DGICRHQ==', 'RjfMlbTvGvMxKaklofhsWw==', 'BsOSSiksfXMpJ2bjNzRm6A==','RjfMlbTvGvMxKaklofhsWw==','1/1','/YGzaqGrJRy58wqnxsKxYuEpL5+PWR+jV+wNxR0MOIE=', '1', '2024-07-25 01:44:30.268', 'K992SZTEVF57Ki/fu/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql14);

            String sql15 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '7', 'Wp+niVwi6v6LzKJbRjOudA==', 'dObOQEquPeMSJWtlChQcOw==', 'vJE0iCtYQQkdgPmO1UHFrw==','GSN118bs2oE9anxftZczPnZudd5X9h/IPE79oMlCY836w9Z1H2QvfXk6QV0M/4DDRDmPdUp+klx48maA06fDcw==','8111/1','3FJ2ho6v7VzGHE58acnlW9aSwWxOe8gK76eQEl4xcmE=', '23', '2024-07-27 19:44:39.947', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql15);

            /*CLIENTS*/
            String sql16 = "INSERT INTO delivery_clients(delivery_client_id, delivery_client_street, delivery_client_street_num, delivery_client_dept_floor, delivery_client_dept_num, delivery_client_district, delivery_client_area, delivery_client_details, delivery_client_name, delivery_client_phone, delivery_client_social_network, delivery_client_active)VALUES('1', 'Q3iinDDrdJpfO8MAgHAJ9Q==', 'PX2+Kjm0q1aMcjmlqNRTVw==', 'MxyuI1iRndQg8r3ImtVuOA==', 'MxyuI1iRndQg8r3ImtVuOA==', 'mzMfjIUTuzi+tqrPTe+G+Q==', 'ZFROSOEPeHK9R1usUwTb5w==', 'g45j7gsu0OstU9dKrtat7D30yF6jTQUbcPUhL7szOZHmHDy0/G57P/XP2kmDuRtb', 'GK4Dveqs+2UW0sqR0m6gXTtialfy71JALCWo7NgwHbs=', 'VJRRaWU1dtj0N+jOWgENqw==', 'tIss+UoHUbW3gZu5sCkgZw==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql16);
            String sql17 = "INSERT INTO delivery_clients(delivery_client_id, delivery_client_street, delivery_client_street_num, delivery_client_dept_floor, delivery_client_dept_num, delivery_client_district, delivery_client_area, delivery_client_details, delivery_client_name, delivery_client_phone, delivery_client_social_network, delivery_client_active)VALUES('2', 'glWeQn7ea8XcPN5JzB5zFw==', 'PX2+Kjm0q1aMcjmlqNRTVw==', 'MxyuI1iRndQg8r3ImtVuOA==', 'MxyuI1iRndQg8r3ImtVuOA==', 'y4qB51ULgLI8BwZ+gXcGuQ==', 'dlvu0iZXjbzhBC6WAGDQeg==', 'AlvbnH4CufZgv3Bygx77/+AxhBpt/NI9b3qkWhEptFw=', 'nxuJ7v9T6d0kJbXi+ue366nTgTEI3Prk5drDuzKgQnc=', 'oNKgxXnMf+ti2D5L13gsAw==', 'K3POuWqlNOYryWuXtsr1kQ==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql17);
            
            utiliMsg.cargaTablesLoaded();

            System.out.println("Tablas creadas exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}

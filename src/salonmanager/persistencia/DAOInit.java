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
                    + "	config_table_total TEXT," /*nro total de tabs*/
                    + " config_table_num_panes TEXT," /*número de mesas por pane*/
                    + "	config_table_name_panes TEXT," /*nombre de cada pane*/
                    + " config_table_chart_panes TEXT," /*Inicial de cada pane*/
                    + " config_table_name_categories TEXT," /*nombre de rubros*/
                    + " config_table_tip TEXT," /*porcentaje propina*/
                    + " config_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlCfgGen);
            System.out.println(sqlCfgGen);

            String sqlAct = "CREATE TABLE IF NOT EXISTS config_actual("
                    + " config_open_ws TEXT,"/*turno actual abierto*/
                    + " config_open_ws_id TEXT," /*openWsId*/
                    + " congif_defer_close_ws TEXT,"
                    + " congif_unmod_tabs TEXT"
                    + ");";
            insertarModificarEliminar(sqlAct);
            System.out.println(sqlAct);

            String sqlIC = "CREATE TABLE IF NOT EXISTS item_cards("
                    + " item_card_id TEXT PRIMARY KEY,"
                    + " item_card_code TEXT UNIQUE,"
                    + " item_card_name TEXT,"
                    + " item_card_category TEXT,"
                    + " item_card_description TEXT,"
                    + " item_card_cost TEXT,"
                    + " item_card_price TEXT,"
                    + " item_card_stock TEXT,"
                    + " item_card_date_creation TEXT,"
                    + " item_card_date_update TEXT,"
                    + " item_card_tip TEXT,"
                    + " item_card_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlIC);
            System.out.println(sqlIC);

            String sqlTabs = "CREATE TABLE IF NOT EXISTS tabs("
                    + "	table_num TEXT,"
                    + " table_pos TEXT,"
                    + " table_open_time TEXT,"
                    + " table_close_time TEXT,"
                    + " table_id TEXT PRIMARY KEY,"
                    + " table_open TEXT,"
                    + " table_bill TEXT,"
                    + " table_to_pay TEXT,"
                    + " table_discount TEXT,"
                    + " table_error TEXT,"
                    + " table_price_correction TEXT,"
                    + " table_amount_cash TEXT,"
                    + " table_amount_electronic TEXT,"
                    + " table_total TEXT,"
                    + " table_comments TEXT,"
                    + " table_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlTabs);
            System.out.println(sqlTabs);

            String sqlWs = "CREATE TABLE IF NOT EXISTS workshifts("
                    + "	workshift_id TEXT PRIMARY KEY,"
                    + " workshift_open_time_shift TEXT,"
                    + "	workshift_close_time_shift TEXT,"
                    + "	workshift_state_shift TEXT,"
                    + "	workshift_mount_cash TEXT,"
                    + "	workshift_mount_electronic TEXT,"
                    + "	workshift_total_mount_tabs TEXT,"
                    + "	workshift_total_mount_ws TEXT,"
                    + "	workshift_error_mount_tabs TEXT,"
                    + "	workshift_error_mount_ws TEXT,"
                    + " workshift_cash_flow_cash TEXT,"
                    + " workshift_cash_flow_elec TEXT,"
                    + " workshift_comment TEXT,"
                    + " workshift_error TEXT,"
                    + " workshift_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlWs);
            System.out.println(sqlWs);

            String sqlUsers = "CREATE TABLE IF NOT EXISTS users("
                    + " user_id TEXT UNIQUE PRIMARY KEY,"
                    + " user_name TEXT,"
                    + " user_last_name TEXT,"
                    + " user_mail TEXT UNIQUE,"
                    + " user_role TEXT,"
                    + " user_image_route TEXT,"
                    + " user_image_name TEXT,"
                    + " user_password TEXT,"
                    + " user_phone TEXT UNIQUE,"
                    + " user_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlUsers);
            System.out.println(sqlUsers);

            String sqlCat = "CREATE TABLE IF NOT EXISTS categories("
                    + " category_name TEXT"
                    + ");";
            insertarModificarEliminar(sqlCat);
            System.out.println(sqlCat);

            String sqlSpa = "CREATE TABLE IF NOT EXISTS spaces("
                    + " space_name TEXT"
                    + ");";
            insertarModificarEliminar(sqlSpa);
            System.out.println(sqlSpa);

            String sqlCh = "CREATE TABLE IF NOT EXISTS chars("
                    + " char_name TEXT"
                    + ");";
            insertarModificarEliminar(sqlCh);
            System.out.println(sqlCh);

            String sqlDeli = "CREATE TABLE IF NOT EXISTS deliverys("
                    + "	delivery_id TEXT,"
                    + " delivery_consumer_phone TEXT,"
                    + " delivery_tab_id TEXT,"
                    + " delivery_user_id TEXT,"
                    + " delivery_open TEXT,"
                    + " delivery_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlDeli);
            System.out.println(sqlDeli);

            String sqlDeliCl = "CREATE TABLE IF NOT EXISTS delivery_clients ("
                    + "	delivery_client_id TEXT PRIMARY KEY,"
                    + " delivery_client_street TEXT,"
                    + " delivery_client_street_num TEXT,"
                    + " delivery_client_dept_floor TEXT,"
                    + " delivery_client_dept_num TEXT,"
                    + " delivery_client_district TEXT,"
                    + " delivery_client_area TEXT,"
                    + " delivery_client_details TEXT,"
                    + " delivery_client_name TEXT,"
                    + " delivery_client_phone TEXT,"
                    + " delivery_client_social_network TEXT,"
                    + " delivery_client_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlDeliCl);
            System.out.println(sqlDeliCl);

            String sqlICOT = "CREATE TABLE IF NOT EXISTS item_card_order_tabs("
                    + " item_card_order_tab_id TEXT,"
                    + " item_card_order_tab_active TEXT,"
                    + " item_card_order_id_fkey TEXT,"
                    + " table_id_fkey TEXT,"
                    + " FOREIGN KEY (item_card_order_id_fkey) REFERENCES item_cards(item_card_id),"
                    + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                    + ");";
            insertarModificarEliminar(sqlICOT);
            System.out.println(sqlICOT);

            String sqlICGT = "CREATE TABLE IF NOT EXISTS item_card_gift_tabs("
                    + " item_card_gift_tab_id TEXT, "
                    + " item_card_gift_tab_active TEXT,"
                    + " item_card_gift_id_fkey TEXT,"
                    + " table_id_fkey TEXT,"
                    + " FOREIGN KEY (item_card_gift_id_fkey) REFERENCES item_cards(item_card_id),"
                    + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                    + ");";
            insertarModificarEliminar(sqlICGT);
            System.out.println(sqlICGT);

            String sqlICPT = "CREATE TABLE IF NOT EXISTS item_card_payed_tabs("
                    + " item_card_payed_tab_id TEXT, "
                    + " item_card_payed_tab_active TEXT,"
                    + " item_card_payed_id_fkey TEXT,"
                    + " table_id_fkey TEXT,"
                    + " FOREIGN KEY (item_card_payed_id_fkey) REFERENCES item_cards(item_card_id),"
                    + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                    + ");";
            insertarModificarEliminar(sqlICPT);
            System.out.println(sqlICPT);

            String sqlICPNT = "CREATE TABLE IF NOT EXISTS item_card_payed_nd_tabs("
                    + " item_card_payed_nd_tab_id TEXT,"
                    + " item_card_payed_nd_tab_active TEXT,"
                    + " item_card_payed_nd_id_fkey TEXT,"
                    + " table_id_fkey TEXT,"
                    + " FOREIGN KEY (item_card_payed_nd_id_fkey) REFERENCES item_cards(item_card_id),"
                    + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                    + ");";
            insertarModificarEliminar(sqlICPNT);
            System.out.println(sqlICPNT);

            String sqlIM = "CREATE TABLE IF NOT EXISTS item_monits("
                    + "	item_monit_id TEXT,"
                    + " item_monit_table_id TEXT,"
                    + " item_monit_item_id TEXT,"
                    + " item_monit_tipe TEXT,"
                    + " item_monit_init_bool TEXT,"
                    + " item_monit_init_date DATETIME(3),"
                    + " item_monit_cook_bool TEXT,"
                    + " item_monit_cook_date DATETIME(3),"
                    + " item_monit_ready_bool TEXT,"
                    + " item_monit_ready_date DATETIME(3),"
                    + " item_monit_otw_bool TEXT,"
                    + " item_monit_otw_date DATETIME(3),"
                    + " item_monit_open TEXT,"
                    + " item_monit_active TEXT,"
                    + " item_monit_indications TEXT"
                    + ");";
            insertarModificarEliminar(sqlIM);
            System.out.println(sqlIM);

            String sqlISS = "CREATE TABLE IF NOT EXISTS item_sales_statics("
                    + " item_sale_static_id TEXT,"
                    + " item_sale_id TEXT,"
                    + "	item_sale_category TEXT,"
                    + " item_sale_tab_pos TEXT,"
                    + " item_sale_waiter_id TEXT,"
                    + " item_sale_workshift_id TEXT,"
                    + " item_sale_price TEXT,"
                    + " item_sale_date DATETIME(3),"
                    + " item_sale_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlISS);
            System.out.println(sqlISS);

            String sqlMF = "CREATE TABLE IF NOT EXISTS money_flows("
                    + " money_flow_id TEXT PRIMARY KEY,"
                    + "	money_flow_kind TEXT,"
                    + " money_flow_m_k TEXT,"
                    + " money_flow_amount TEXT,"
                    + "	money_flow_comment TEXT,"
                    + " money_flow_date TEXT,"
                    + "	money_flow_ws_id TEXT,"
                    + " money_flow_active TEXT"
                    + ");";
            insertarModificarEliminar(sqlMF);
            System.out.println(sqlMF);

            String sqlWT = "CREATE TABLE IF NOT EXISTS waiter_tabs("
                    + " waiter_tab_id TEXT,"
                    + " waiter_tab_active TEXT,"
                    + " waiter_id_fkey TEXT,"
                    + " table_id_fkey TEXT,"
                    + "	FOREIGN KEY (waiter_id_fkey) REFERENCES users(user_id),"
                    + " FOREIGN KEY (table_id_fkey) REFERENCES tabs(table_id)"
                    + ");";
            insertarModificarEliminar(sqlWT);
            System.out.println(sqlWT);

            String sqlCW = "CREATE TABLE IF NOT EXISTS cashier_workshifts("
                    + " cashier_workshift_id INT PRIMARY KEY,"
                    + " cashier_workshift_active TEXT,"
                    + " cashier_id_fkey TEXT,"
                    + " workshift_id_fkey TEXT,"
                    + " FOREIGN KEY (cashier_id_fkey) REFERENCES users(user_id),"
                    + " FOREIGN KEY (workshift_id_fkey) REFERENCES workshifts(workshift_id)"
                    + ");";
            insertarModificarEliminar(sqlCW);
            System.out.println(sqlCW);

            String sqlR = "CREATE TABLE IF NOT EXISTS registers("
                    + "	register_id INT PRIMARY KEY,"
                    + " register_ejecution DATETIME(3),"
                    + " register_user TEXT,"
                    + " register_user_modify TEXT,"
                    + " register_operation TEXT,"
                    + " register_object TEXT,"
                    + " register_modification TEXT"
                    + ");";
            insertarModificarEliminar(sqlR);
            System.out.println(sqlR);

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
            System.out.println(sql1);

            String sql2 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active) VALUES('c2OuJxQqABPRxKy/kPXW2A==', '5FB3bF4LheVY0H6KFiHt/g==', '+RzQUjtQ3UxomiHSZ0fmuQ==', 'S9FqHlkcN9TEgUKgVz/iTA==', 'M88re0RIWY4NF0w7/z6VRg==', 'CtarrfvT/WbyQwRI4y+J7ZxL3mwfw49jKVkGIX6LMVbnHbXQ/sEcQvTwCi+undDi7qqcr9bQj+WqoYWWXlInl/D4zUgkXSKu/0DOY7ZQzdVy8qSrJzVzAOJjhW90yG67', 'fJ5rBMyacmtSuKFHHH34nA==', 'mfaEjOqNlJsxISmRZ33MdQ==', 'EtvVCnIlzRb1kVZUnbvHxg==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql2);
            System.out.println(sql2);

            /*USERS*/
            String sql3 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)VALUES('TrQ0T6UCPBE08WnIh15NbA==', 'q/vjhIIdVCNUxcdb/mkHFQ==', '24wt53kpAa7qb6njcPoVvA==', 'nRBR0CPOQaoShhtEN1hgUg==', 'DADiihVREcn88R30VPihog==', 'CtarrfvT/WbyQwRI4y+J7ZxL3mwfw49jKVkGIX6LMVbnHbXQ/sEcQvTwCi+undDi7qqcr9bQj+WqoYWWXlInlx+aRV6D1ENTSIOEEnTf1r7NNuwoYVKrY2+0+z5Su9Zu', 'EJl5Rx42MVsOtfOvn7JcRA==', 'mfaEjOqNlJsxISmRZ33MdQ==', 'IFpSS1UrcHbUwRUGURUklw==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql3);
            System.out.println(sql3);

            String sql4 = "UPDATE users SET user_active = 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u' WHERE user_id = 'TrQ0T6UCPBE08WnIh15NbA==';";
            insertarModificarEliminar(sql4);
            System.out.println(sql4);

            String sql5 = "UPDATE users SET user_role = 'bv2ha6jljpAl7dOl6aOVYg==' WHERE user_id = 'TrQ0T6UCPBE08WnIh15NbA==';";
            insertarModificarEliminar(sql5);
            System.out.println(sql5);

            String sql6 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)VALUES('K0hLKXQPl8wnur/1a3hydA==', 'uOxJYnR3WPEDqRfbaUSi/g==', '+RzQUjtQ3UxomiHSZ0fmuQ==', 'y+WfCGTeuSNjZ0fPv1Bn1Q==', 'DADiihVREcn88R30VPihog==', 'CtarrfvT/WbyQwRI4y+J7ZxL3mwfw49jKVkGIX6LMVbnHbXQ/sEcQvTwCi+undDi7qqcr9bQj+WqoYWWXlInlx+aRV6D1ENTSIOEEnTf1r7NNuwoYVKrY2+0+z5Su9Zu', 'EJl5Rx42MVsOtfOvn7JcRA==', 'mfaEjOqNlJsxISmRZ33MdQ==', 'Izm4ou4ZPD7eAq9ThW8sHg==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql6);
            System.out.println(sql6);

            String sql7 = "UPDATE users SET user_active = 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u' WHERE user_id = 'K0hLKXQPl8wnur/1a3hydA==';";
            insertarModificarEliminar(sql7);
            System.out.println(sql7);

            String sql8 = "UPDATE users SET user_role = 'bv2ha6jljpAl7dOl6aOVYg==' WHERE user_id = 'K0hLKXQPl8wnur/1a3hydA==';";
            insertarModificarEliminar(sql8);
            System.out.println(sql8);

            String sql9 = "INSERT INTO users(user_id, user_name, user_last_name, user_mail, user_role, user_image_route, user_image_name, user_password, user_phone, user_active)VALUES('AXCN9bSSGikZtQo2vxsepQ==', 'uBIO47NCLKppSWpS9papig==', 'QPSKtQXU0iqfrsCoFLYpaw==', '0deZa0hq3gro/Q3U32b7Fw==', 'INIKjDsbEsznKLwFmrgNAw==', 'CtarrfvT/WbyQwRI4y+J7ZxL3mwfw49jKVkGIX6LMVbnHbXQ/sEcQvTwCi+undDi7qqcr9bQj+WqoYWWXlInlx+aRV6D1ENTSIOEEnTf1r7NNuwoYVKrY2+0+z5Su9Zu', 'EJl5Rx42MVsOtfOvn7JcRA==', 'Cwshhq0bCO0DfChF6YX97g==', 'vPRLIg7odM0TppoxfAyfyA==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql9);
            System.out.println(sql9);

            /*ITEMCARDS*/
            String sql10 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '2', 't9aQ9FmDqSugvBL4bZpB2g==', 'H9nxzKSUZp4yJ2SMWm6K3g==', 'vJE0iCtYQQkdgPmO1UHFrw==','l5P8mY7HqphqYPRm0tu/IGj00uvJ8U9GLsrSerZLlmL1bDvqGDT6DY2mdA/nJy3z/C9+YFiRbRTGZd+xw4yW2A==','5:11/1','MeNR7CLAjL1EwHjdysz5kNaSwWxOe8gK76eQEl4xcmE=', '1', '2024-07-23 08:52:13.929', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql10);
            System.out.println(sql10);

            String sql11 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '3', '8G+77TicvtTcmbpyVzXr5g==', 'GQAmNfW/AnxBpnhyk1xyZwsLIYatGwjtA3woRemF/e4=', 'vJE0iCtYQQkdgPmO1UHFrw==','GQAmNfW/AnxBpnhyk1xyZwYyn3nenQkYpvT+21/G3pULCyGGrRsI7QN8KEXphf3u','5611/1','c6d6hIuzHCMHUPEfLoGVO9aSwWxOe8gK76eQEl4xcmE=', '1', '2024-07-23 09:01:50.611', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql11);
            System.out.println(sql11);

            String sql12 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '4', 'y3PGsd8HnJxFBx/C2pqCaA==', 'TAOIo5BXifN2Qa3p7LkOsnUCKYlfzkBp2no/TZaR1Kg=', 'oq+ndGGeFoWVRa7z+jTQTg==','2q4Q7ZUWSzfRyhcxBrMIxU4SnvdqNJhJLEulzwXLPWNiD1zCMlj/ALtgxrUbLqfFCwshhq0bCO0DfChF6YX97g==','811/1','48+qcX1tPL/1FORBb9VQoOEpL5+PWR+jV+wNxR0MOIE=', '23', '2024-07-23 09:17:52.86', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql12);
            System.out.println(sql12);

            String sql13 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '5', 'iDom2cUYpxBhZih6kwNJcg==', 'XpucmJPsrBx4+8Jxp5mo/q0Jx8tfKZykJg/cyvIo3bk=', 'oq+ndGGeFoWVRa7z+jTQTg==','XpucmJPsrBx4+8Jxp5mo/g0nsAyWbSkVnIH4w9IO8s0hLnmxsqao/CY1A10qRlYFl+tF6wgTGgJ7CBymChjL9w==','5:11/1','c6d6hIuzHCMHUPEfLoGVO9aSwWxOe8gK76eQEl4xcmE=', '7', '2024-07-23 09:19:41.525', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql13);
            System.out.println(sql13);

            String sql14 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '6', 'NrvATbrJT1n2JF6DGICRHQ==', 'RjfMlbTvGvMxKaklofhsWw==', 'BsOSSiksfXMpJ2bjNzRm6A==','RjfMlbTvGvMxKaklofhsWw==','1/1','/YGzaqGrJRy58wqnxsKxYuEpL5+PWR+jV+wNxR0MOIE=', '1', '2024-07-25 01:44:30.268', 'K992SZTEVF57Ki/fu/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql14);
            System.out.println(sql14);

            String sql15 = "INSERT INTO item_cards(item_card_id, item_card_code, item_card_name, item_card_category, item_card_description, item_card_cost, item_card_price,  item_card_stock, item_card_date_creation, item_card_tip, item_card_active) VALUES( '7', 'Wp+niVwi6v6LzKJbRjOudA==', 'dObOQEquPeMSJWtlChQcOw==', 'vJE0iCtYQQkdgPmO1UHFrw==','GSN118bs2oE9anxftZczPnZudd5X9h/IPE79oMlCY836w9Z1H2QvfXk6QV0M/4DDRDmPdUp+klx48maA06fDcw==','8111/1','3FJ2ho6v7VzGHE58acnlW9aSwWxOe8gK76eQEl4xcmE=', '23', '2024-07-27 19:44:39.947', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql15);
            System.out.println(sql15);


            /*CLIENTS*/
            String sql16 = "INSERT INTO delivery_clients(delivery_client_id, delivery_client_street, delivery_client_street_num, delivery_client_dept_floor, delivery_client_dept_num, delivery_client_district, delivery_client_area, delivery_client_details, delivery_client_name, delivery_client_phone, delivery_client_social_network, delivery_client_active)VALUES('1', 'Q3iinDDrdJpfO8MAgHAJ9Q==', 'PX2+Kjm0q1aMcjmlqNRTVw==', 'MxyuI1iRndQg8r3ImtVuOA==', 'MxyuI1iRndQg8r3ImtVuOA==', 'mzMfjIUTuzi+tqrPTe+G+Q==', 'ZFROSOEPeHK9R1usUwTb5w==', 'g45j7gsu0OstU9dKrtat7D30yF6jTQUbcPUhL7szOZHmHDy0/G57P/XP2kmDuRtb', 'GK4Dveqs+2UW0sqR0m6gXTtialfy71JALCWo7NgwHbs=', 'VJRRaWU1dtj0N+jOWgENqw==', 'tIss+UoHUbW3gZu5sCkgZw==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql16);
            System.out.println(sql16);

            String sql17 = "INSERT INTO delivery_clients(delivery_client_id, delivery_client_street, delivery_client_street_num, delivery_client_dept_floor, delivery_client_dept_num, delivery_client_district, delivery_client_area, delivery_client_details, delivery_client_name, delivery_client_phone, delivery_client_social_network, delivery_client_active)VALUES('2', 'glWeQn7ea8XcPN5JzB5zFw==', 'PX2+Kjm0q1aMcjmlqNRTVw==', 'MxyuI1iRndQg8r3ImtVuOA==', 'MxyuI1iRndQg8r3ImtVuOA==', 'y4qB51ULgLI8BwZ+gXcGuQ==', 'dlvu0iZXjbzhBC6WAGDQeg==', 'AlvbnH4CufZgv3Bygx77/+AxhBpt/NI9b3qkWhEptFw=', 'nxuJ7v9T6d0kJbXi+ue366nTgTEI3Prk5drDuzKgQnc=', 'oNKgxXnMf+ti2D5L13gsAw==', 'K3POuWqlNOYryWuXtsr1kQ==', 'K992SZTEVF57Ki/ju/UUXu/Y1r6cqpvxCcVQRsD/kqMLCyGGrRsI7QN8KEXphf3u');";
            insertarModificarEliminar(sql17);
            System.out.println(sql16);

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

package salonmanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.FrameFull;
import static salonmanager.entidades.graphics.FrameGeneral.altoUnit;
import static salonmanager.entidades.graphics.FrameGeneral.anchoUnit;
import salonmanager.entidades.graphics.PanelPpalCustom;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemCard;
import salonmanager.persistencia.DAOUser;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.servicios.ServicioSalon;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class Manager extends FrameFull {

    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Utilidades utili = new Utilidades();
    ServicioSalon ss = new ServicioSalon();
    Color narUlg = new Color(255, 255, 176);
    Color bluSt = new Color(3, 166, 136);
    Color narLg = new Color(252, 203, 5);
    SalonManager sm = new SalonManager();
    User user = null;
    String pass;
    Workshift actualWs = null;
    DAOConfig daoC = new DAOConfig();
    DAOWorkshift daoW = new DAOWorkshift();
    DAOItemCard daoI = new DAOItemCard();
    DAOUser daoU = new DAOUser();
    Salon salon = null;
    int f1 = anchoUnit * 3;
    Font font1 = new Font("Arial", Font.BOLD, f1);
    int f2 = (int) Math.round(anchoUnit * 2.3);
    Font font2 = new Font("Arial", Font.BOLD, f2);
    int f3 = (int) Math.round(anchoUnit * 1.6);
    Font font3 = new Font("Arial", Font.BOLD, f3);
    int f4 = (int) Math.round(anchoUnit * 1.2);
    Font font4 = new Font("Arial", Font.BOLD, f4);
    int f5 = (int) Math.round(anchoUnit * 1.1);
    Font font5 = new Font("Arial", Font.BOLD, f5);
    ConfigActual cfgAct = null;

    public Manager(User userIn, String passIn) throws Exception {
        sm.addFrame(this);
        user = userIn;
        pass = passIn;
        cfgAct = daoC.askConfigActual();
        setTitle("Salón Manager");
        setLayout(null);
        PanelPpalCustom panelPpal = new PanelPpalCustom(frame, 4);
        add(panelPpal);

        JMenuBar menuBar = utiliGraf.navegador(userIn, passIn, this);
        setJMenuBar(menuBar);

        JLabel labelLegal = utiliGraf.labelLegal(anchoFrame, alturaFrame, 1, 10);
        panelPpal.add(labelLegal);

        JPanel panelTitle = utiliGraf.panelTitleBacker(1);
        panelPpal.add(panelTitle);

        JLabel labelSTitleSoft = utiliGraf.labelTitleBackerA3bW("by NAXOFT");
        labelSTitleSoft.setBounds(anchoUnit * 3, altoUnit * 85, anchoUnit * 18, altoUnit * 7);
        panelPpal.add(labelSTitleSoft);

        JPanel panelUser = new JPanel();
        panelUser.setLayout(null);
        panelUser.setBounds(anchoUnit * 82, altoUnit * 3, anchoUnit * 21, altoUnit * 18);
        panelUser.setBackground(narLg);
        panelPpal.add(panelUser);

        String route = utili.barrReplaceInverse(userIn.getRouteImage());
        ImageIcon imageIcon = new ImageIcon(route);
        Image originalImage = imageIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(anchoUnit * 8, altoUnit * 14, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel labelImage = new JLabel(resizedIcon);
        labelImage.setBounds(anchoUnit, altoUnit * 2, anchoUnit * 8, altoUnit * 14);
        panelUser.add(labelImage);

        JLabel labelName = utiliGraf.labelTitleBacker2(userIn.getName());
        labelName.setBounds(anchoUnit * 10, altoUnit * 3, anchoUnit * 10, altoUnit * 3);
        panelUser.add(labelName);

        JLabel labelLastName = utiliGraf.labelTitleBacker2(userIn.getLastName());
        labelLastName.setBounds(anchoUnit * 10, altoUnit * 6, anchoUnit * 10, altoUnit * 3);
        panelUser.add(labelLastName);

        JLabel labelRol = utiliGraf.labelTitleBacker2(userIn.getRol());
        labelRol.setBounds(anchoUnit * 10, altoUnit * 12, anchoUnit * 8, altoUnit * 3);
        panelUser.add(labelRol);

        JPanel panelWorkshift = new JPanel();
        panelWorkshift.setLayout(null);
        panelWorkshift.setBackground(narLg);
        panelWorkshift.setBounds(anchoUnit * 54, altoUnit * 24, anchoUnit * 49, altoUnit * 20);
        panelPpal.add(panelWorkshift);

        JLabel labelWsTit = utiliGraf.labelTitleBackerA4W("INFORMACIÓN TURNO ACTUAL:");
        labelWsTit.setBounds(anchoUnit * 2, altoUnit * 1, anchoUnit * 46, altoUnit * 5);
        panelWorkshift.add(labelWsTit);

        String wsSt = "Estado del Turno: hay un turno iniciado.";
        if (cfgAct.isOpenWs()) {
            Timestamp date = daoW.askInitWsDateById(cfgAct.getOpenIdWs());
            User cashier = daoU.getCashierByWorkshift(cfgAct.getOpenIdWs());
            wsSt = "<html>Turno Activo. Fecha de Inicio: " + utili.friendlyDate2(date) + ".<br>Responsable de caja: " + cashier.getName() + " " + cashier.getLastName() + ".</html>";
        }

        JLabel labelWsData = utiliGraf.labelTitleBacker1(wsSt);
        labelWsData.setBounds(anchoUnit * 2, altoUnit * 7, anchoUnit * 46, altoUnit * 12);
        panelWorkshift.add(labelWsData);

        JPanel panelCard = new JPanel();
        panelCard.setLayout(null);
        panelCard.setBackground(narLg);
        panelCard.setBounds(anchoUnit * 54, altoUnit * 47, anchoUnit * 49, altoUnit * 11);
        panelPpal.add(panelCard);

        JLabel labelCard = utiliGraf.labelTitleBackerA4W("CONSULTAR CARTA COMPLETA:");
        labelCard.setBounds(anchoUnit * 2, altoUnit * 3, anchoUnit * 35, altoUnit * 5);
        panelCard.add(labelCard);

        JButtonMetalBlu butList = utiliGraf.button1("Consultar", anchoUnit * 35, altoUnit * 2, anchoUnit * 12);
        butList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    listOpener();
                } catch (Exception ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panelCard.add(butList);

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir2(frame, 4);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirm = utiliMsg.cargaConfirmarCierrePrograma();
                if (confirm) {
                    sm.frameCloser();
                    System.exit(0);
                }
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    sm.frameCloser();
                    System.exit(0);
                }
            }
        });
    }

    public void salonFrameManager() throws Exception {
        Manager man = this;
        if (salon == null) {
            ConfigActual cfgAct = daoC.askConfigActual();
            User userWs = null;
            if (cfgAct.isOpenWs()) {
                Workshift ws = daoW.askWorshiftById(cfgAct.getOpenIdWs());
                userWs = daoU.getCashierByWorkshift(cfgAct.getOpenIdWs());
                if (userWs.getId() == null) {
                    userWs = user;
                    ws.setCashierWs(userWs);
                    daoU.saveCashierWorkshift(ws);
                }
            }

            if (userWs != null) {
                if (!userWs.getId().equals(user.getId())) {
                    ss.endWorkshift(null, man, true);
                } else {
                    salon = new Salon(man, cfgAct);
                }
            } else {
                salon = new Salon(man, cfgAct);
            }

        } else {
            salon.setVisible(true);
            salon.toFront();
            salon.setExtendedState(JFrame.MAXIMIZED_BOTH);
            salon.requestFocus();
        }
    }

    public Workshift getActualWorkShift() {
        return actualWs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userAux) {
        this.user = userAux;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String passAux) {
        this.pass = passAux;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Font getFont1() {
        return font1;
    }

    public void setFont1(Font font1) {
        this.font1 = font1;
    }

    public Font getFont2() {
        return font2;
    }

    public void setFont2(Font font2) {
        this.font2 = font2;
    }

    public Font getFont3() {
        return font3;
    }

    public void setFont3(Font font3) {
        this.font3 = font3;
    }

    public Font getFont4() {
        return font4;
    }

    public void setFont4(Font font4) {
        this.font4 = font4;
    }

    public Font getFont5() {
        return font5;
    }

    public void setFont5(Font font5) {
        this.font5 = font5;
    }

//    public JTable createTableItems() throws Exception {
//        tableItems = new JTable();
//
//        String col1 = "Item";
//        String col2 = "Unidades";
//        String col3 = "Precio";
//
//        ArrayList<ItemCard> totalItems = daoI.listarItemsCard();
//        int rows = totalItems.size();
//
//        String[] colNames = {col1, col2, col3};
//        String[][] data = new String[rows][3];
//
//        for (int i = 0; i < rows; i++) {
//            ItemCard ic = totalItems.get(i);
//
//            data[i][0] = " " + ic.getName();
//            data[i][1] = " " + ic.getStock() + " u.";
//            data[i][2] = " $ " + ic.getPrice().get(0);
//        }
//
//        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
//        tableItems.setModel(tableModel);
//        tableItems.setDefaultEditor(Object.class, null);
//
//        JTableHeader header = tableItems.getTableHeader();
//        header.setPreferredSize(new java.awt.Dimension(header.getWidth(), altoUnit * 6));
//        header.setFont(new Font("Arial", Font.BOLD, 16));
//        header.setBackground(narLg);
//
//        Font cellFont = new Font("Arial", Font.BOLD, 14);
//        tableItems.setFont(cellFont);
//        tableItems.setRowHeight(altoUnit * 4);
//        tableItems.setBackground(narUlg);
//
//        TableColumn column1 = tableItems.getColumnModel().getColumn(0);
//        column1.setPreferredWidth(anchoUnit * 20);
//        TableColumn column2 = tableItems.getColumnModel().getColumn(1);
//        column2.setPreferredWidth(anchoUnit * 7);
//        TableColumn column3 = tableItems.getColumnModel().getColumn(2);
//        column3.setPreferredWidth(anchoUnit * 8);
//
//        return tableItems;
//    }

    
    
//    public void updateTableItem() throws Exception {
//        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//            @Override
//            protected Void doInBackground() throws Exception {
//                // Obtener los nuevos datos para la tabla
//                ArrayList<ItemCard> totalItems = daoI.listarItemsCard();
//                int rows = totalItems.size();
//
//                String[] colNames = {"Item", "Unidades", "Precio"};
//                String[][] data = new String[rows][3];
//
//                for (int i = 0; i < rows; i++) {
//                    ItemCard ic = totalItems.get(i);
//                    data[i][0] = " " + ic.getName();
//                    data[i][1] = " " + ic.getStock() + " u.";
//                    data[i][2] = " $ " + ic.getPrice().get(0);
//                }
//
//                // Actualizar el modelo de la tabla en el hilo de la interfaz de usuario
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        DefaultTableModel tableModel = new DefaultTableModel(data, colNames);
//                        tableItems.setModel(tableModel);
//                    }
//                });
//
//                return null;
//            }
//
//            @Override
//            protected void done() {
//                // Revalidar y repintar los componentes en el hilo de la interfaz de usuario
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        panelCard.revalidate();
//                        scrollPane.revalidate();
//                        tableItems.revalidate();
//                        panelCard.repaint();
//                        scrollPane.repaint();
//                        tableItems.repaint();
//                    }
//                });
//            }
//        };
//        worker.execute();
//    }

//    public void updateTableItems() throws Exception {
//        panelCard.removeAll();
//        scrollPane.removeAll();
//        tableItems.removeAll();
//        tableItems = createTableItems();
//        scrollPane = new JScrollPane(tableItems);
//        scrollPane.setPreferredSize(new Dimension(anchoUnit * 35, altoUnit * 38));
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setBounds(anchoUnit * 2, altoUnit * 7, anchoUnit * 35, altoUnit * 36);
//        panelCard.add(scrollPane);
//        panelCard.revalidate();
//        scrollPane.revalidate();
//        tableItems.revalidate();        
//        panelCard.repaint();
//        scrollPane.repaint();
//        tableItems.repaint();  
//    }
    private void listOpener() throws Exception {
        new ItemcardList(this);
//        updateTableItems();
    }
}

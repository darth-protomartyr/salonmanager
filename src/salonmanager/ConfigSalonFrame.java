package salonmanager;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.config.ConfigGeneral;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.entidades.graphics.PanelNestedSpace;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.persistencia.DAOConfig;
import salonmanager.servicios.ServiceConfigSal;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesGraficasCfgSal;
import salonmanager.utilidades.UtilidadesMensajes;

public class ConfigSalonFrame extends FrameThird {

    DAOConfig daoC = new DAOConfig();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    UtilidadesGraficasCfgSal utiliGrafCfgS = new UtilidadesGraficasCfgSal();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    ServiceConfigSal scs = new ServiceConfigSal();
    SalonManager sm = new SalonManager();

    Font font1 = new Font("Arial", Font.BOLD, 20);
    Font font2 = new Font("Arial", Font.PLAIN, 16);

    JList listSpaces = new JList();
    JList listSpacesSel = new JList();
    JList listCat = new JList();
    JList listCatSel = new JList();

    ArrayList<String> spacesDB = new ArrayList<>();
    ArrayList<String> spaces = new ArrayList<>();
    ArrayList<String> spacesSel = new ArrayList<>();
    ArrayList<String> charsDB = new ArrayList<>();
    ArrayList<String> charsSel = new ArrayList<>();
    ArrayList<Integer> quants = new ArrayList<>();
    ArrayList<String> categoriesDB = new ArrayList<>();
    ArrayList<String> categories = new ArrayList<>();
    ArrayList<String> categoriesSel = new ArrayList<>();

    JButtonMetalBlu butConfirm1 = null;
    JButtonMetalBlu butConfirm2 = null;
    JButtonMetalBlu butReset = null;
    JButtonMetalBlu butAddSpace = null;
    JButtonMetalBlu butAddCategory = null;
    JSpinner spinnerTip = new JSpinner();
    String data1 = "";
    String data2 = "";
    JLabel labelData1 = new JLabel();
    JLabel labelData2 = new JLabel();
    JPanel panelPanel = new JPanel();
    ArrayList<PanelNestedSpace> panels = new ArrayList<>();
    ConfigGeneral cfgGen = new ConfigGeneral();
    User user = null;
    boolean selSectors = false;

    public ConfigSalonFrame(User u) throws Exception {
        sm.addFrame(this);
        user = u;
        cfgGen = daoC.askConfigGeneral();
        setTitle("Configuración Salón");
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        spacesDB = daoC.askSpaces();
        spaces = spacesDB;
        categoriesDB = daoC.askCategories();
        categories = categoriesDB;
        charsDB = daoC.askChars();

        JLabel labelTit = utiliGraf.labelTitleBackerA4W("Configurar Salón");
        labelTit.setBounds(anchoUnit * 9, altoUnit * 0, this.getWidth() - anchoUnit * 10, altoUnit * 5);
        panelPpal.add(labelTit);
        
        JPanel panelLogo = utiliGraf.panelLogoBacker2(this.getWidth());
        panelPpal.add(panelLogo);

        JPanel panelSpaces = utiliGrafCfgS.panelSpacesBacker(this);
        panelPpal.add(panelSpaces);
        
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setBounds(anchoUnit * 1, altoUnit * 19 + 3, anchoUnit * 32, 2); // Posición y tamaño del separador
        panelPpal.add(separator1);
        
        JPanel panelSpacesPanel = utiliGrafCfgS.panelSpacesPanelBacker(this);
        panelPpal.add(panelSpacesPanel);
        
        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setBounds(anchoUnit * 1, altoUnit * 45 + 1, anchoUnit * 32, 4); // Posición y tamaño del separador
        panelPpal.add(separator2);
        
        JPanel panelCat = utiliGrafCfgS.panelCatBacker(this);
        panelPpal.add(panelCat);        
        
        JSeparator separator3 = new JSeparator(SwingConstants.HORIZONTAL);
        separator3.setBounds(anchoUnit * 1, altoUnit * 60 + 3, anchoUnit * 32, 4); // Posición y tamaño del separador
        panelPpal.add(separator3);

        JPanel panelTip = utiliGrafCfgS.panelTipBacker(this);
        panelPpal.add(panelTip);  
        
        butConfirm2 = utiliGrafCfgS.butConf2Backer(this);
        panelPpal.add(butConfirm2);

        butReset = utiliGrafCfgS.butResetBacker(this);
        panelPpal.add(butReset);

        scs.updateValues(this);
        
        
        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
        panelPpal.add(butSalir);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (confirmation) {
                    dispose();
                }
            }
        });
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
    
    public SalonManager getSm() {
        return sm;
    }

    public void setSm(SalonManager sm) {
        this.sm = sm;
    }

    public JList getListSpaces() {
        return listSpaces;
    }

    public void setListSpaces(JList listSpaces) {
        this.listSpaces = listSpaces;
    }

    public JList getListSpacesSel() {
        return listSpacesSel;
    }

    public void setListSpacesSel(JList listSpacesSel) {
        this.listSpacesSel = listSpacesSel;
    }

    public JList getListCat() {
        return listCat;
    }

    public void setListCat(JList listCat) {
        this.listCat = listCat;
    }

    public JList getListCatSel() {
        return listCatSel;
    }

    public void setListCatSel(JList listCatSel) {
        this.listCatSel = listCatSel;
    }

    public ArrayList<String> getSpacesDB() {
        return spacesDB;
    }

    public void setSpacesDB(ArrayList<String> spacesDB) {
        this.spacesDB = spacesDB;
    }

    public ArrayList<String> getSpaces() {
        return spaces;
    }

    public void setSpaces(ArrayList<String> spaces) {
        this.spaces = spaces;
    }

    public ArrayList<String> getSpacesSel() {
        return spacesSel;
    }

    public void setSpacesSel(ArrayList<String> spacesSel) {
        this.spacesSel = spacesSel;
    }

    public ArrayList<String> getCharsDB() {
        return charsDB;
    }

    public void setCharsDB(ArrayList<String> charsDB) {
        this.charsDB = charsDB;
    }

    public ArrayList<String> getCharsSel() {
        return charsSel;
    }

    public void setCharsSel(ArrayList<String> charsSel) {
        this.charsSel = charsSel;
    }

    public ArrayList<Integer> getQuants() {
        return quants;
    }

    public void setQuants(ArrayList<Integer> quants) {
        this.quants = quants;
    }

    public ArrayList<String> getCategoriesDB() {
        return categoriesDB;
    }

    public void setCategoriesDB(ArrayList<String> categoriesDB) {
        this.categoriesDB = categoriesDB;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public ArrayList<String> getCategoriesSel() {
        return categoriesSel;
    }

    public void setCategoriesSel(ArrayList<String> categoriesSel) {
        this.categoriesSel = categoriesSel;
    }

    public JButtonMetalBlu getButConfirm1() {
        return butConfirm1;
    }

    public void setButConfirm1(JButtonMetalBlu butConfirm1) {
        this.butConfirm1 = butConfirm1;
    }

    public JButtonMetalBlu getButConfirm2() {
        return butConfirm2;
    }

    public void setButConfirm2(JButtonMetalBlu butConfirm2) {
        this.butConfirm2 = butConfirm2;
    }

    public JButtonMetalBlu getButReset() {
        return butReset;
    }

    public void setButReset(JButtonMetalBlu butReset) {
        this.butReset = butReset;
    }

    public JButtonMetalBlu getButAddSpace() {
        return butAddSpace;
    }

    public void setButAddSpace(JButtonMetalBlu butAddSpace) {
        this.butAddSpace = butAddSpace;
    }

    public JButtonMetalBlu getButAddCategory() {
        return butAddCategory;
    }

    public void setButAddCategory(JButtonMetalBlu butAddCategory) {
        this.butAddCategory = butAddCategory;
    }

    public JSpinner getSpinnerTip() {
        return spinnerTip;
    }

    public void setSpinnerTip(JSpinner spinnerTip) {
        this.spinnerTip = spinnerTip;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public JLabel getLabelData1() {
        return labelData1;
    }

    public void setLabelData1(JLabel labelData1) {
        this.labelData1 = labelData1;
    }

    public JLabel getLabelData2() {
        return labelData2;
    }

    public void setLabelData2(JLabel labelData2) {
        this.labelData2 = labelData2;
    }

    public JPanel getPanelPanel() {
        return panelPanel;
    }

    public void setPanelPanel(JPanel panelPanel) {
        this.panelPanel = panelPanel;
    }

    public ArrayList<PanelNestedSpace> getPanels() {
        return panels;
    }

    public void setPanels(ArrayList<PanelNestedSpace> panels) {
        this.panels = panels;
    }

    public ConfigGeneral getCfgGen() {
        return cfgGen;
    }

    public void setCfgGen(ConfigGeneral cfgGen) {
        this.cfgGen = cfgGen;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSelSectors() {
        return selSectors;
    }

    public void setSelSectors(boolean selSectors) {
        this.selSectors = selSectors;
    }  
    
    
    
}

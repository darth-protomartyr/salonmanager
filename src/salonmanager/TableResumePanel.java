package salonmanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import static java.lang.Double.parseDouble;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import salonmanager.entidades.graphics.FrameThird;
import salonmanager.entidades.graphics.PanelPpal;
import salonmanager.entidades.bussiness.Table;
import salonmanager.entidades.bussiness.Workshift;
import salonmanager.entidades.config.ConfigActual;
import salonmanager.entidades.graphics.JButtonMetalBlu;
import salonmanager.persistencia.DAOConfig;
import salonmanager.persistencia.DAOItemcard;
import salonmanager.persistencia.DAOTable;
import salonmanager.persistencia.DAOWorkshift;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesGraficas;
import salonmanager.utilidades.UtilidadesMensajes;

public class TableResumePanel extends FrameThird {

    DAOTable daoT = new DAOTable();
    DAOWorkshift daoW = new DAOWorkshift();
    DAOConfig daoC = new DAOConfig();
    DAOItemcard daoI = new DAOItemcard();
    UtilidadesGraficas utiliGraf = new UtilidadesGraficas();
    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();
    Color narLg = new Color(252, 203, 5);
    Color bluLg = new Color(194, 242, 206);
    SalonManager sm = new SalonManager();
    Font newFont = new Font("Arial", Font.BOLD, 17);

    double amountCash = 0;
    double amountElec = 0;
    double sum = 0;
    double wrong = 0;
    double loss = 0;
    double totalMount = 0;

    JTextField textFieldCash = new JTextField();
    JTextField textFieldElec = new JTextField();
    JLabel labelLoss = new JLabel();
    Table tabAux = new Table();
    Admin admin = null;
    TabsToEnd tte = null;
    int kind = 0;

    
    TableResumePanel(TabsToEnd ttEnd, Table tab, int k, Admin adm) throws Exception {
        tte = ttEnd;
        kind = k;
        sm.addFrame(this);
        tabAux = tab;
        admin = adm;
        String tit = "Consulta Mesa";
        if (kind == 1) {
            tit = "Corrección Mesa";
            loss = tab.getError();
        } else if (kind == 2) {
            tit = "Finalizar Mesa";
            totalMount = tab.getTotal();
        }
        setTitle(tit);
        PanelPpal panelPpal = new PanelPpal(frame);
        add(panelPpal);

        JLabel labelTit = utiliGraf.labelTitleBackerA4W(tit.toUpperCase());
        labelTit.setBounds(anchoUnit * 4, altoUnit * 1, anchoUnit * 26, altoUnit * 5);
        panelPpal.add(labelTit);

        JPanel panelPn = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 7, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Mesa: ", tabAux.getPos() + tabAux.getNum());
        panelPpal.add(panelPn);

        String dateOpen = utili.friendlyDate1(tabAux.getOpenTime());
        JPanel panelInit = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 12, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Inicio de mesa: ", dateOpen);
        panelPpal.add(panelInit);

        String dateClose = "";
        if (tabAux.getCloseTime() != null) {
            dateClose = utili.friendlyDate1(tabAux.getCloseTime());
        } else {
            tabAux.setCloseTime(new Timestamp(new Date().getTime()));
            dateClose = utili.friendlyDate1(tabAux.getCloseTime());
        }
        JPanel panelEnd = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 17, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Fin de mesa: ", dateClose);
        panelPpal.add(panelEnd);

        JPanel panelWaiter = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 22, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Mozo: ", tabAux.getWaiter().getName() + " " + tabAux.getWaiter().getLastName());
        panelPpal.add(panelWaiter);

        JPanel panelDiscount = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 27, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Descuento: ", tabAux.getDiscount() + "%");
        panelPpal.add(panelDiscount);

        JPanel panelFact = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 32, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Facturación: ", "$" + tabAux.getTotal() + "");
        panelPpal.add(panelFact);

        if (kind == 0 || kind == 1) {
            JPanel panelCash = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 37, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Efectivo: ", "$" + tabAux.getAmountCash() + "");
            panelPpal.add(panelCash);

            JPanel panelElectronic = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 42, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Transferencia: ", "$" + tabAux.getAmountElectronic());
            panelPpal.add(panelElectronic);

            JPanel panelError = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 47, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Error: ", "$" + tabAux.getError());
            panelPpal.add(panelError);
        }

        JPanel panelCorrection = null;
        if (kind == 0 || kind == 1) {
            panelCorrection = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 52, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Corrección precio: ", "$" + tabAux.getPriceCorrection());
        } else {
            panelCorrection = utiliGraf.panelInfoBacker(anchoUnit * 4, altoUnit * 37, anchoUnit * 26, altoUnit * 5, bluLg, 20, "Corrección precio: ", "$" + tabAux.getPriceCorrection());
        }
        panelPpal.add(panelCorrection);

        String message = utili.listarItems(tabAux);

        JLabel labelMess = new JLabel(message);
        Font customFont = new Font("Arial", Font.BOLD, 15);
        labelMess.setFont(customFont);
        labelMess.setVerticalAlignment(SwingConstants.TOP);
        labelMess.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        JScrollPane scrollPane = new JScrollPane(labelMess);
        scrollPane.setBounds(anchoUnit * 4, altoUnit * 59, anchoUnit * 26, altoUnit * 32);
        panelPpal.add(scrollPane);

        if (kind == 1 || kind == 2) {
            if (kind == 1) {
                scrollPane.setBounds(anchoUnit * 4, altoUnit * 57, anchoUnit * 26, altoUnit * 14);
            } else {
                scrollPane.setBounds(anchoUnit * 4, altoUnit * 42, anchoUnit * 26, altoUnit * 29);
            }

            JPanel panelCorrect = new JPanel(null);
            panelCorrect.setBackground(narLg);
            panelCorrect.setBounds(anchoUnit * 4, altoUnit * 71, anchoUnit * 26, altoUnit * 21);
            panelPpal.add(panelCorrect);

            String t = "";
            if (kind == 1) {
                t = "Corregir Error";
            } else {
                t = "Cerrar mesa";
            }

            JLabel labelCorrect = utiliGraf.labelTitleBacker1(t);
            labelCorrect.setBounds(anchoUnit * 6, altoUnit * 0, anchoUnit * 26, altoUnit * 4);
            panelCorrect.add(labelCorrect);

            JLabel labelCorrectCash = utiliGraf.labelTitleBacker3("Ingresar Efectivo");
            labelCorrectCash.setBounds(anchoUnit * 4, altoUnit * 4, anchoUnit * 12, altoUnit * 3);
            panelCorrect.add(labelCorrectCash);

            textFieldCash.setBounds(anchoUnit * 4, altoUnit * 7, anchoUnit * 8, altoUnit * 4);
            textFieldCash.setFont(newFont);
            textFieldCash.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char keyChar = e.getKeyChar();
                    if (keyChar == KeyEvent.VK_DELETE || keyChar == KeyEvent.CHAR_UNDEFINED) {
                        e.consume(); // Consume la tecla para evitar que se procese como entrada
                    }
                    if (e.getKeyChar() == '-') {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            textFieldCash.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent de) {
                    updateMount();
                }

                @Override
                public void removeUpdate(DocumentEvent de) {
                    updateMount();
                }

                @Override
                public void changedUpdate(DocumentEvent de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            panelCorrect.add(textFieldCash);

            JLabel labelCorrectElec = utiliGraf.labelTitleBacker3("Ingresar Trasferencia");
            labelCorrectElec.setBounds(anchoUnit * 14, altoUnit * 4, anchoUnit * 15, altoUnit * 3);
            panelCorrect.add(labelCorrectElec);

            textFieldElec.setBounds(anchoUnit * 14, altoUnit * 7, anchoUnit * 8, altoUnit * 4);
            textFieldElec.setFont(newFont);
            textFieldElec.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char keyChar = e.getKeyChar();
                    if (keyChar == KeyEvent.VK_DELETE || keyChar == KeyEvent.CHAR_UNDEFINED) {
                        e.consume(); // Consume la tecla para evitar que se procese como entrada
                    }
                    if (e.getKeyChar() == '-') {
                        e.consume();
                    }
                }

                @Override
                public void keyPressed(KeyEvent e) {
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            textFieldElec.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent de) {
                    updateMount();
                }

                @Override
                public void removeUpdate(DocumentEvent de) {
                    updateMount();
                }

                @Override
                public void changedUpdate(DocumentEvent de) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            });
            panelCorrect.add(textFieldElec);

            String st1 = "";
            if (kind == 1) {
                st1 = "El monto faltante es de $" + loss + ".";
            } else if (kind == 2) {
                st1 = "El monto a pagar es de $" + totalMount + ".";
            }

            labelLoss = utiliGraf.labelTitleBacker2("El monto faltante es de $" + loss + ".");
            labelLoss.setHorizontalAlignment(SwingConstants.CENTER);
            labelLoss.setBounds(anchoUnit * 2, altoUnit * 11, anchoUnit * 22, altoUnit * 4);
            panelCorrect.add(labelLoss);

            JButtonMetalBlu butConfirmMount = utiliGraf.button2("Confirmar pago", anchoUnit * 6, altoUnit * 16, anchoUnit * 14);
            butConfirmMount.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    try {
                        if (kind == 1) {
                            confirmMount();
                        } else {
                            closeTab();
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            panelCorrect.add(butConfirmMount);

        }

        JButtonMetalBlu butSalir = utiliGraf.buttonSalir(frame);
        butSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (kind == 1) {
                    try {
                        admin.enabledTrue(0);
                    } catch (Exception ex) {
                        Logger.getLogger(TableResumePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (kind == 2) {
                    tte.setEnabled(true);
                }
                dispose();
            }
        });
        panelPpal.add(butSalir);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                boolean confirmation = utiliMsg.cargaConfirmarCierreVentana();
                if (kind == 1) {
                    try {
                        admin.enabledTrue(0);
                    } catch (Exception ex) {
                        Logger.getLogger(TableResumePanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (kind == 2) {
                    tte.setEnabled(true);
                }
                dispose();
            }
        });
    }

    private void updateMount() {
        String erC = textFieldCash.getText();
        String erE = textFieldElec.getText();

        boolean error = false;

        if (erC.equals("")) {
            erC = "0";
        }

        if (erE.equals("")) {
            erE = "0";
        }

        try {
            if (!erC.equals("")) {
                amountCash = parseDouble(erC);
            }

            if (!erE.equals("")) {
                amountElec = parseDouble(erE);
            }
        } catch (NumberFormatException e) {
            utiliMsg.errorNumerico();
            error = true;
        } catch (Exception ex) {
            Logger.getLogger(ErrorTableCount.class.getName()).log(Level.SEVERE, null, ex);
        }

        sum = amountCash + amountElec;

        if (kind == 1) {
            if (sum <= loss) {
                wrong = loss - sum;
            } else {
                labelLoss.setText("El monto supera el Total");
                error = true;
            }

            if (error == false) {
                if (wrong == loss) {
                    labelLoss.setText("El faltante está cubierto");
                } else {
                    labelLoss.setText("El monto faltante es $" + wrong + ".");
                }
            }
        } else if (kind == 2) {
            if (error == false) {
                if (sum < totalMount) {
                    labelLoss.setText("El monto faltante es de $" + (totalMount - sum) + ".");
                }

                if (sum == totalMount) {
                    labelLoss.setText("El monto total está cubierto.");
                }

                if (sum > totalMount) {
                    labelLoss.setText("El monto excede el total en " + (sum - totalMount) + ".");
                }
            }
        }
    }

    private void confirmMount() throws Exception {
        boolean error = false;
        Workshift ws = daoW.askWorshiftByTabDate(tabAux.getOpenTime());

        if (ws.getCloseWs() == null) {
            utiliMsg.errorWsOpen();
            error = true;
        }

        if (sum < 1 && error == false) {
            utiliMsg.errorMountNull();
            error = true;
        }

        if (sum > loss && error == false) {
            utiliMsg.errorTotalLess();
            error = true;
        }

        if (error == false) {
            boolean confirm = false;
            if (loss > sum) {
                confirm = utiliMsg.cargaConfirmErrorInsuf();
                String mess = tabAux.getComments() + "\n El error fue revisado y quedó un monto pendiente.";
                tabAux.setComments(mess);
            } else {
                confirm = utiliMsg.cargaConfirmErrorSuf();
                String mess = tabAux.getComments() + "\n El monto pendiente fue subsanado.";
                tabAux.setComments(mess);
            }

            if (confirm) {
                tabAux.setAmountCash(tabAux.getAmountCash() + amountCash);
                tabAux.setAmountElectronic(tabAux.getAmountElectronic() + amountElec);
                tabAux.setTotal(tabAux.getTotal() + sum);
                tabAux.setError(tabAux.getError() - sum);
                daoT.updateTableMountCash(tabAux);
                daoT.updateTableMountElectronic(tabAux);
                daoT.updateTableTotal(tabAux);
                daoT.updateError(tabAux);
                daoT.updateComments(tabAux);

                ws.setTotalMountCashWs(ws.getTotalMountCashWs() + amountCash);
                ws.setTotalMountElectronicWs(ws.getTotalMountElectronicWs() + amountElec);
                ws.setTotalMountWs(ws.getTotalMountWs() + sum);
                ws.setTotalMountRealWs(ws.getTotalMountRealWs() + sum);
                ws.setErrorMountRealWs(ws.getErrorMountWs() - sum);

                daoW.updateWorkshiftCash(ws);
                daoW.updateWorkshiftElectronic(ws);
                daoW.updateWorkshiftTotal(ws);
                daoW.updateWorkshiftMountReal(ws);
                daoW.updateWorkshiftError(ws);

                ConfigActual cfgAct = daoC.askConfigActual();
                ArrayList<String> ids = cfgAct.getArrayDeferWs();
                Iterator<String> iterador = ids.iterator();
                while (iterador.hasNext()) {
                    String st = iterador.next();
                    if (st.equals(tabAux.getId())) {
                        iterador.remove();
                    }
                }

                cfgAct.setArrayDeferWs(ids);
                daoC.updateCfgActDeferWs(ids);
                admin.enabledTrue(1);
                dispose();
            }
        }
    }

    private void closeTab() throws Exception {
        boolean confirm = utiliMsg.cargaConfirmarFacturacion(totalMount, sum - totalMount);
        if (confirm) {
            daoT.updateCloseTime(tabAux);
            tabAux.setOpen(false);
            daoT.updateTableOpen(tabAux);
            tabAux.setBill(true);
            daoT.updateTableBill(tabAux);
            tabAux.setAmountCash(amountCash);
            daoT.updateTableMountCash(tabAux);
            tabAux.setAmountElectronic(amountElec);
            daoT.updateTableMountElectronic(tabAux);
            tabAux.setTotal(totalMount);
            daoT.updateTableTotal(tabAux);
            String comment3 = "Mesa cerrada en turno diferido.<br>";
            tabAux.setComments(tabAux.getComments() + comment3);
            if (totalMount - sum > 0) {
                tabAux.setError(totalMount - sum);
                daoT.updateError(tabAux);
                ConfigActual cfgAct = daoC.askConfigActual();
                ArrayList<String> errorTabs = cfgAct.getArrayDeferWs();
                errorTabs.add(tabAux.getId());
                daoC.updateCfgActDeferWs(errorTabs);
                String comment2 = "La mesa contiene un error y deberá ser revisada.<br>";
                tabAux.setComments(tabAux.getComments() + comment2);
            }
            daoT.updateComments(tabAux);

            if (tabAux.isToPay()) {
                tabAux.setToPay(false);
                daoT.updateToPay(tabAux);
                daoI.downActiveItemPayedTableAll(tabAux);
                daoI.upActiveItemOrderTableAll(tabAux);
            }
            tte.confirmEndTable(tabAux);
            dispose();
        }
    }
}

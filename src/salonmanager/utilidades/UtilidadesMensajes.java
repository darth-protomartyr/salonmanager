package salonmanager.utilidades;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import salonmanager.entidades.ItemCarta;

public class UtilidadesMensajes extends JFrame {

    private javax.swing.JOptionPane optionPaneOk;

//    public int paneEliminarIngre() {
//        int dialogResult = JOptionPane.showOptionDialog(null, "¿Estás seguro de que quieres eliminar el ingrediente?", "Eliminar Ingrediente", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Aceptar"}, null);
//        return dialogResult;
//    }
    public void cargaRegistroExitoso() {
        optionPaneOk.showMessageDialog(null, "El usuario fue registrado con éxito.");
    }

    public void errorRegistroFallido() {
        optionPaneOk.showMessageDialog(null, "Error: El registro no fue realizado.");
    }

    public void errorCantCharName() {
        optionPaneOk.showMessageDialog(null, "Error: el número de caracteres del nombre no debe superar los 30 ni ser menor a 2.");
    }

    public void errorNameRepeat() {
        optionPaneOk.showMessageDialog(null, "Error: el mail ya se encuentra ingresado en nuestra base de datos.");
    }

    public void errorMailRepeat() {
        optionPaneOk.showMessageDialog(null, "Error: el mail ya se encuentra ingresado en nuestra base de datos.");
    }

    public void errorMail() {
        optionPaneOk.showMessageDialog(null, "Error: el mail ingresado no es válido.");
    }

    public void errorPassChar() {
        optionPaneOk.showMessageDialog(null, "Error: el password debe tener entre 8 y 30 caracteres.");
    }

    public void errorPassCoincidence() {
        optionPaneOk.showMessageDialog(null, "Error: las contraseñas ingresadas no coinciden.");
    }

    public void errorDataNull() {
        optionPaneOk.showMessageDialog(null, "Error: Usted no ingresó todos los datos requeridos.");
    }

    public void errorUserNull() {
        optionPaneOk.showMessageDialog(null, "Error: no hay un usuario registrado con el mail ingresado.");
    }

    public void errorAccessDenied() {
        optionPaneOk.showMessageDialog(null, "Error: Acceso denegado.");
    }

    public void errorCantCharDescription() {
        optionPaneOk.showMessageDialog(null, "Error: el número de caracteres de la descripción no debe superar los 149.");
    }

    public void errorNumerico() {
        optionPaneOk.showMessageDialog(null, "El dato ingresado no es numérico");
    }

    public void errorCantCharNum() {
        optionPaneOk.showMessageDialog(null, "Error: el número de caracteres del numero ingresado no debe superar los 12.");
    }

    public void errorIngresoItem() {
        optionPaneOk.showMessageDialog(null, "Error: no se generó un item.");
    }

    public void errorNameItem() {
        optionPaneOk.showMessageDialog(null, "Error: item debe tener un nombre.");
    }

    public void errorPriceItem() {
        optionPaneOk.showMessageDialog(null, "Error: el item debe tener un precio.");
    }

    public void errorCargaDB() {
        optionPaneOk.showMessageDialog(null, "Error: no pudo cargarse la información en la Base de Datos.");
    }

    public void cargaItem() {
        optionPaneOk.showMessageDialog(null, "El item de la carta fue cargado con éxito a la base de datos.");
    }

    public void errorPriceCost() {
        optionPaneOk.showMessageDialog(null, "El precio de venta es inferior al costo.");
    }

    public int errorPriceNull() {
        int dialogResult = JOptionPane.showOptionDialog(null, "¿Estás seguro de que quieres que el precio de venta sea 0?", "Confirmar precio 0", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Aceptar"}, null);
        return dialogResult;
    }

    public void errorSeleccion() {
        optionPaneOk.showMessageDialog(null, "Ningún elemento fue seleccionado.");
    }

    public void errorWaiterNull() {
        optionPaneOk.showMessageDialog(null, "Antes debe seleccionar un mozo del panel ubicado arriba a la derecha.");
    }

    public void errorTableNull() {
        optionPaneOk.showMessageDialog(null, "Para realizar esta acción antes debe seleccionar una mesa.");
    }

    public void errorItemsTableNull() {
        optionPaneOk.showMessageDialog(null, "Para realizar esta acción antes debe haber items cargados en la mesa.");

    }

    public void cargaTableErase() {
        optionPaneOk.showMessageDialog(null, "La mesa fue cerrada con éxito.");
    }

    public int cargaNewWaiter() {
        int dialogResult = JOptionPane.showOptionDialog(null, "¿Estás seguro de que quieres modificar el mozo?", "Confirmar Cambio de mozo", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Aceptar"}, null);
        return dialogResult;
    }

    public void errorItemGift(String item) {
        optionPaneOk.showMessageDialog(null, "No queda " + item + " por obsequiar en esta mesa");
    }

    public void cargaGift(String item) {
        optionPaneOk.showMessageDialog(null, "Se ingresó " + item + " a la lista de obsequios");
    }

    public int cargaConfirmarCierre() {
        int dialogResult = JOptionPane.showOptionDialog(null, "Si confirma el cierre de mesa, no podrá \n agregar Obsequios ni descuentos", "CONFIRMAR CIERRE DE MESA", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Confirmar"}, null);
        return dialogResult;
    }

    public void errorBillSend() {
        optionPaneOk.showMessageDialog(null, "La cuenta ya fue enviada, no se puede realizar la acción.");
    }

    public void errorTotalLess() {
        optionPaneOk.showMessageDialog(null, "No se registra error: el monto ingtresado es mayor al Total de la cuenta.");
    }

//    public void errorTotalEqual() {
//        optionPaneOk.showMessageDialog(null, "No se registra error: el monto ingtresado es igual al Total de la cuenta.");
//    }
    public void errorMountNull() {
        optionPaneOk.showMessageDialog(null, "El monto ingtresado es nulo.");
    }

    public void cargaError() {
        optionPaneOk.showMessageDialog(null, "El error fue ingresado al sistema.");
    }

    public void errorBillUnsend() {
        optionPaneOk.showMessageDialog(null, "La acción no puede realizarse,\nel cierre de mesa no ha sido iniciado.");
    }

    public void errorItemNull() {
        optionPaneOk.showMessageDialog(null, "No seleccionó ningún Item.");
    }

    public void errorDiscountRepeat() {
        optionPaneOk.showMessageDialog(null, "Ya fue aplicado un descuento \ny no puede ser modificado.");
    }

    public int cargaConfirmarMontoError(double in, double out) {
        int dialogResult = JOptionPane.showOptionDialog(null, "¿Cónfirma que el monto ingresado es de " + in + "\n"
                + "y el faltante de $" + out + "?", "CONFIRMAR CIERRE DE MESA", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Confirmar"}, null);
        return dialogResult;
    }

    public int cargaConfirmarItemsError(int num) {
        int dialogResult = JOptionPane.showOptionDialog(null, "¿Cónfirma que faltó el pago de " + num + " items?", "CONFIRMAR CIERRE DE MESA", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Confirmar"}, null);
        return dialogResult;
    }

    public void errorIngresoNum() {
        optionPaneOk.showMessageDialog(null, "El número de la mesa no fue ingresado.");

    }

    public void errorIngresoPos() {
        optionPaneOk.showMessageDialog(null, "La ubicación de la mesa no fue ingresada.");

    }

    public void errorIngresoOpenTime() {
        optionPaneOk.showMessageDialog(null, "El momento de apertura de la mesa no fue ingresado.");

    }

    public void errorIngresoId() {
        optionPaneOk.showMessageDialog(null, "La mesa no tiene identificador.");
    }
    
    public char[] solicitudMod() {
        JPasswordField passwordField = new JPasswordField();
        Object[] message = {"Contraseña:", passwordField};

        int option = JOptionPane.showConfirmDialog(null, message, "Ingrese su contraseña", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            return passwordField.getPassword();
        } else {
            return null;
        }
    }

    public void errorMixedPayUp() {
            optionPaneOk.showMessageDialog(null, "Debe ingresar el pago mixto \n"
                    + "en la parte inferior del cuadro \n"
                    + "o cerrar la ventana y volverla abrir.");
    }
    
    
    
    
    
    
    
}

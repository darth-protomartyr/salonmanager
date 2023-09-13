package salonmanager.utilidades;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
        optionPaneOk.showMessageDialog(null, "No ha seleccionado un mozo.");
    }
}

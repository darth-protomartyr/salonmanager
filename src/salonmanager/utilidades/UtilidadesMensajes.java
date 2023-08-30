package salonmanager.utilidades;

import javax.swing.JFrame;


public class UtilidadesMensajes extends JFrame {

    private javax.swing.JOptionPane optionPaneOk;

//    public int paneEliminarIngre() {
//        int dialogResult = JOptionPane.showOptionDialog(null, "¿Estás seguro de que quieres eliminar el ingrediente?", "Eliminar Ingrediente", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, new Object[]{"Aceptar"}, null);
//        return dialogResult;
//    }
    public void registroExitoso() {
        optionPaneOk.showMessageDialog(null, "El usuario fue registrado con éxito.");
    }

    public void registroFallido() {
        optionPaneOk.showMessageDialog(null, "El registro no fue realizado.");
    }

    public void errorCantCharName() {
        optionPaneOk.showMessageDialog(null, "Error: el número de caracteres no debe superar los 30 ni ser menor a 2.");
    }

    public void mailRepeat() {
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

    public void dataNull() {
        optionPaneOk.showMessageDialog(null, "Error: Usted no ingresó todos los datos requeridos.");
    }

    public void userNull() {
        optionPaneOk.showMessageDialog(null, "Error: no hay un usuario registrado con el mail ingresado.");
    }

    public void accessDenied() {
        optionPaneOk.showMessageDialog(null, "Error: Acceso denegado.");
    }
}

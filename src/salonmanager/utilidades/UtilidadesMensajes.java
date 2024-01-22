package salonmanager.utilidades;

import javax.swing.JFrame;
import salonmanager.entidades.graphics.CustomDialog;
import salonmanager.entidades.graphics.CustomDialogConfirm;
import salonmanager.entidades.graphics.CustomDialogPass;
import salonmanager.entidades.graphics.CustomDialogTextIn;
import salonmanager.entidades.bussiness.User;

public class UtilidadesMensajes extends JFrame {

    private javax.swing.JOptionPane optionPaneOk;

    public void errorRegistroFallido() {
        CustomDialog cm = new CustomDialog("Error: No pudo realizarse el registro.", 2);
        cm.setVisible(true);

    }

    public void errorCantCharName() {
        CustomDialog cm = new CustomDialog("Error: El número de caracteres del nombre no debe superar los.", 2);
        cm.setVisible(true);
    }
    
    public void errorCantCharNameUser() {
        CustomDialog cm = new CustomDialog("Error: El número de caracteres no debe superar los 30.", 2);
        cm.setVisible(true);
    }

    public void errorPhoneNumber() {
        CustomDialog cm = new CustomDialog("Error: El teléfono puede tener de 5 a 30 caracteres y estar formado por números, espacios en blanco, '+' y '-'.", 2);
        cm.setVisible(true);
    }
    
    public void errorNameRepeat() {
        CustomDialog cm = new CustomDialog("Error: El nombre ya se encuentra ingresado en nuestra base de datos.", 2);
        cm.setVisible(true);
    }
    
    public void errorPhoneRepeat() {
        CustomDialog cm = new CustomDialog("Error: El teléfono ya se encuentra ingresado en nuestra base de datos.", 2);
        cm.setVisible(true);
    }

    public void errorMailRepeat() {
        CustomDialog cm = new CustomDialog("Error: El mail ya se encuentra ingresado en nuestra base de datos.", 2);
        cm.setVisible(true);
    }

    public void errorMail() {
        CustomDialog cm = new CustomDialog("Error: el mail ingresado no es válido.", 2);
        cm.setVisible(true);
    }

    public void errorPassChar() {
        CustomDialog cm = new CustomDialog("Error: el password debe tener entre 8 y 30 caracteres.", 2);
        cm.setVisible(true);
    }

    public void errorPassCoincidence() {
        CustomDialog cm = new CustomDialog("Error: las contraseñas ingresadas no coinciden.", 2);
        cm.setVisible(true);
    }

    public void errorDataNull() {
        CustomDialog cm = new CustomDialog("Error: Usted no ingresó todos los datos requeridos.", 2);
        cm.setVisible(true);
    }

    public void errorUserNull() {
        CustomDialog cm = new CustomDialog("Error: no hay un usuario registrado con el mail ingresado.", 2);
        cm.setVisible(true);
    }

    public void errorAccessDenied() {
        CustomDialog cm = new CustomDialog("Error: Acceso denegado.", 2);
        cm.setVisible(true);
    }

    public void errorCantCharDescription() {
        CustomDialog cm = new CustomDialog("Error: el número de caracteres el límite establecido en uno de los campos.", 2);
        cm.setVisible(true);
    }

    public void errorNumerico() {
        CustomDialog cm = new CustomDialog("Error: el dato ingresado no es numérico.", 2);
        cm.setVisible(true);
    }

    public void errorCantCharNum() {
        CustomDialog cm = new CustomDialog("Error: el número de caracteres del numero ingresado no debe superar los 12.", 2);
        cm.setVisible(true);
    }

    public void errorIngresoItem() {
        CustomDialog cm = new CustomDialog("Error: no se generó un item.", 2);
        cm.setVisible(true);
    }

    public void errorNameItem() {
        CustomDialog cm = new CustomDialog("Error: item debe tener un nombre.", 2);
        cm.setVisible(true);
    }

    public void errorPriceItem() {
        CustomDialog cm = new CustomDialog("Error: el item debe tener un precio.", 2);
        cm.setVisible(true);
    }

    public void errorCargaDB() {
        CustomDialog cm = new CustomDialog("Error: no pudo cargarse la información en la Base de Datos.", 2);
        cm.setVisible(true);
    }

    public void errorPriceCost() {
        CustomDialog cm = new CustomDialog("Error: el precio de venta es inferior al costo.", 2);
        cm.setVisible(true);
    }

    public void errorSeleccion() {
        CustomDialog cm = new CustomDialog("Error: Ningún elemento fue seleccionado.", 2);
        cm.setVisible(true);
    }

    public void errorWaiterNull() {
        CustomDialog cm = new CustomDialog("Error: Antes debe seleccionar una mesa, pedido en barra o delivery del panel ubicado a la izquierda.", 2);
        cm.setVisible(true);
    }

    public void errorItemsTableNull() {
        CustomDialog cm = new CustomDialog("Error: Imposible realizar esta acción antes debe haber items cargados en la orden.", 2);
        cm.setVisible(true);
    }

    public void errorItemGift(String item) {
        CustomDialog cm = new CustomDialog("Error: No queda " + item + " por obsequiar en esta orden.", 2);
        cm.setVisible(true);
    }

    public void errorBillSend() {
        CustomDialog cm = new CustomDialog("Error: La cuenta ya fue enviada, no se puede realizar la acción.", 2);
        cm.setVisible(true);
    }

    public void errorTotalLess() {
        CustomDialog cm = new CustomDialog("Error: el monto ingtresado es mayor al Total de la cuenta.", 2);
        cm.setVisible(true);
    }

    public void errorMountNull() {
        CustomDialog cm = new CustomDialog("Error: El monto ingtresado es nulo.", 2);
        cm.setVisible(true);
    }

    public void errorBillUnsend() {
        CustomDialog cm = new CustomDialog("Error: La acción no puede realizarse, el cierre de la orden no ha sido iniciado.", 2);
        cm.setVisible(true);
    }

    public void errorItemNull() {
        CustomDialog cm = new CustomDialog("Error: No seleccionó ningún Item.", 2);
        cm.setVisible(true);
    }

    public void errorDiscountRepeat() {
        CustomDialog cm = new CustomDialog("Error: Ya fue aplicado un descuento y no puede ser modificado.", 2);
        cm.setVisible(true);
    }

    public void errorIngresoNum() {
        CustomDialog cm = new CustomDialog("El número de la mesa no fue ingresado.", 2);
        cm.setVisible(true);
    }

    public void errorIngresoPos() {
        CustomDialog cm = new CustomDialog("Error: La ubicación de la mesa no fue ingresada.", 2);
        cm.setVisible(true);
    }

    public void errorIngresoOpenTime() {
        CustomDialog cm = new CustomDialog("Error: El momento de apertura de la oreden no fue ingresado.", 2);
        cm.setVisible(true);
    }

    public void errorIngresoId() {
        CustomDialog cm = new CustomDialog("Error: La orden no tiene identificador.", 2);
        cm.setVisible(true);
    }

    public void errorMixedPayUp() {
        CustomDialog cm = new CustomDialog("Error: Debe ingresar el pago mixto en la parte inferior del cuadro o cerrar la ventana y volverla abrir.", 2);
        cm.setVisible(true);
    }

    public void errorWorkshift() {
        CustomDialog cm = new CustomDialog("Error: Debe iniciar un turno antes de cargar órdenes.", 2);
        cm.setVisible(true);
    }

    public void errorCashierNull() {
        CustomDialog cm = new CustomDialog("Error: No hay un cajero para administrar el salón.", 2);
        cm.setVisible(true);
    }

    public boolean cargaConfirmarInicioTurno(String name, String lastName) {
        CustomDialogConfirm cdc = new CustomDialogConfirm("¿Cónfirma que desea iniciar un turno con el usuario " + name + " " + lastName + "?");
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean cargaConfirmarCierreTurno(String name, String lastName) {
        CustomDialogConfirm cdc = new CustomDialogConfirm("¿Cónfirma que desea cerrar el turno?");
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean cargaConfirmarCambioTurno(User user) {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Aún hay órdenes abiertas, ¿desea iniciar el próximo turno con otro usuario?");
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean cargaConfirmarFacturacion(double realMount, double error) {
        String mess = "";
//        double error = realMount - total;
        if (error == 0) {
            mess = "Confirma que el monto ingresado es de $" + realMount + "?";
        } else if (error < 0) {
            mess = "Confirma que el monto ingresado es de $" + realMount + " y hay un faltante de " + error * (-1) + "?";
        } else  {
            mess = "Confirma que el monto ingresado es de $" + realMount + " y hay un excedente de " + error + "?";
        }
        CustomDialogConfirm cdc = new CustomDialogConfirm(mess);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }
    
    public boolean cargaConfirmarMontoError(double in, double out) {
        CustomDialogConfirm cdc = new CustomDialogConfirm("¿Cónfirma que el monto ingresado es de $" + in + " y el faltante de $" + out + "?");
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }
    
    public boolean cargaConfirmarCierre() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si confirma el cierre de órdenes, no podrá agregar obsequios ni descuentos.");
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }
    
    public boolean cargaConfirmErrorPriceNull() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("¿Estás seguro de que quieres que el precio de venta sea 0?");
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }
  

    
    public void cargaError() {
        CustomDialog cm = new CustomDialog("El error fue ingresado al sistema.", 1);
        cm.setVisible(true);
    }
    
    public void cargaItem() {
        CustomDialog cm = new CustomDialog("El item de la Card fue cargado con éxito a la base de datos.", 1);
        cm.setVisible(true);
    }
    
    public void cargaTableErase() {
        CustomDialog cm = new CustomDialog("La orden fue cerrada con éxito.", 1);
        cm.setVisible(true);
    }
    
    public void cargaGift(String item) {
        CustomDialog cm = new CustomDialog("Se ingresó " + item + " a la lista de obsequios.", 1);
        cm.setVisible(true);
    }

    
    ////------------------------------Reubicar------------------------------------------
    public void errorTableResume() {
        CustomDialog cm = new CustomDialog("Error: no hay órdenes con esas características.", 2);
        cm.setVisible(true);
    }

    public void itemNull() {
        CustomDialog cm = new CustomDialog("Error: no se registraron items en esta lista.", 2);
        cm.setVisible(true);
    }

    public void confirmRealWsMount() {
        CustomDialog cm = new CustomDialog("Para cerrar la ventana debe confirmar el monto final del turno.", 3);
        cm.setVisible(true);
    }

    public void errorEmptyCause() {
        CustomDialog cm = new CustomDialog("Error: No se ingreso la causa del Error en la orden.", 2);
        cm.setVisible(true);        
    }

    void configNull() {
        CustomDialog cm = new CustomDialog("Error: No hay una configuración habilitada para esta sesión.", 2);
        cm.setVisible(true);        
    }

    public void erroNotNewButton() {
        CustomDialog cm = new CustomDialog("Error: Antes de crear un nuevo pedido debe dar inicio al anterior.", 2);
        cm.setVisible(true);         
    }

    public void errorInsufficientMount() {
        CustomDialog cm = new CustomDialog("Error: El monto ingresado no cubre el valor gastado.", 2);
        cm.setVisible(true); 
    }

    public void errorTableNull() {
        CustomDialog cm = new CustomDialog("Error: No se ha seleccionado una orden.", 2);
        cm.setVisible(true); 
    }

    public String requestIndication() {
        String indications = "";
        CustomDialogTextIn cdti = new CustomDialogTextIn("Ingrese indicaciones del cliente:");
        cdti.setVisible(true);
        indications = cdti.getText();
        return indications;
    }

    public char[] requestMod() {
        char[] chars = null;
        CustomDialogPass cdp = new CustomDialogPass("Ingrese su contraseña:");
        cdp.setVisible(true);
        chars = cdp.getChar();
        return chars;
    }

    public void errorMultipleIndications() {
        CustomDialog cm = new CustomDialog("Error: Las indicaciones se ingresan de forma individual para cada Item.", 2);
        cm.setVisible(true); 
    }

    public void errorIndiLenghtExcess() {
        CustomDialog cm = new CustomDialog("Error: El texto de las indicaciones excede el límite de caracteres.", 2);
        cm.setVisible(true);         
    }

    public void cargaUsuario() {
        CustomDialog cm = new CustomDialog("El usuario fue ingresado. El Administrador le asignara un rol a la brevedad.", 1);
        cm.setVisible(true);
    }


    public void errorDeliveryNull() {
        CustomDialog cm = new CustomDialog("Error: Debe crear o seleccionar un usuario Delivery para realizar el envío.", 2);
        cm.setVisible(true); 
    }
    
    public void errorConsumerNull() {
        CustomDialog cm = new CustomDialog("Error: Debe crear o seleccionar un cliente para realizar el envío.", 2);
        cm.setVisible(true); 
    }

    public void errorEmptyFields() {
        CustomDialog cm = new CustomDialog("Error: Todos los campos deben ser completados.", 2);
        cm.setVisible(true); 
    }

    public void errorNullDeli() {
        CustomDialog cm = new CustomDialog("Error: Aún no hay se han asignado datos al envío.", 2);
        cm.setVisible(true); 
    }
}
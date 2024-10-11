package salonmanager.utilidades;

import static java.lang.Double.parseDouble;
import javax.swing.JFrame;
import salonmanager.entidades.graphics.CustomDialog;
import salonmanager.entidades.graphics.CustomDialogConfirm;
import salonmanager.entidades.graphics.CustomDialogPass;
import salonmanager.entidades.graphics.CustomDialogTextIn;
import salonmanager.entidades.bussiness.User;
import salonmanager.entidades.graphics.CustomDialogTextInAlt;

public class UtilidadesMensajes extends JFrame {

    private javax.swing.JOptionPane optionPaneOk;

//Mensajes de confirmación    
//Mensajes de confirmación    
    public void successMoneyFlow(JFrame frame) {
        CustomDialog cm = new CustomDialog("La operación fue confirmada", 1, frame);
        cm.setVisible(true);
    }
    
    public void successError(JFrame frame) {
        CustomDialog cm = new CustomDialog("El error fue ingresado al sistema", 1, frame);
        cm.setVisible(true);
    }

    public void successItem(JFrame frame) {
        CustomDialog cm = new CustomDialog("El item de la Carta fue cargado con éxito a la base de datos", 1, frame);
        cm.setVisible(true);
    }

    public void successTableErase(JFrame frame) {
        CustomDialog cm = new CustomDialog("La orden fue cerrada con éxito", 1, frame);
        cm.setVisible(true);
    }

    public void successGift(String item, JFrame frame) {
        CustomDialog cm = new CustomDialog("Se ingresó " + item + " a la lista de obsequios", 1, frame);
        cm.setVisible(true);
    }

    public void successUsuarioRolNull(JFrame frame) {
        CustomDialog cm = new CustomDialog("El usuario fue ingresado, el Administrador le asignara un rol en brevedad", 1, frame);
        cm.setVisible(true);
    }
    
    public void successUsuarioRolFull(JFrame frame) {
        CustomDialog cm = new CustomDialog("El usuario fue ingresado", 1, frame);
        cm.setVisible(true);
    }

//    public void successWsDefer(JFrame frame) {
//        CustomDialog cm = new CustomDialog("La carga de datos de cierre de turno fue almacenada para el posterior arbitraje del administrador", 1, frame);
//        cm.setVisible(true);
//    }

    public void successAdvertNoData(JFrame frame) {
        CustomDialog cm = new CustomDialog("Si posee los datos de cierre de turno presione el botón ARBITRAR para que luego el administrador lo haga", 1, frame);
        cm.setVisible(true);
    }

    public void successAdvertNotOpen(JFrame frame) {
        CustomDialog cm = new CustomDialog("Se mostraran solo las órdenes cerradas del Turno Actual", 1, frame);
        cm.setVisible(true);
    }

    public void successMod(JFrame frame) {
        CustomDialog cm = new CustomDialog("Las modificaciones fueron realizadas con éxito", 1, frame);
        cm.setVisible(true);
    }

    public void successUserUpdate(JFrame frame) {
        CustomDialog cm = new CustomDialog("Se actualizó la información del Usuario", 1, frame);
        cm.setVisible(true);
    }

    public void successUpdatePriceItemActive(JFrame frame) {
        CustomDialog cm = new CustomDialog("Precios actualizados, el salon fue cerrado y deberá abrirlo para continuar con el turno", 1, frame);
        cm.setVisible(true);
    }

    public void successUpdateCaptionItemActive(JFrame frame) {
        CustomDialog cm = new CustomDialog("Categorías actualizadas, el salon fue cerrado y deberá abrirlo para continuar con el turno", 1, frame);
        cm.setVisible(true);
    }

    public void successErrorWs(JFrame frame) {
        CustomDialog cm = new CustomDialog("El turno fue cerrado pero deberá ser revisado por el administrador para completar su información", 1, frame);
        cm.setVisible(true); 
    }
    
    public void successCloseWsPending(JFrame frame) {
        CustomDialog cm = new CustomDialog("Se realizó el cierre de caja. A continuación se finalizará la sesión por cambio de turno.", 1, frame);
        cm.setVisible(true); 
    }
    
    public void successTablesLoaded(JFrame frame) {
        CustomDialog cm = new CustomDialog("Se se realizó correctamente la instalación del programa.", 1, frame);
        cm.setVisible(true); 
    }

    public void successCloseWs(JFrame frame) {
        CustomDialog cm = new CustomDialog("El cierre de caja fue exitoso.", 1, frame);
        cm.setVisible(true); 
    }
    
    public void successUpdateUsuario(JFrame frame) {
        CustomDialog cm = new CustomDialog("Los datos del usuario fueron actualizados", 1, frame);
        cm.setVisible(true);
    }
    
    //Mensajes Error
    //Mensajes Error
    public void errorError(JFrame frame) {
        CustomDialog cm = new CustomDialog("Se produjo un error en la carga de la base de datos", 2, frame);
        cm.setVisible(true);
    }
    
    public void errorPnlsMod() {
        CustomDialog cm = new CustomDialog("No hay items para modificar", 2, null);
        cm.setVisible(true);
    }

    public void errorModDisabled() {
        CustomDialog cm = new CustomDialog("Los cambios globales no están habilitados", 2, null);
        cm.setVisible(true);
    }

    public void errorUserSelected() {
        CustomDialog cm = new CustomDialog("No ha seleccionado usuario", 2, null);
        cm.setVisible(true);
    }

    public void errorWsOpen() {
        CustomDialog cm = new CustomDialog("Error: para corregir el faltante de dinero, el turno de la mesa debe estar cerrado", 2, null);
        cm.setVisible(true);
    }

    public void errorForbbidenValue() {
        CustomDialog cm = new CustomDialog("Error: el valor ingresado no se ajusta a los límites permitidos", 2, null);
        cm.setVisible(true);
    }

    public void errorMultipleIndications() {
        CustomDialog cm = new CustomDialog("Error: Las indicaciones se ingresan de forma individual para cada Item", 2, null);
        cm.setVisible(true);
    }

    public void errorIndiLenghtExcess() {
        CustomDialog cm = new CustomDialog("Error: El texto de las indicaciones excede el límite de caracteres", 2, null);
        cm.setVisible(true);
    }

    public void errorUnnecesaryOp() {
        CustomDialog cm = new CustomDialog("Error: es innecesario hacer el pago mixto", 2, null);
        cm.setVisible(true);
    }

    public void errorLackOfFunds() {
        CustomDialog cm = new CustomDialog("Error: fondos insuficientes", 2, null);
        cm.setVisible(true);
    }

    public void errorCommentNull() {
        CustomDialog cm = new CustomDialog("Error: Debe adjuntar la causa del faltante en los comentarios", 2, null);
        cm.setVisible(true);
    }

    public void errorNullDates() {
        CustomDialog cm = new CustomDialog("Error: el período consultado no arroja resultados", 2, null);
        cm.setVisible(true);
    }

    public void errorNullItemDates() {
        CustomDialog cm = new CustomDialog("Error: la consulta no arroja resultados", 2, null);
        cm.setVisible(true);
    }

    public void errorPeriodNull() {
        CustomDialog cm = new CustomDialog("Error: Antes debe seleccionar un período de tiempo", 2, null);
        cm.setVisible(true);
    }

    public void errorNullSector() {
        CustomDialog cm = new CustomDialog("Error: Aún no ha designado sectores a la configuración", 2, null);
        cm.setVisible(true);
    }

    public void errorNullTabsQ() {
        CustomDialog cm = new CustomDialog("Error: Aún no se han designado las cantidades de mesas de todos de los sectores", 2, null);
        cm.setVisible(true);
    }

    public void errorCatExcess() {
        CustomDialog cm = new CustomDialog("Error: ya se han seleccionado los 6 categorías permitidas", 2, null);
        cm.setVisible(true);
    }

    public void errorSpacesExcess() {
        CustomDialog cm = new CustomDialog("Error: ya se han seleccionado los 8 sectores permitidos", 2, null);
        cm.setVisible(true);
    }

    public void errorRegistroFallido() {
        CustomDialog cm = new CustomDialog("Error: No pudo realizarse el registro", 2, null);
        cm.setVisible(true);
    }

    public void errorCantCharName() {
        CustomDialog cm = new CustomDialog("Error: El nombre no debe superar los 30 caracteres", 2, null);
        cm.setVisible(true);
    }

    public void errorCantCharNameUser() {
        CustomDialog cm = new CustomDialog("Error: El número de caracteres no debe superar los 30", 2, null);
        cm.setVisible(true);
    }

    public void errorPhoneNumber() {
        CustomDialog cm = new CustomDialog("Error: El teléfono puede tener de 5 a 30 caracteres y estar formado por números, espacios en blanco, '+' y '-'", 2, null);
        cm.setVisible(true);
    }

    public void errorNameRepeat() {
        CustomDialog cm = new CustomDialog("Error: El nombre ya se encuentra ingresado en nuestra base de datos", 2, null);
        cm.setVisible(true);
    }

    public void errorPhoneRepeat() {
        CustomDialog cm = new CustomDialog("Error: El teléfono ya se encuentra ingresado en nuestra base de datos", 2, null);
        cm.setVisible(true);
    }

    public void errorMailRepeat() {
        CustomDialog cm = new CustomDialog("Error: El mail ya se encuentra ingresado en nuestra base de datos", 2, null);
        cm.setVisible(true);
    }

    public void errorMail() {
        CustomDialog cm = new CustomDialog("Error: el mail ingresado no es válido", 2, null);
        cm.setVisible(true);
    }

    public void errorPassChar() {
        CustomDialog cm = new CustomDialog("Error: el password debe tener entre 8 y 30 caracteres", 2, null);
        cm.setVisible(true);
    }

    public void errorPassCoincidence() {
        CustomDialog cm = new CustomDialog("Error: las contraseñas ingresadas no coinciden", 2, null);
        cm.setVisible(true);
    }

    public void errorDataNull() {
        CustomDialog cm = new CustomDialog("Error: Usted no ingresó todos los datos requeridos", 2, null);
        cm.setVisible(true);
    }

    public void errorUserNull() {
        CustomDialog cm = new CustomDialog("Error: no hay un usuario registrado con el mail ingresado", 2, null);
        cm.setVisible(true);
    }

    public void errorAccessDenied() {
        CustomDialog cm = new CustomDialog("Error: Acceso denegado", 2, null);
        cm.setVisible(true);
    }

    public void errorCantCharDescription() {
        CustomDialog cm = new CustomDialog("Error: el número de caracteres el límite establecido en uno de los campos", 2, null);
        cm.setVisible(true);
    }

    public void errorNumerico() {
        CustomDialog cm = new CustomDialog("Error: el dato ingresado no es numérico", 2, null);
        cm.setVisible(true);
    }

    public void errorCantCharNum() {
        CustomDialog cm = new CustomDialog("Error: el número de caracteres del numero ingresado no debe superar los 12", 2, null);
        cm.setVisible(true);
    }

    public void errorIngresoItem() {
        CustomDialog cm = new CustomDialog("Error: no se generó un item", 2, null);
        cm.setVisible(true);
    }

    public void errorNameItem() {
        CustomDialog cm = new CustomDialog("Error: item debe tener un nombre", 2, null);
        cm.setVisible(true);
    }

    public void errorPriceItem() {
        CustomDialog cm = new CustomDialog("Error: el item debe tener un precio", 2, null);
        cm.setVisible(true);
    }

    public void errorCargaDB() {
        CustomDialog cm = new CustomDialog("Error: no pudo cargarse la información en la Base de Datos", 2, null);
        cm.setVisible(true);
    }

    public void errorSeleccion() {
        CustomDialog cm = new CustomDialog("Error: Ningún item fue seleccionado", 2, null);
        cm.setVisible(true);
    }

    public void errorWaiterNull() {
        CustomDialog cm = new CustomDialog("Error: Antes debe seleccionar una mesa, pedido en barra o delivery del panel ubicado a la izquierda", 2, null);
        cm.setVisible(true);
    }

    public void errorItemsTableNull() {
        CustomDialog cm = new CustomDialog("Error: Imposible realizar esta acción antes debe haber items cargados en la orden", 2, null);
        cm.setVisible(true);
    }

    public void errorItemGift(String item) {
        CustomDialog cm = new CustomDialog("Error: No queda " + item + " por obsequiar en esta orden", 2, null);
        cm.setVisible(true);
    }

    public void errorBillSend() {
        CustomDialog cm = new CustomDialog("Error: La cuenta ya fue enviada, no se puede realizar la acción", 2, null);
        cm.setVisible(true);
    }

    public void errorTotalLess() {
        CustomDialog cm = new CustomDialog("Error: el monto ingtresado es mayor o igual al Total de la cuenta", 2, null);
        cm.setVisible(true);
    }

    public void errorMountNull() {
        CustomDialog cm = new CustomDialog("Error: No ha ingresado ingresado ningún monto", 2, null);
        cm.setVisible(true);
    }

    public void errorBillUnsend() {
        CustomDialog cm = new CustomDialog("Error: La acción no puede realizarse, el cierre de la orden no ha sido iniciado", 2, null);
        cm.setVisible(true);
    }

    public void errorItemNull() {
        CustomDialog cm = new CustomDialog("Error: No seleccionó ningún Item", 2, null);
        cm.setVisible(true);
    }

    public void errorDiscountRepeat() {
        CustomDialog cm = new CustomDialog("Error: Ya fue aplicado un descuento y no puede ser modificado", 2, null);
        cm.setVisible(true);
    }

    public void errorIngresoNum() {
        CustomDialog cm = new CustomDialog("El número de la mesa no fue ingresado", 2, null);
        cm.setVisible(true);
    }

    public void errorIngresoPos() {
        CustomDialog cm = new CustomDialog("Error: La ubicación de la mesa no fue ingresada", 2, null);
        cm.setVisible(true);
    }

    public void errorIngresoOpenTime() {
        CustomDialog cm = new CustomDialog("Error: El momento de apertura de la oreden no fue ingresado", 2, null);
        cm.setVisible(true);
    }

    public void errorIngresoId() {
        CustomDialog cm = new CustomDialog("Error: La orden no tiene identificador", 2, null);
        cm.setVisible(true);
    }

    public void errorMixedPayUp() {
        CustomDialog cm = new CustomDialog("Error: Debe ingresar el pago mixto en la parte inferior del cuadro o cerrar la ventana y volverla abrir", 2, null);
        cm.setVisible(true);
    }

    public void errorWorkshift() {
        CustomDialog cm = new CustomDialog("Error: Debe iniciar un turno antes de iniciar esta acción", 2, null);
        cm.setVisible(true);
    }

    public void errorCashierNull() {
        CustomDialog cm = new CustomDialog("Error: No hay un cajero para administrar el salón", 2, null);
        cm.setVisible(true);
    }

    public void errorNameLength(int large) {
        CustomDialog cm = new CustomDialog("Error: el nombre no debe tener más de " + large + " caracteres", 2, null);
        cm.setVisible(true);
    }

    public void errorUnconfirmTable() {
        CustomDialog cm = new CustomDialog("Error: No ha cofirmado los sectores y/o las cantidades de mesas", 2, null);
        cm.setVisible(true);
    }

    public void errorTableResume() {
        CustomDialog cm = new CustomDialog("Error: no hay órdenes con esas características", 2, null);
        cm.setVisible(true);
    }

    public void itemNull() {
        CustomDialog cm = new CustomDialog("Error: no se registraron items en esta lista", 2, null);
        cm.setVisible(true);
    }

    public void errorEmptyCause() {
        CustomDialog cm = new CustomDialog("Error: debe ingresar el comentario", 2, null);
        cm.setVisible(true);
    }

    void configNull() {
        CustomDialog cm = new CustomDialog("Error: No hay una configuración habilitada", 2, null);
        cm.setVisible(true);
    }

    public void erroNotNewButton() {
        CustomDialog cm = new CustomDialog("Error: Antes de crear un nuevo pedido debe dar inicio al anterior", 2, null);
        cm.setVisible(true);
    }

    public void errorInsufficientMount() {
        CustomDialog cm = new CustomDialog("Error: El monto ingresado no cubre el valor gastado", 2, null);
        cm.setVisible(true);
    }

    public void errorTableNull() {
        CustomDialog cm = new CustomDialog("Error: No se ha seleccionado una orden", 2, null);
        cm.setVisible(true);
    }

    public void errorCat() {
        CustomDialog cm = new CustomDialog("Error: no hay Items en esta Categoría", 2, null);
        cm.setVisible(true);
    }

    public void errorCatSelection() {
        CustomDialog cm = new CustomDialog("Error: No ha seleccionado una categoría", 2, null);
        cm.setVisible(true);
    }

    public void errorCatEqual() {
        CustomDialog cm = new CustomDialog("Error: la categoría seleccionada es igual a la del item", 2, null);
        cm.setVisible(true);
    }

    public void errorDeliveryNull() {
        CustomDialog cm = new CustomDialog("Error: Debe crear o seleccionar un usuario Delivery para realizar el envío", 2, null);
        cm.setVisible(true);
    }

    public void errorConsumerNull() {
        CustomDialog cm = new CustomDialog("Error: Debe crear o seleccionar un cliente para realizar el envío", 2, null);
        cm.setVisible(true);
    }

    public void errorEmptyFields() {
        CustomDialog cm = new CustomDialog("Error: Todos los campos deben ser completados", 2, null);
        cm.setVisible(true);
    }
    
    public void errorEmptyFieldsCmr() {
        CustomDialog cm = new CustomDialog("Error: Nombre Apellido y teléfono deben ser completados", 2, null);
        cm.setVisible(true);
    }

    public void errorNullDeli() {
        CustomDialog cm = new CustomDialog("Error: Aún no hay se han asignado datos al envío", 2, null);
        cm.setVisible(true);
    }

    public void errorWsPendient() {
        CustomDialog cm = new CustomDialog("Error: Hay un Turno pendiente y no se puede reconfigurar el salón", 2, null);
        cm.setVisible(true);
    }
    
    public void errorDiferentCashier() {
        CustomDialog cm = new CustomDialog("Error: Hay un turno iniciado con cajero diferente al usuario con el que inició la sesión actual", 2, null);
        cm.setVisible(true);
    }

    public void errorCategoriesNull() {
        CustomDialog cm = new CustomDialog("Error: Debe ingresar hasta 6 categorías de venta", 2, null);
        cm.setVisible(true);
    }
    
    public void errorTabsToResolve() {
        CustomDialog cm = new CustomDialog("Error: Para continuar debe resolver las mesas del turno", 2, null);
        cm.setVisible(true); 
    }
    
    public void errorSumCorrection() {
        CustomDialog cm = new CustomDialog("Error: el monto ingresado es superior al faltante", 2, null);
        cm.setVisible(true);
    }

    public void errorSaveTable() {
        CustomDialog cm = new CustomDialog("Error: no se logró guardar la orden en la base de datos", 2, null);
        cm.setVisible(true); 
    }

    public void errorNullWaiter() {
        CustomDialog cm = new CustomDialog("Error: no hay mozos registrados en la base de datos", 2, null);
        cm.setVisible(true); 
    }
    
    public void errorNullOpWaiter() {
        CustomDialog cm = new CustomDialog("Error: no hay operaciones registradas con mozos", 2, null);
        cm.setVisible(true); 
    }
    
    public void errorCloseSalon() {
        CustomDialog cm = new CustomDialog("Error: el salón se cerrará, abralo nuevamente y resuelva el Turno anterior", 2, null);
        cm.setVisible(true); 
    }
    
    public void errorNullClient() {
        CustomDialog cm = new CustomDialog("Error: no hay clientes registrados en la base de datos", 2, null);
        cm.setVisible(true); 
    }
    
    public void errorSumError() {
        CustomDialog cm = new CustomDialog("Error: Las sumas de dinero en efectivo y trasferencias no coincide con el total ingresado", 2, null);
        cm.setVisible(true); 
    }

    public void errorSumOverMount() {
        CustomDialog cm = new CustomDialog("Error: el monto registrado supera el total consumido", 2, null);
        cm.setVisible(true); 
    }
    

    //Mensaje de optativos
    //Mensaje de optativos
    public boolean optionConfirmRestart() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si presiona ACEPTAR se realizará la acción, el programa se cerrará y deberá reiniciarlo", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean errorPriceCost() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Error: el precio de venta es inferior o igual al costo. Si presiona ACEPTAR, la operación se realizará de todos modos", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarConfigSalon() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si está seguro de que quiere configurar el salón, presione ACEPTAR", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarInicioTurno(String name, String lastName) {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si desea iniciar un turno con el usuario " + name + " " + lastName + ", presione ACEPTAR", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmRealWsMount() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR si NO desea cerrar el turno", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarCierreTurno(String name, String lastName) {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si presionea ACEPTAR, el turno se cerrará", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarCambioTurno(User user) {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si desea cerrar el turno a pesar de que aún hay órdenes abiertas, presione ACEPTAR", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarCierreVentana() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR si desea cerrar la ventana", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarCierrePrograma() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR si desea cerrar el programa?", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmErrorPriceNull() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR si está seguro de que quiere que el precio de venta sea 0 (cero)", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarFacturacion(double realMount, double error) {
        String mess = "";
        if (error == 0) {
            mess = "Presione ACEPTAR para confirmar que el monto ingresado es de $" + realMount + ".";
        } else if (error < 0) {
            mess = "Presione ACEPTAR para confirmar que el monto ingresado es de $" + realMount + " y hay un faltante de $" + error * (-1);
        } else {
            mess = "Presione ACEPTAR para confirmar que el monto ingresado es de $" + realMount + " y hay un excedente de $" + error;
        }
        CustomDialogConfirm cdc = new CustomDialogConfirm(mess, 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }
    
    public boolean optionConfirmarCorrection(double sum, double error) {
        String mess = "";
        if (sum == error) {
            mess = "Presione ACEPTAR para confirmar que el monto ingresado es de $" + sum + ".";
        } else {
            double missing = error - sum; 
            mess = "Presione ACEPTAR para confirmar que el monto ingresado es de $" + sum + " y hay un excedente de $" + missing;
        }
        CustomDialogConfirm cdc = new CustomDialogConfirm(mess, 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }
    

    public boolean optionConfirmarMontoError(double in, double out) {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR para cónfirmar que el monto ingresado es de $" + in + " y el faltante  es de $" + out, 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarCierre() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR para confirmar el cierre de la órden, recuerde que no podrá agregar obsequios ni descuentos", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarCambioCat() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR para confirmar que quiere cambiar la categoría de los Items seleccionados", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarCambioPrice() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR para confirmar que quiere cambiar el precio de los Items seleccionados", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionWorkshiftEmpty() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("No se registran movimientos. Presione ACEPTAR para confirmar que desea descartarlo y cerrar el turno", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarConfiguracion() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR si está seguro de que desea configurar el salón", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarCambioPrAct() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Hay mesas abiertas que ordenaron el item modificado, presione ACEPTAR si desea continuar la operación", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmLowerPrice() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR para confirmar que desea ingresar un precio final inferior o igual al costo", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmarUpdateActiveTabs() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si desea que el precio modificado se ACTUALICE en las mesas que ya lo ordenaron, presione ACTUALIZAR", 2);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmErrorInsuf() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("El monto es insuficiente y no podrá ser corregido luego. Presione ACEPTAR para confirmar la operación", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }

    public boolean optionConfirmErrorSuf() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Presione ACEPTAR para confirmar que el monto cubre el error",1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;
    }
    
    public boolean optionConfirmarNuevoTurno() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si NO posee el dinero ni la información del turno anterior, presione ACEPTAR para abrir un nuevo turno", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;        
    }

    public boolean optionConfirmarOpenTabsOldWs() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("El turno anterior posee mesas abiertas, presione ACEPTAR para resolver dichas mesas y abrir turno", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;    
    }

    public boolean optionConfirmarCierreTurnoError() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si posee el dinero y la información del turno anterior, presione ACEPTAR para completar la información", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;       
    }

    public boolean optionConfirmAddTables() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("Si en el turno anterior se facturaron nuevas mesas, presione ACEPTAR para optionr la información", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;  
    }

    public boolean optionConfirmNewTab() {
        CustomDialogConfirm cdc = new CustomDialogConfirm("La mesa fue cargada. Si desea ingresar una nueva mesa presione ACEPTAR", 1);
        cdc.setVisible(true);
        boolean confirm = cdc.getConfirm();
        return confirm;  
    }

    ////-----------------------------Value Takers--------------------------------------
    ////-----------------------------Value Takers--------------------------------------
    public String requestIndication() {
        String indications = "";
        CustomDialogTextIn cdti = new CustomDialogTextIn("Indicaciones Cliente", "Ingrese indicaciones del cliente:", 1);
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

    public String requestPass() {
        String indications = "";
        CustomDialogTextIn cdti = new CustomDialogTextIn("Confirmar operación", "Ingrese su clave para confirmar:", 2);
        cdti.setVisible(true);
        indications = cdti.getText();
        return indications;
    }

    public String requestNewString(int i, int large) {
        String st = "";
        String tit = "";
        String question = "";
        if (i == 1) {
            tit = "Nuevo espacio";
            question = "Ingrese el nombre de un nuevo espacio de hasta 15 caracteres";
        } else if (i == 2) {
            tit = "Nueva Categoría";
            question = "Ingrese el nombre de una nueva categoría de hasta 10 caracteres";
        }
        
        CustomDialogTextInAlt cdti = new CustomDialogTextInAlt(tit, question, large);
        cdti.setVisible(true);
        st = cdti.getText();
        if (i == 1) {
            st = st.toLowerCase();
        } else if (i == 2) {
            st = st.toUpperCase();
        }
        return st;
    }


    public double requestErrorCash() {
        double cash = 0;
        String indications = "";
        CustomDialogTextIn cdti = new CustomDialogTextIn("Ingresar efectivo", "Ingrese el monto en efectivo:", 3);
        cdti.setVisible(true);
        indications = cdti.getText();
        try {
            cash = parseDouble(indications);
        }  catch (NumberFormatException e) {
            errorNumerico();
            cdti.setText("");
        }
        return cash;
    }

    public double requestErrorElec() {
        double cash = 0;
        String indications = "";
        CustomDialogTextIn cdti = new CustomDialogTextIn("Ingresar transferencias", "Ingrese el monto de las transferencias electrónicas:", 3);
        cdti.setVisible(true);
        indications = cdti.getText();
        try {
            cash = parseDouble(indications);
        }  catch (NumberFormatException e) {
            errorNumerico();
            cdti.setText("");
        }
        return cash;
    }

    public String requestCause() {
        String indications = "";
        CustomDialogTextIn cdti = new CustomDialogTextIn("Causa Error", "Ingrese la causa del Error:", 1);
        cdti.setVisible(true);
        indications = cdti.getText();
        return indications;
    }
    
    ////------------------------------Reubicar------------------------------------------
    ////------------------------------Reubicar------------------------------------------
}
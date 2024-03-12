package salonmanager.persistencia;
import java.sql.SQLException;
import salonmanager.entidades.bussiness.CashFlow;
import salonmanager.utilidades.Utilidades;
import salonmanager.utilidades.UtilidadesMensajes;

public class DAOCashFlow extends DAO {

    Utilidades utili = new Utilidades();
    UtilidadesMensajes utiliMsg = new UtilidadesMensajes();

    public void saveCashFlow(CashFlow cf) throws Exception {
        try {
            String sql1 = "INSERT INTO workshift_flows(workshift_flow_kind, workshift_flow_m_k, workshift_flow_amount, workshift_flow_comment, workshift_flow_time, workshift_id, workshift_flow_active)"
                    + "VALUES(" + cf.isCashFwKind() + ", " + cf.getCashFwWsId() + ", " + cf.getCashFwAmount() + ", '" + cf.getCashFwComment() + "', '" + cf.getCashFwTime() + "', "   + cf.getCashFwWsId() + ", "  + cf.isCashFwActive() + ");";
            System.out.println(sql1);
            insertarModificarEliminar(sql1.trim());
            utiliMsg.cargaCashFlowSuccess();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                utiliMsg.errorCargaDB();
            } else {
                e.printStackTrace();
            }
        } finally {
            desconectarBase();
        }
    }
}

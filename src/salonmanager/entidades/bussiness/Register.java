package salonmanager.entidades.bussiness;

import java.sql.Timestamp;

public class Register {

    int id;
    Timestamp ejecution;
    String userId;
    String objectKind;
    String objectId;
    String operation;

    public Register() {
    }

    public Register(Timestamp ejecution, String userId, String objectKind, String objectId, int opKind, String modification) {
        String operation = "";
        if (opKind == 1) {
            operation = "CREACION";
        } else if (opKind == 2) {
            operation = "MODIFICACION";
        } else if (opKind == 3) {
            operation = "CONSULTA";
        } else if (opKind == 4) {
            operation = "ALTA";
        } else if (opKind == 5) {
            operation = "BAJA";
        }
        operation += " - " + modification;
        this.ejecution = ejecution;
        this.userId = userId;
        this.objectKind = objectKind;
        this.objectId = objectId;
        this.operation = operation;
    }

    
    public Register(int id, Timestamp ejecution, String userId, String objectKind, String objectId, int opKind, String modification) {
        String operation = "";
        if (opKind == 1) {
            operation = "CREACIÓN";
        } else if (opKind == 2) {
            operation = "MODIFICACIÖN";
        } else if (opKind == 3) {
            operation = "CONSULTA";
        } else if (opKind == 4) {
            operation = "ALTA";
        } else if (opKind == 5) {
            operation = "BAJA";
        }
        operation += " - " + modification;
        this.id = id;
        this.ejecution = ejecution;
        this.userId = userId;
        this.objectKind = objectKind;
        this.objectId = objectId;
        this.operation = operation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getEjecution() {
        return ejecution;
    }

    public void setEjecution(Timestamp ejecution) {
        this.ejecution = ejecution;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getObjectKind() {
        return objectKind;
    }

    public void setObjectKind(String objectKind) {
        this.objectKind = objectKind;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
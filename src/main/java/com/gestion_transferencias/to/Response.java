package com.gestion_transferencias.to;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Response {
    private  boolean exito;
    private String msj;

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }
}

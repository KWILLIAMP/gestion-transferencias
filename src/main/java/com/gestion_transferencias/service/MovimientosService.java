package com.gestion_transferencias.service;

import com.gestion_transferencias.entidad.Movimientos;
import com.gestion_transferencias.to.ReporteTO;
import com.gestion_transferencias.to.Response;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


public interface MovimientosService {
    List<Movimientos> getAllMovimientos();

    Movimientos getMovimientosById(Long id);
    Response createMovimientos(Movimientos movimientos);
    Movimientos updateMovimientos(Long id, Movimientos movimientosDetails);
    void deleteMovimientos(Long id);

    List<ReporteTO> reporteFecha(String fDesde , String fHasta );
    List<ReporteTO> reporteUsuario(Long fecha );
}

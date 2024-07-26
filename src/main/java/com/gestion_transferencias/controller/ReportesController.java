package com.gestion_transferencias.controller;

import com.gestion_transferencias.entidad.Movimientos;
import com.gestion_transferencias.service.MovimientosService;
import com.gestion_transferencias.to.ReporteTO;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

    @Autowired
    private MovimientosService movimientosService;

    @GetMapping("/fecha")
    public List<ReporteTO> getFecha(@RequestParam(name = "fdesde")String  fDesde , @RequestParam(name = "fhasta")  String fHasta) {
        return movimientosService.reporteFecha( fDesde , fHasta );

    }
    @GetMapping("/usuario/{user}")
    public  List<ReporteTO> getUsuario(@PathVariable Long user) {
        return movimientosService.reporteUsuario(user);
    }
}

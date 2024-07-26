package com.gestion_transferencias.controller;
import com.gestion_transferencias.entidad.Movimientos;
import com.gestion_transferencias.service.MovimientosService;
import com.gestion_transferencias.to.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
public class MovimientosController {


    @Autowired
    private MovimientosService movimientosService;

    @GetMapping("/list")
    public List<Movimientos> getAllMovimientos() {
        return movimientosService.getAllMovimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimientos> getMovimientosById(@PathVariable Long id) {
        Movimientos movimientos = movimientosService.getMovimientosById(id);
        if (movimientos != null) {
            return ResponseEntity.ok(movimientos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Response createMovimientos(@RequestBody Movimientos movimientos) {
        return movimientosService.createMovimientos(movimientos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movimientos> updateMovimientos(@PathVariable Long id, @RequestBody Movimientos movimientosDetails) {
        Movimientos updatedMovimientos = movimientosService.updateMovimientos(id, movimientosDetails);
        if (updatedMovimientos != null) {
            return ResponseEntity.ok(updatedMovimientos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovimientos(@PathVariable Long id) {
        movimientosService.deleteMovimientos(id);
        return ResponseEntity.noContent().build();
    }
}

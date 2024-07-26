package com.gestion_transferencias.controller;
import com.gestion_transferencias.entidad.Cuenta;
import com.gestion_transferencias.service.CuentaService;
import com.gestion_transferencias.to.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    private static final Logger log = LoggerFactory.getLogger(CuentaController.class);
    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/obtenercuentas")
    public List<Cuenta> getAllCuentas() {
        return cuentaService.getAllCuentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> getCuentaById(@PathVariable Long id) {
        Cuenta cuenta = cuentaService.getCuentaById(id);
        if (cuenta != null) {
            return ResponseEntity.ok(cuenta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public Response createCuenta(@RequestBody Cuenta cuenta) {
         return cuentaService.createCuenta(cuenta);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cuenta> updateCuenta(@PathVariable Long id, @RequestBody Cuenta cuentaDetails) {
        Cuenta updatedCuenta = cuentaService.updateCuenta(id, cuentaDetails);
        if (updatedCuenta != null) {
            return ResponseEntity.ok(updatedCuenta);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/elim/{id}")
    public Response deleteCuenta(@PathVariable Long id) {
        return cuentaService.deleteCuenta(id);
    }
}

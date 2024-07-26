package com.gestion_transferencias.service;

import com.gestion_transferencias.entidad.Cuenta;
import com.gestion_transferencias.to.Response;

import java.util.List;

public interface CuentaService {

    List<Cuenta> getAllCuentas();

    Cuenta getCuentaById(Long id);
    Response createCuenta(Cuenta cuenta);
    Cuenta updateCuenta(Long id, Cuenta cuentaDetails);

    Response deleteCuenta(Long id);
}

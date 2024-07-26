package com.gestion_transferencias.repository;

import com.gestion_transferencias.entidad.Cuenta;
import com.gestion_transferencias.entidad.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    List<Cuenta> findByClienteId(long clienteId);

    Cuenta findByIdCuenta(Long idCuenta);
}

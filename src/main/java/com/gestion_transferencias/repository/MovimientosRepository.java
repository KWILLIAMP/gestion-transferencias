package com.gestion_transferencias.repository;

import com.gestion_transferencias.entidad.Cuenta;
import com.gestion_transferencias.entidad.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

    List<Movimientos> findByIdCuenta(long idCuenta);

    List<Movimientos> findByFechaBetween(Date startDate, Date endDate);


}

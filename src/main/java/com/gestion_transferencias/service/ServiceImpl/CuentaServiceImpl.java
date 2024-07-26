package com.gestion_transferencias.service.ServiceImpl;
import com.gestion_transferencias.entidad.Cuenta;
import com.gestion_transferencias.repository.CuentaRepository;
import com.gestion_transferencias.service.CuentaService;
import com.gestion_transferencias.to.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    private static final Logger log = LoggerFactory.getLogger(CuentaServiceImpl.class);
    @Autowired
    private CuentaRepository cuentaRepository;

    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    public Cuenta getCuentaById(Long id) {
        Cuenta cuenta = new Cuenta();
        try {
           cuenta = cuentaRepository.findById(id).orElse(null);
        }catch (Exception e){
         cuenta = null;
        }
        return cuenta;
    }

    public Response createCuenta(Cuenta cuenta) {
        Response res = new Response();
        try {
            cuentaRepository.save(cuenta);
            res.setExito(true);
            res.setMsj("Registro OK");
        }catch (Exception e){
            log.info("ERROR : ", e);
            res.setExito(false);
            res.setMsj("Error Registro ");
        }
        return res;
    }

    public Cuenta updateCuenta(Long id, Cuenta cuentaDetails) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        cuenta.setTipoCuenta(cuentaDetails.getTipoCuenta());
        cuenta.setEstado(cuentaDetails.getEstado());
        cuenta.setClienteId(cuentaDetails.getClienteId());
        cuenta.setSaldoInicial(cuenta.getSaldoInicial());
        return cuentaRepository.save(cuenta);
    }

    public Response deleteCuenta(Long id) {
        Response res = new Response();
        try{
            cuentaRepository.deleteById(id);
            res.setExito(true);
            res.setMsj("Registro OK");
        }
        catch ( Exception e){
            log.info("ERROR : ", e);
            res.setExito(false);
            res.setMsj("Error Registro ");
        }
        return res;

    }
}

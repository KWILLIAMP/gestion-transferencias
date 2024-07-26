package com.gestion_transferencias.service.ServiceImpl;

import com.gestion_transferencias.entidad.Cuenta;
import com.gestion_transferencias.entidad.Movimientos;
import com.gestion_transferencias.repository.CuentaRepository;
import com.gestion_transferencias.repository.MovimientosRepository;
import com.gestion_transferencias.service.MovimientosService;
import com.gestion_transferencias.to.Cliente;
import com.gestion_transferencias.to.ReporteTO;
import com.gestion_transferencias.to.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.DateFormatter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovimientosServiceImpl implements MovimientosService {

    private static final Logger log = LoggerFactory.getLogger(MovimientosServiceImpl.class);
    @Autowired
    private MovimientosRepository movimientosRepository;

    @Autowired
    private CuentaRepository cuentaRepository;


    @Autowired
    private RestTemplate restTemplate;



    public List<Movimientos> getAllMovimientos() {

        return movimientosRepository.findAll();
    }

    public Movimientos getMovimientosById(Long id) {
        return movimientosRepository.findById(id).orElse(null);
    }

    public Response createMovimientos(Movimientos movimiento) {

        Response res = new Response();

        if(movimiento.getSaldo() <= 0 && movimiento.getValor() <= 0 ){
            res.setExito(false);
            res.setMsj("Saldo no disponible");
            return res;
        }
        double nuevoSaldo= movimiento.getSaldo() + movimiento.getValor();
        movimiento.setSaldo(nuevoSaldo);
        try{
            movimientosRepository.save(movimiento);

            res.setExito(true);
            res.setMsj("Registro movimiento OK");
        }catch ( Exception e){
            res.setExito( false );
            res.setMsj("Error en el Regsitro ");
            log.error("Error: ", e);

        }
        return res;
    }

    public Movimientos updateMovimientos(Long id, Movimientos movimientosDetails) {

        return null;
    }

    public void deleteMovimientos(Long id) {
        movimientosRepository.deleteById(id);
    }

    public List<ReporteTO> reporteFecha(String fDesde, String fHasta ){

        String cuentasUrl = "http://localhost:8080/clientes/{id}";
        List<ReporteTO> res = new ArrayList<>();
        List<Movimientos>  mvts = movimientosRepository.findByFechaBetween( cadenaAFecha(fDesde), cadenaAFecha(fHasta));
        if (!mvts.isEmpty()) {
            for ( Movimientos item : mvts ){
                ReporteTO obj = new ReporteTO();
                Cuenta cuenta = cuentaRepository.findByIdCuenta( item.getIdCuenta());
                Cliente cliente = restTemplate.getForObject(cuentasUrl, Cliente.class, cuenta.getClienteId());

                obj.setFecha(  dateACadena(item.getFecha()));
                if(  cliente != null){
                    obj.setCliente(cliente.getPersona().getNombre());
                }
                obj.setNroCuenta(cuenta.getNroCuenta());
                obj.setTipoCuenta(cuenta.getTipoCuenta());
                obj.setSaldoInicial(cuenta.getSaldoInicial());
                obj.setEstado(cuenta.getEstado());
                obj.setMovimiento(item.getValor());
                obj.setSaldoDisponible(item.getSaldo());

                res.add(obj);

            }
        }

        return res;
    }

    public  List<ReporteTO> reporteUsuario( Long usuarioId ){
        List<ReporteTO> lst = new ArrayList<>();
        String cuentasUrl = "http://localhost:8080/clientes/{id}";
        try{

            Cliente cliente = restTemplate.getForObject(cuentasUrl, Cliente.class, usuarioId);
            if( cliente == null){
                 return lst;
            }
            List<Cuenta> ctas = cuentaRepository.findByClienteId(usuarioId);
            for ( Cuenta item : ctas){

                List<Movimientos>  mvts = movimientosRepository.findByIdCuenta(item.getIdCuenta());
                ReporteTO res = new ReporteTO();

                if( !mvts.isEmpty() ){
                    for ( Movimientos itemMov : mvts ){

                        res.setFecha( dateACadena(itemMov.getFecha()) );

                        res.setMovimiento(itemMov.getValor());
                        res.setSaldoDisponible(itemMov.getSaldo());

                    }
                }

                res.setCliente(cliente.getPersona().getNombre());
                res.setNroCuenta(item.getNroCuenta());
                res.setTipoCuenta(item.getTipoCuenta());
                res.setSaldoInicial(item.getSaldoInicial());
                res.setEstado(item.getEstado());
                lst.add(res);
            }

        }catch (Exception e){
            log.error( "ERROR : " , e);
        }
        return lst;
    }

    private Date cadenaAFecha( String fecha ) {
        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd/MM/yyyy");

        try {
           return  inputFormatter.parse(fecha);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String dateACadena( Date fecha ){
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        return formato.format(fecha);
    }
}

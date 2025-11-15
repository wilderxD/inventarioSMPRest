package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Salida;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ISalidasDAO extends JpaRepository<Salida, Long>{
 
    public Optional<Salida> findByFactura(String factura);
    
}

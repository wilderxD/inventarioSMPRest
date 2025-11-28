package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Salida;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ISalidasDAO extends JpaRepository<Salida, Long>{
 
    public Optional<Salida> findByFactura(String factura);
    
    @Query("SELECT u FROM Salida u")
    Stream<Salida> streamAll();
    
}

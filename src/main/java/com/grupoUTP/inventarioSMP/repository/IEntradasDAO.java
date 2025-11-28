package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Entrada;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IEntradasDAO extends JpaRepository<Entrada, Long>{
    
    public Optional<Entrada> findByFactura(String factura);
    
    @Query("SELECT u FROM Entrada u")
    Stream<Entrada> streamAll();
    
}

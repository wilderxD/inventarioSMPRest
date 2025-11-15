package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Entrada;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IEntradasDAO extends JpaRepository<Entrada, Long>{
    
    public Optional<Entrada> findByFactura(String factura);
    
}

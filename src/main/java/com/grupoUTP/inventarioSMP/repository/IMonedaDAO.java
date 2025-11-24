package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IMonedaDAO extends JpaRepository<Moneda, Long>{
    
}

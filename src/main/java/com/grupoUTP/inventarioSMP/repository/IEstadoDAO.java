package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Estado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEstadoDAO extends JpaRepository<Estado, Long>{
    
}

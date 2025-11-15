package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IAsignadoDAO extends JpaRepository<Asignado, Long>{
    
}

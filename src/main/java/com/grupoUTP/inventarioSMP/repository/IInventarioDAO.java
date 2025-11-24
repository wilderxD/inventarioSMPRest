package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IInventarioDAO extends JpaRepository<Inventario, Long>{
    
}

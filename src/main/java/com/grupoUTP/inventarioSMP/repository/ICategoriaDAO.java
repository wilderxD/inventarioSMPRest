package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ICategoriaDAO extends JpaRepository<Categoria, Long>{
    
}

package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.entity.Inventario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IInventarioDAO extends JpaRepository<Inventario, Long>{
    
    Optional<Inventario> findByCategoria(Categoria categoria);
    
    @Query("SELECT COALESCE(SUM(i.entradas), 0) FROM Inventario i")
    Integer obtenerTotalEntregas();
    
    @Query("SELECT COALESCE(SUM(i.salidas), 0) FROM Inventario i")
    Integer obtenerTotalSalidas();
    
}

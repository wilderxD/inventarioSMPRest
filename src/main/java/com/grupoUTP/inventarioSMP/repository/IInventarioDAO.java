package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.entity.Inventario;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IInventarioDAO extends JpaRepository<Inventario, Long>{
    
    Optional<Inventario> findByCategoria(Categoria categoria);
    
    @Query("SELECT u FROM Inventario u")
    Stream<Inventario> streamAll();
    
    @Query("SELECT COALESCE(SUM(i.entradas), 0) FROM Inventario i")
    Integer obtenerTotalEntregas();
    
    @Query("SELECT COALESCE(SUM(i.salidas), 0) FROM Inventario i")
    Integer obtenerTotalSalidas();
    
}

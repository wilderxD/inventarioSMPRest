package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IAsignadoDAO extends JpaRepository<Asignado, Long>{
    
    @Query("SELECT u FROM Asignado u")
    Stream<Asignado> streamAll();
}

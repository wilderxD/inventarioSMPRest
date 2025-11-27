package com.grupoUTP.inventarioSMP.repository;

import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.entity.Estado;
import com.grupoUTP.inventarioSMP.entity.Moneda;
import com.grupoUTP.inventarioSMP.entity.Oficina;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IEquipoDAO extends JpaRepository<Equipo, Long>{
    
    @Query("SELECT u FROM Equipo u")
    Stream<Equipo> streamAll();
    
    @Query("from Moneda")
    public List<Moneda> findAllMonedas();
    
    @Query("from Estado")
    public List<Estado> findAllEstados();
    
    @Query("from Oficina")
    public List<Oficina> findAllOficinas();
}

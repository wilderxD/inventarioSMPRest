package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.entity.Salida;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ISalidaService {
    
    public List<Salida> findAll();
    
    public Page<Salida> findAll(Pageable pageable);
    
    public Salida findById(Long id);
    
    public Salida save(Salida salida);
    
    public void delete(Long id);
    
    public List<Equipo> findAllEquipos();
    
}

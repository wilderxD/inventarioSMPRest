package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Entrada;
import com.grupoUTP.inventarioSMP.entity.Equipo;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IEntradaService {
    
    public List<Entrada> findAll();
    
    public Page<Entrada> findAll(Pageable pageable);
    
    public Entrada findById(Long id);
    
    public Entrada save(Entrada entrada);
    
    public void delete(Long id);
    
    public List<Equipo> findAllEquipos();
    
}

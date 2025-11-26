package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.entity.Estado;
import com.grupoUTP.inventarioSMP.entity.Moneda;
import com.grupoUTP.inventarioSMP.entity.Oficina;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IEquipoService {
    
    public List<Equipo> findAll();
    
    public Page<Equipo> findAll(Pageable pageable);
    
    public Equipo findById(Long id);
    
    public Equipo save(Equipo equipo);
    
    public void delete (Long id);
    
    public List<Moneda> findAllMoneda();
    
    public List<Categoria> findAllCategoria();
    
    public List<Estado> findAllEstado();
    
    public List<Asignado> findAllAsignado();
    
    public List<Oficina> findAllOficina();
    
    public void descargarReportes(HttpServletResponse response, String formato);
    
}

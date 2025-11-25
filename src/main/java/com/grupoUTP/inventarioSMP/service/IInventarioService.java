package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.dto.ResumenDTO;
import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.entity.Inventario;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IInventarioService {
    
    public List<Inventario> findAll();
    
    public Page<Inventario> findAll(Pageable pageable);
    
    public Inventario findById(Long id);
    
    public Inventario save(Inventario inventario);
    
    public void delete(Long id);
    
    public void registrarIngreso(Categoria categoria);
    
    public void registrarSalida(Categoria categoria);
    
    public ResumenDTO obtenerResumenGeneral();
    
}

package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Categoria;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ICategoriaService {
    
    public List<Categoria> findAll();
    
    public Page<Categoria> findAll(Pageable pageable);
    
    public Categoria findById(Long id);
    
    public Categoria save (Categoria categoria);
    
    public void delete(Long id);
    
}

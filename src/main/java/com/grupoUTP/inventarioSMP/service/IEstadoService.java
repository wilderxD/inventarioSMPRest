package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Estado;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IEstadoService {
    public List<Estado> findAll();
    
    public Page<Estado> findAll(Pageable pageable);
    
    public Estado findById(Long id);
    
    public Estado save(Estado estado);
    
    public void delete(Long id);
}

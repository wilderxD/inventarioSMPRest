package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IAsignadoService {
    public List<Asignado> findAll();
    
    public Page<Asignado> findAll(Pageable pageable);
    
    public Asignado findById(Long id);
    
    public Asignado save(Asignado asignado);
    
    public void delete(Long id);
    
    public void descargarReportes(HttpServletResponse response, String formato) throws IOException;
}

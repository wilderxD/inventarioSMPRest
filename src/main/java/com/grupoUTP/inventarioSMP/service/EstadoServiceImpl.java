package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Estado;
import com.grupoUTP.inventarioSMP.repository.IEstadoDAO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor  
public class EstadoServiceImpl implements IEstadoService{
    
    private final IEstadoDAO estadoDAO;

    @Override
    public List<Estado> findAll() {
        return estadoDAO.findAll();
    }

    @Override
    public Page<Estado> findAll(Pageable pageable) {
        return estadoDAO.findAll(pageable);
    }

    @Override
    public Estado findById(Long id) {
        return estadoDAO.findById(id).orElse(null);
    }

    @Override
    public Estado save(Estado estado) {
        return estadoDAO.save(estado);
    }

    @Override
    public void delete(Long id) {
        estadoDAO.deleteById(id);
    }
    
}

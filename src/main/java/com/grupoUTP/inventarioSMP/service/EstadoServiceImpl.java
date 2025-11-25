package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Estado;
import com.grupoUTP.inventarioSMP.repository.IEstadoDAO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor  
public class EstadoServiceImpl implements IEstadoService{
    
    private final IEstadoDAO estadoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        return estadoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Estado> findAll(Pageable pageable) {
        return estadoDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Estado findById(Long id) {
        return estadoDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Estado save(Estado estado) {
        return estadoDAO.save(estado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        estadoDAO.deleteById(id);
    }
    
}

package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Oficina;
import com.grupoUTP.inventarioSMP.repository.IOficinaDAO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OficinaServiceImpl implements IOficinaService{
    
    private final IOficinaDAO oficinaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Oficina> findAll() {
        return oficinaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Oficina> findAll(Pageable pageable) {
        return oficinaDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Oficina findById(Long id) {
        return oficinaDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Oficina save(Oficina oficina) {
        return oficinaDAO.save(oficina);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        oficinaDAO.deleteById(id);
    }
    
}

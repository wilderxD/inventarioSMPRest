package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Oficina;
import com.grupoUTP.inventarioSMP.repository.IOficinaDAO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OficinaServiceImpl implements IOficinaService{
    
    private final IOficinaDAO oficinaDAO;

    @Override
    public List<Oficina> findAll() {
        return oficinaDAO.findAll();
    }

    @Override
    public Page<Oficina> findAll(Pageable pageable) {
        return oficinaDAO.findAll(pageable);
    }

    @Override
    public Oficina findById(Long id) {
        return oficinaDAO.findById(id).orElse(null);
    }

    @Override
    public Oficina save(Oficina oficina) {
        return oficinaDAO.save(oficina);
    }

    @Override
    public void delete(Long id) {
        oficinaDAO.deleteById(id);
    }
    
}

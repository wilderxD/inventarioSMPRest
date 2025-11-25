package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.repository.ICategoriaDAO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements ICategoriaService{
    
    private final ICategoriaDAO categoriaDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return categoriaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Categoria> findAll(Pageable pageable) {
        return categoriaDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Categoria findById(Long id) {
        return categoriaDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {
       return categoriaDAO.save(categoria);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        categoriaDAO.deleteById(id);
    }
    
}

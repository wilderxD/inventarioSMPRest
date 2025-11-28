package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Entrada;
import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.repository.IEntradasDAO;
import com.grupoUTP.inventarioSMP.repository.IEquipoDAO;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntradaServiceImpl implements IEntradaService{
        
        private final IEntradasDAO entradaDAO;
        
        private final IEquipoDAO equipoDAO;        
        
    @Override
    @Transactional(readOnly = true)
    public List<Entrada> findAll() {
        return entradaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Entrada> findAll(Pageable pageable) {
        return entradaDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Entrada findById(Long id) {
        return entradaDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Entrada save(Entrada entrada) {
        return entradaDAO.save(entrada);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entradaDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> findAllEquipos() {
        return equipoDAO.findAll();
    }

    @Override
    public void descargarReportes(HttpServletResponse response, String formato) throws IOException {
        
    }
    
}

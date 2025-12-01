package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import com.grupoUTP.inventarioSMP.repository.IAsignadoDAO;
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
public class AsignadoServiceImpl implements IAsignadoService{
    
    private final IAsignadoDAO asignadoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Asignado> findAll() {
        return asignadoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Asignado> findAll(Pageable pageable) {
        return asignadoDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Asignado findById(Long id) {
        return asignadoDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Asignado save(Asignado asignado) {
        return asignadoDAO.save(asignado);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        asignadoDAO.deleteById(id);
    }

    @Override
    public void descargarReportes(HttpServletResponse response, String formato) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

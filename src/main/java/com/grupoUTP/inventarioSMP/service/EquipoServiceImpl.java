package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.entity.Estado;
import com.grupoUTP.inventarioSMP.entity.Moneda;
import com.grupoUTP.inventarioSMP.entity.Oficina;
import com.grupoUTP.inventarioSMP.repository.IAsignadoDAO;
import com.grupoUTP.inventarioSMP.repository.ICategoriaDAO;
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
public class EquipoServiceImpl implements IEquipoService{
    
    private final IEquipoDAO equipoDAO;    
    private final ICategoriaDAO categoriaDAO;    
    private final IAsignadoDAO asignadoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> findAll() {
        return equipoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Equipo> findAll(Pageable pageable) {
        return equipoDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Equipo findById(Long id) {
        return equipoDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Equipo save(Equipo equipo) {      
        return equipoDAO.save(equipo);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        equipoDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Moneda> findAllMoneda() {
        return equipoDAO.findAllMonedas();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAllCategoria() {
        return categoriaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estado> findAllEstado() {
        return equipoDAO.findAllEstados();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asignado> findAllAsignado() {
        return asignadoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Oficina> findAllOficina() {
        return equipoDAO.findAllOficinas();
    }
    
    @Override
    @Transactional(readOnly = true)
    public void descargarReportes(HttpServletResponse response, String formato) throws IOException{
        try{
            
        }
    }
    
}

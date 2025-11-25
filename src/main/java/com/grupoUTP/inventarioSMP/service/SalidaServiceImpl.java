package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.entity.Salida;
import com.grupoUTP.inventarioSMP.repository.IEquipoDAO;
import com.grupoUTP.inventarioSMP.repository.ISalidasDAO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SalidaServiceImpl implements ISalidaService{
    
    private final ISalidasDAO salidaDAO;
    private final IEquipoDAO equipoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Salida> findAll() {
        return salidaDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Salida> findAll(Pageable pageable) {
        return salidaDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Salida findById(Long id) {
        return salidaDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Salida save(Salida salida) {
        return salidaDAO.save(salida);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        salidaDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipo> findAllEquipos() {
        return equipoDAO.findAll();
    }
    
}

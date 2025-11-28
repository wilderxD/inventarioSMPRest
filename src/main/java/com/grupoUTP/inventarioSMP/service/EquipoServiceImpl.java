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
import com.grupoUTP.inventarioSMP.utilitarios.ReporteExcel;
import com.grupoUTP.inventarioSMP.utilitarios.ReportePDF;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;
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
        try(Stream<Equipo> stream = equipoDAO.streamAll()){
            if("excel".equalsIgnoreCase(formato)){
                ReporteExcel<Equipo> exportar = new ReporteExcel<>("Equipo");
                String[] cabeceras = {"ID","Descripcion", "Categoria", "Asignado", "Moneda", "valor", "Estado", "Detalle"};
                
                exportar.exportar(response, stream, cabeceras, equipo -> new Object[]{
                    equipo.getId(),
                    equipo.getDescripcion(),
                    equipo.getCategoria().getDescripcion(),
                    equipo.getAsignado() != null ? equipo.getAsignado().getDescripcion() : "SIN ASIGNAR",
                    equipo.getMoneda().getDescripcion(),
                    equipo.getValor(),
                    equipo.getEstado().getDescripcion(),
                    equipo.getDetalle() != null ? equipo.getDetalle() : "sin detalles"
                });
            }else if("pdf".equalsIgnoreCase(formato)){
                ReportePDF<Equipo> exportar = new ReportePDF<>("Reporte de Equipos");
                
                String[] cabeceras = {"ID","Descripcion", "Categoria", "Asignado", "Moneda", "valor", "Estado", "Detalle"};
                float[] anchos = {1.5f, 4.0f, 4.0f, 4.0f, 2.0f, 2.0f, 2.0f, 5.0f};
                
                exportar.exportar(response, stream, cabeceras, anchos, equipo -> new String[]{
                    String.valueOf(equipo.getId()),
                    equipo.getDescripcion(),
                    String.valueOf(equipo.getCategoria().getDescripcion()),
                    String.valueOf(equipo.getAsignado() != null ? equipo.getCategoria().getDescripcion() : "SIN ASIGNAR"),
                    String.valueOf(equipo.getMoneda().getDescripcion()),
                    String.valueOf(equipo.getValor()),
                    String.valueOf(equipo.getEstado().getDescripcion()),
                    equipo.getDetalle()  == null ? "sin detalles" : equipo.getDetalle()
                });
            }
        }
    }
    
}

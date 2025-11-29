package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Entrada;
import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.repository.IEntradasDAO;
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
    @Transactional(readOnly = true)
    public void descargarReportes(HttpServletResponse response, String formato) throws IOException {
        try(Stream<Entrada> stream = entradaDAO.streamAll()){
            if("excel".equalsIgnoreCase(formato)){
                ReporteExcel<Entrada> exportar = new ReporteExcel<>("Entradas");
                String[] cabeceras = {"ID", "Factura", "Equipo", "Cantidad", "Fecha de Ingreso"};
                
                exportar.exportar(response, stream, cabeceras, entrada -> new Object[]{
                    entrada.getId(),
                    entrada.getFactura(),
                    entrada.getEquipo().getDescripcion(),
                    entrada.getCantidad(),
                    entrada.getCreateAt()
                });
            }else if("pdf".equalsIgnoreCase(formato)){
                ReportePDF<Entrada> exportar = new ReportePDF<>("Reporte Ingreso de equipos");
                
                String[] cabeceras = {"ID", "Factura", "Equipo", "Cantidad", "Fecha de Ingreso"};
                float[] anchos = {1.5f, 3.0f, 4.0f, 2.5f, 3.0f};
                
                exportar.exportar(response, stream, cabeceras, anchos, entrada -> new String[]{
                    String.valueOf(entrada.getId()),
                    entrada.getFactura(),
                    entrada.getEquipo().getDescripcion(),
                    String.valueOf(entrada.getCantidad()),
                    String.valueOf(entrada.getCreateAt())
                });
            }
        }
    }
    
}

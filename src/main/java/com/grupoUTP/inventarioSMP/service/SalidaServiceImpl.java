package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.entity.Salida;
import com.grupoUTP.inventarioSMP.repository.IEquipoDAO;
import com.grupoUTP.inventarioSMP.repository.ISalidasDAO;
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

    @Override
    @Transactional(readOnly = true)
    public void descargarReportes(HttpServletResponse response, String formato) throws IOException {
        try(Stream<Salida> stream = salidaDAO.streamAll()){
            if("excel".equalsIgnoreCase(formato)){
                ReporteExcel<Salida> exportar = new ReporteExcel<>("Salidas");
                String[] caebeceras = {"ID", "Factura", "Equipo", "Cantidad", "Fecha de Salida"};
                
                exportar.exportar(response, stream, caebeceras, salida -> new Object[]{
                    salida.getId(),
                    salida.getFactura(),
                    salida.getEquipo().getDescripcion(),
                    salida.getCantidad(),
                    salida.getCreateAt()
                });
            }else if("pdf".equalsIgnoreCase(formato)){
                ReportePDF<Salida> exportar = new ReportePDF<>("Salidas");
                
                String[] cabeceras = {"ID", "Factura", "Equipo", "Cantidad", "Fecha de Salida"};
                float[] anchos = {1.5f, 3.0f, 4.0f, 2.5f, 3.0f};
                
                exportar.exportar(response, stream, cabeceras, anchos, salida -> new String[]{
                    String.valueOf(salida.getId()),
                    salida.getFactura(),
                    salida.getEquipo().getDescripcion(),
                    String.valueOf(salida.getCantidad()),
                    String.valueOf(salida.getCreateAt())
                });
            }
        }
    }
    
}

package com.grupoUTP.inventarioSMP.service;

import com.grupoUTP.inventarioSMP.dto.ResumenDTO;
import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.entity.Inventario;
import com.grupoUTP.inventarioSMP.repository.IInventarioDAO;
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
public class InventarioServiceImpl implements IInventarioService{

    private final IInventarioDAO inventarioDAO;
    
    @Override
    @Transactional(readOnly = true)
    public List<Inventario> findAll() {
        return inventarioDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Inventario> findAll(Pageable pageable) {
        return inventarioDAO.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Inventario findById(Long id) {
        return inventarioDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Inventario save(Inventario inventario) {        
        return inventarioDAO.save(inventario);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        inventarioDAO.deleteById(id);
    }

    @Override
    @Transactional
    public void registrarIngreso(Categoria categoria) {
                
        Inventario inventarioActual = inventarioDAO.findByCategoria(categoria).orElse(null);
        
        if(inventarioActual == null){
            inventarioActual.setCategoria(categoria);
            inventarioActual.setEntradas(1);
            inventarioActual.setSalidas(0);
            inventarioActual.setStock(1);
            
            inventarioDAO.save(inventarioActual);
        }else{
        
            inventarioActual.setEntradas(inventarioActual.getEntradas() + 1);
            inventarioActual.setStock(inventarioActual.getStock() + 1);

            inventarioDAO.save(inventarioActual);
        }        
    }

    @Override
    @Transactional
    public void registrarSalida(Categoria categoria) {
        
        Inventario inventarioActual = inventarioDAO.findByCategoria(categoria).orElse(null);
        
        if(inventarioActual != null){
            if(inventarioActual.getStock() > 0){
                inventarioActual.setCategoria(inventarioActual.getCategoria());
                inventarioActual.setEntradas(inventarioActual.getEntradas());
                inventarioActual.setSalidas(inventarioActual.getSalidas() + 1);
                inventarioActual.setStock(inventarioActual.getStock() - 1);
                
                inventarioDAO.save(inventarioActual);
            }
        }
    }
    
    @Override
    @Transactional
    public ResumenDTO obtenerResumenGeneral(){
        ResumenDTO resumen = new ResumenDTO();
        
        resumen.setTotalEntradas( inventarioDAO.obtenerTotalEntregas());
        resumen.setTotalSAlidas(inventarioDAO.obtenerTotalSalidas());
        
        List<Inventario> listaInventario = inventarioDAO.findAll();
        resumen.setDetalleCategorias(listaInventario);
        
        return resumen;
    }

    @Override
    @Transactional(readOnly = true)
    public void descargarReportes(HttpServletResponse response, String formato) throws IOException {
        try(Stream<Inventario> stream = inventarioDAO.streamAll()){
            if("excel".equalsIgnoreCase(formato)){
                ReporteExcel<Inventario> exportar = new ReporteExcel<>("Inventario");
                String[] cabeceras = {"ID", "Categoria", "Ingresos", "Salidas", "Stock"};
                
                exportar.exportar(response, stream, cabeceras, inventario -> new Object[]{
                    inventario.getId(),
                    inventario.getCategoria() != null ? inventario.getCategoria().getDescripcion() : "Sin Categoria",
                    inventario.getEntradas(),
                    inventario.getSalidas(),
                    inventario.getStock(),
                });
            }else if("pdf".equalsIgnoreCase(formato)){
                ReportePDF<Inventario> exportar = new ReportePDF<>("Reporte de Inventario");
                
                String[] cabeceras = {"ID", "Categoria", "Ingresos", "Salidas", "Stock"};
                float[] anchos = {1.5f, 4.0f, 3.0f, 3.0f, 3.0f};
                
                exportar.exportar(response, stream, cabeceras, anchos, inventario -> new String[]{
                    String.valueOf(inventario.getId()),
                    String.valueOf(inventario.getCategoria().getDescripcion()),
                    String.valueOf(inventario.getEntradas()),
                    String.valueOf(inventario.getSalidas()),
                    String.valueOf(inventario.getStock())
                });
            }
        }
    }
    
}

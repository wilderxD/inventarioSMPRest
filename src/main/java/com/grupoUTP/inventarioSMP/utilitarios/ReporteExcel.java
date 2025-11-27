package com.grupoUTP.inventarioSMP.utilitarios;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReporteExcel<T>{   
    
    private XSSFWorkbook libro;
    private XSSFSheet hoja;
    private String nombreHoja;
    
    public ReporteExcel(String nombreHoja){
        this.libro = new XSSFWorkbook();        
        this.nombreHoja = nombreHoja;
        this.hoja = libro.createSheet(nombreHoja);
    }
    
    private void createCell(Row fila, int columnCount, Object value, CellStyle style){
        
        hoja.autoSizeColumn(columnCount);
        Cell cell = fila.createCell(columnCount);
        
        if(value == null){
            cell.setCellValue("");
        }else if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if(value instanceof Long){
            cell.setCellValue((Long) value);
        }else if(value instanceof Boolean){
            cell.setCellValue((String)((Boolean) value ? "Sí" : "No"));
        }else if(value instanceof Double){
            cell.setCellValue((Double) value);
        }else {
            cell.setCellValue(value.toString());
        }
        cell.setCellStyle(style);
    }
    
    public void exportar(HttpServletResponse response, Stream<T> dataStream, String[] cabeceras, Function<T, Object[]> mapeadorFila)throws IOException{
        
        CellStyle estiloCabecera = libro.createCellStyle();
        XSSFFont fuenteCabecera = libro.createFont();
        fuenteCabecera.setBold(true);
        fuenteCabecera.setFontHeight(14);
        estiloCabecera.setFont(fuenteCabecera);
        
        CellStyle estiloDatos = libro.createCellStyle();        
        XSSFFont fuenteDatos = libro.createFont();
        fuenteDatos.setFontHeight(12);
        estiloDatos.setFont(fuenteDatos);
        
        Row filaCabecera = hoja.createRow(0);
        for(int i = 0; i < cabeceras.length; i++){
            createCell(filaCabecera, i, cabeceras[i], estiloCabecera);
        }
        
        AtomicInteger rowCount = new AtomicInteger(1);
        
        dataStream.forEach(endidad -> {
            Row fila = hoja.createRow(rowCount.getAndIncrement());
            
            Object[] valoresCeldas = mapeadorFila.apply(endidad);
            
            for(int i = 0; i < valoresCeldas.length; i++){
                createCell(fila, i, valoresCeldas[i], estiloDatos);
            }
        });
        
        ServletOutputStream outputStream = response.getOutputStream();
        libro.write(outputStream);
        libro.close();
        outputStream.close();
    }
    
}

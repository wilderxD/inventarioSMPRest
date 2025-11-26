package com.grupoUTP.inventarioSMP.utilitarios;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Stream;


public class ReportePDF<T> {
    
    private String tituloReporte;
    
    public ReportePDF(String tituloReporte){
        this.tituloReporte = tituloReporte;
    }
    
    public void exportar(HttpServletResponse response, Stream<T> dataStream, String[] cabeceras, float[] anchosColumnas, Function<T, String[]> mapeadorFila) throws IOException{
        
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento, response.getOutputStream());
        
        documento.open();
        
        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fuenteTitulo.setSize(18);
        fuenteTitulo.setColor(Color.BLUE);
        Paragraph pTitulo = new Paragraph(tituloReporte, fuenteTitulo);
        pTitulo.setAlignment(Paragraph.ALIGN_CENTER);
        documento.add(pTitulo);
        
        PdfPTable tabla = new PdfPTable(cabeceras.length);
        tabla.setWidthPercentage(100f);
        tabla.setSpacingBefore(10);
        
        if(anchosColumnas != null && anchosColumnas.length == cabeceras.length){
            tabla.setWidths(anchosColumnas);
        }
        
        escribirCabecera(tabla, cabeceras);
        
        dataStream.forEach(entidad -> {
            String[] datosFila = mapeadorFila.apply(entidad);
            for(String dato : datosFila){
                tabla.addCell(dato != null ? dato : "");
            }
        });
        
        documento.add(tabla);
        documento.close();
    }
    
    private void escribirCabecera(PdfPTable tabla, String[] cabeceras){
        PdfPCell celda = new PdfPCell();
        celda.setBackgroundColor(Color.BLUE);
        celda.setPadding(5);
        
        Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
        fuente.setColor(Color.WHITE);
        
        for(String titulo : cabeceras){
            celda.setPhrase(new Phrase(titulo, fuente));
            tabla.addCell(celda);
        }
    }    
}

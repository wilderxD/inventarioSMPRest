package com.grupoUTP.inventarioSMP.dto;

import com.grupoUTP.inventarioSMP.entity.Inventario;
import java.util.List;
import lombok.Data;

@Data
public class ResumenDTO {
    
    private Integer TotalEntradas;
    private Integer TotalSAlidas;
    private List<Inventario> detalleCategorias;
    
}

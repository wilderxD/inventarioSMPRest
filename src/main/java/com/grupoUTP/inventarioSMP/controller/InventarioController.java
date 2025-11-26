package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.dto.ResumenDTO;
import com.grupoUTP.inventarioSMP.entity.Inventario;
import com.grupoUTP.inventarioSMP.service.IEntradaService;
import com.grupoUTP.inventarioSMP.service.IInventarioService;
import com.grupoUTP.inventarioSMP.service.ISalidaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InventarioController {
    private final IInventarioService inventarioService;
    private final ISalidaService salidaService;
    private final IEntradaService entradaService;
    
    @GetMapping("/inventario")
    public List<Inventario> listarInventario(){
        return inventarioService.findAll();
    }
    
    @GetMapping("/inventario/resumen")
    public ResponseEntity<?> obtenerResumen(){
        ResumenDTO resumen = inventarioService.obtenerResumenGeneral();
        return ResponseEntity.ok(resumen);
    }
    
    
    
}

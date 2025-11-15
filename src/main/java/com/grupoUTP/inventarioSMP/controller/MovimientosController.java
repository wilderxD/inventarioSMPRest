package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.entity.Entrada;
import com.grupoUTP.inventarioSMP.service.IEntradaService;
import com.grupoUTP.inventarioSMP.service.ISalidaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovimientosController {
    
    private final IEntradaService entradaService;
    
    private final ISalidaService salidasService;
    
    @GetMapping("/entradas")
    public List<Entrada> mostrarEntradas(){
        return entradaService.findAll();
    }
    
    @GetMapping("/entradas/page/{page}")
    public Page<Entrada> mostrarEntradas(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return entradaService.findAll(pageable);
    }
    
    @GetMapping("/entradas/{id}")
    public ResponseEntity<?> verEntrada(@PathVariable Long id){
        
        Entrada entrada = null;
        Map<String, Object> response = new HashMap<>();
        
        try{
            entrada = entradaService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.!");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        if(entrada == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Entrada>(entrada, HttpStatus.OK);
    }
    
}

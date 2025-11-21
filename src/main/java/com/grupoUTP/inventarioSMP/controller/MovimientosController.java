package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.entity.Entrada;
import com.grupoUTP.inventarioSMP.entity.Salida;
import com.grupoUTP.inventarioSMP.service.IEntradaService;
import com.grupoUTP.inventarioSMP.service.ISalidaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            response.put("mensaje", "La entrada ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Entrada>(entrada, HttpStatus.OK);
    }
    
    @PostMapping("/entradas")
    public ResponseEntity<?> createEntrada(@RequestBody Entrada entrada, BindingResult result){
        Entrada entradaNew = null;
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }    
            try{
                entradaNew = entradaService.save(entrada);
            }catch(DataAccessException e){
                response.put("mensaje", "Error al realizar el insert en la base de datos.!");
                response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
            }
            
            response.put("mensaje", "El nuevo ingreso se ah registrado con exito.!");
            response.put("entrada", entradaNew);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);        
    }
    
    @PutMapping("/entradas/{id}")
    public ResponseEntity<?> actualizarRntrada(@RequestBody Entrada entrada, BindingResult result, @PathVariable Long id){
        Entrada entradaActual = entradaService.findById(id);
        Entrada entradaUpdate = null;
                
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        if(entradaActual == null){
            response.put("mensaje", "Error: no se pudo editar, la entrada ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        try{
            entradaActual.setFactura(entrada.getFactura());
            entradaActual.setEquipo(entrada.getEquipo());
            
            entradaUpdate = entradaService.save(entradaActual);                    
        }catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar la entrada en la base de datos.!");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "la Entrada ha sido actualizada con exito.!");
        response.put("entrada", entradaUpdate);
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);        
    }
    
    @DeleteMapping("/entradas/{id}")
    public ResponseEntity<?> eliminarEntrada(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        
        try{
            entradaService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar el registro de la base de datos.!");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El registro ha sido eliminado con exito.!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
    @GetMapping("/salidas")
    public List<Salida> mostrarSalidas(){
        return salidasService.findAll();
    }
    
    @GetMapping("/salidas/page/{page}")
    public Page<Salida> mostrarSalidas(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return salidasService.findAll(pageable);
    }
    
    @GetMapping("salidas/{id}")
    public ResponseEntity<?> verSalidas(@PathVariable Long id){
        Salida salida = null;
        Map<String, Object> response = new HashMap<>();
        
        try{
            salida = salidasService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        if(salida == null){
            response.put("mensaje", "El registro de ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Salida>(salida, HttpStatus.OK);
    }
    
    @PostMapping("/salidas")
    public ResponseEntity<?> registrarSalida(@RequestBody Salida salida, BindingResult result){
        Salida salidaNew = null;
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        try{
            salidaNew = salidasService.save(salida);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar el registro en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("mensaje", "El registro ha sido creado con exito.!");
        response.put("salidas", salidaNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/salidas/{id}")
    public ResponseEntity<?> actualizarSalida(@RequestBody Salida salida, BindingResult result, @PathVariable Long id){
        Salida salidaActual = salidasService.findById(id);
        Salida salidaUpdate = null;
        
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        if(salidaActual == null){
            response.put("mensaje", "Error: no se pudo editar, el registro de salida cpn ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        try{
            salidaActual.setEquipo(salida.getEquipo());
            salidaActual.setFactura(salida.getFactura());
            
            salidaUpdate = salidasService.save(salidaActual);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar el registro de salida en la base de datos.!");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El registro de salida se ah actualizado con exito.!");
        response.put("salida", salidaUpdate);
                
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/salidas/{id}")
    public ResponseEntity<?> eliminarSalida(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        
        try{
            salidasService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar el registro de salida de la base de datos.!");        
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El registro ha sido eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
}

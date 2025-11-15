package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.entity.Equipo;
import com.grupoUTP.inventarioSMP.entity.Estado;
import com.grupoUTP.inventarioSMP.entity.Moneda;
import com.grupoUTP.inventarioSMP.service.EquipoServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequiredArgsConstructor
@RequestMapping("/api")
public class EquiposController {
    
    private final EquipoServiceImpl equipoService;
    
    private final Logger log = LoggerFactory.getLogger(EquiposController.class);
    
    @GetMapping("/equipos")
    public List<Equipo> index(){
        return equipoService.findAll();        
    }
    
    @GetMapping("/equipos/page/{page}")
    public Page<Equipo> index(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return equipoService.findAll(pageable);
    }
    
    @GetMapping("/equipos/{id}")
    public ResponseEntity<?> show(@PathVariable Long id){
        Equipo equipo = null;
        Map<String, Object> response = new HashMap<>();
        
        try{
            equipo = equipoService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        if(equipo == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Equipo>(equipo, HttpStatus.OK);
    }
    
    @PostMapping("/equipos")
    public ResponseEntity<?> create(@RequestBody Equipo equipo, BindingResult result){
        Equipo equipoNew = null;
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
            equipoNew = equipoService.save(equipo);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("mensaje", "El equipo ha sido creado con exito.!");
        response.put("equipo", equipoNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/equipos/{id}")
    public ResponseEntity<?> update(@RequestBody Equipo equipo, BindingResult result, @PathVariable Long id){
        Equipo equipoActual = equipoService.findById(id);
        Equipo equipoUpdate = null;
        
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        if(equipoActual == null){
            response.put("mensaje", "Error: no se pudo editar, el equipo ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        try{
            equipoActual.setDescripcion(equipo.getDescripcion());
            equipoActual.setValor(equipo.getValor());
            equipoActual.setMoneda(equipo.getMoneda());
            equipoActual.setDetalle(equipo.getDetalle());
            equipoActual.setCategoria(equipo.getCategoria());
            equipoActual.setEstado(equipo.getEstado());
            equipoActual.setAsignado(equipo.getAsignado());

            equipoUpdate = equipoService.save(equipoActual);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar el cliente en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El equipo ha sido actualizado con exito.!");
        response.put("equipo", equipoUpdate);
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        
        try{
            equipoService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar el equipo de la base de datos.!");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("rmensaje", "El equipo ha sido eliminado con exito.!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
    @GetMapping("/equipos/monedas")
    public List<Moneda> listarMonedas(){
        return equipoService.findAllMoneda();
    }
    
    @GetMapping("/equipos/categorias")
    public List<Categoria> listarCategoria(){
        return equipoService.findAllCategoria();
    }
    
    @GetMapping("/equipos/estados")
    public List<Estado> listarEstados(){
        return equipoService.findAllEstado();
    }
    
    @GetMapping("/equipos/asignados")
    public List<Asignado> listarAsignados(){
        return equipoService.findAllAsignado();
    }
    
}

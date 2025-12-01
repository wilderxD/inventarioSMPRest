package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import com.grupoUTP.inventarioSMP.service.IAsignadoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequiredArgsConstructor
@RequestMapping("/api")
public class AsignadoController {
    
    private final IAsignadoService asignadoService;
    
    @GetMapping("/asignados")
    public List<Asignado> listarAsignados(){
        return asignadoService.findAll();
    }
    
    @GetMapping("/asignados/page/{page}")
    public Page<Asignado> listarAsignados(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return asignadoService.findAll(pageable);
    }
    
    @GetMapping("/asigandos/{id}")
    public ResponseEntity<?> verAsignado(@PathVariable  Long id){
        Asignado asignado = null;
        Map<String, Object> response = new HashMap<>();
        
        try{
            asignado = asignadoService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        if(asignado == null){
            response.put("mensaje", "El asignado ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Asignado>(asignado, HttpStatus.OK);
    }
    
    @PostMapping("/asignados")
    public ResponseEntity<?> crearAsignado(@RequestBody Asignado asginado, BindingResult result){
        Asignado asignadoNew = null;
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
            asignadoNew = asignadoService.save(asginado);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("mensaje", "El usuario a asignadar ha sido creado con exito.");
        response.put("asignado", asignadoNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/asignados/{id}")
    public ResponseEntity<?> actualizarAsignado(@RequestBody Asignado asignado, BindingResult result, @PathVariable Long id){
        Asignado asignadoActual = asignadoService.findById(id);
        Asignado asignadoUpdate = null;
        
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map( err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        if(asignadoActual == null){
            response.put("mensaje", "Error: no se pudo editar");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        try{
            asignadoActual.setCodigo(asignado.getCodigo());
            asignadoActual.setDescripcion(asignado.getDescripcion());
            asignadoActual.setEquipos(asignado.getEquipos());
            asignadoActual.setOficina(asignado.getOficina());
            asignadoActual.setCreateAt(asignado.getCreateAt());
            
            asignadoUpdate = asignadoService.save(asignadoActual);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar el registro.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El registro seleccionado ha sido actualizado con exito.!");
        response.put("asignado", asignadoUpdate);
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/asignado/{id}")
    public ResponseEntity<?> eliminarAsignado(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        
        try{
            asignadoService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar el registro de la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "El registro ha sido eliminado con exito.!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
    
    
}


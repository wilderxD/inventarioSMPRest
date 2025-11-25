package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.entity.Oficina;
import com.grupoUTP.inventarioSMP.service.ICategoriaService;
import com.grupoUTP.inventarioSMP.service.IEstadoService;
import com.grupoUTP.inventarioSMP.service.IOficinaService;
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

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UtilitariosController {
    
    private final ICategoriaService categoriaService;
    private final IEstadoService estadoService;
    private final IOficinaService oficinaService;
    
    @GetMapping("/categorias")
    public List<Categoria> listarcategorias(){
        return categoriaService.findAll();
    }
    
    @GetMapping("/categorias/page/{page}")
    public Page<Categoria> listarCategorias(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return categoriaService.findAll(pageable);
    }
    
    @GetMapping("/categorias/{id}")
    public ResponseEntity<?> verCategoria(@PathVariable Long id){
        Categoria categoria = null;
        Map<String, Object> response = new HashMap<>();
        
        try{
            categoria = categoriaService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        if(categoria == null){
            response.put("mensaje", "La categoria ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Categoria>(categoria, HttpStatus.OK);
    }
    
    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria, BindingResult result){
        Categoria categoriaNew = null;
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
            categoriaNew = categoriaService.save(categoria);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar el insert en la base de datis.!");
            response.put("errors", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("mensaje", "El equipo ha sido creado con exito.!");
        response.put("categoria", categoriaNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/categorias/{id}")
    public ResponseEntity<?> actualizarCategoria(@RequestBody Categoria categoria, BindingResult result, @PathVariable Long id){
        Categoria categoriaActual = categoriaService.findById(id);
        Categoria categoriaUpdate = null;
        
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        if(categoriaActual == null){
            response.put("mensaje", "Error: no se puso editar, la categoria ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        try{
            categoriaActual.setDescripcion(categoria.getDescripcion());
            
            categoriaUpdate = categoriaService.save(categoriaActual);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar la categoria en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "La categoria ha sido actualizada con exito.!");
        response.put("categoria", categoriaUpdate);
        
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        
        try{
            categoriaService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "error al eliminar la categoria de la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "La categoria ha sido eliminado con exito.!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);        
    }
    
    @GetMapping("/oficinas")
    public List<Oficina> listarOficinas(){
        return oficinaService.findAll();
    }
    
    @GetMapping("/oficinas/page/{page}")
    public Page<Oficina> listarOficinas(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return oficinaService.findAll(pageable);
    }
    
    @GetMapping("/oficinas/{id}")
    public ResponseEntity<?> verOficina(@PathVariable Long id){
        Oficina oficina = null;
        Map<String, Object> response = new HashMap<>();
        
        try{
            oficina = oficinaService.findById(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        if(oficina == null){
            response.put("mensaje", "La oficina ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<Oficina>(oficina, HttpStatus.OK);
    }
    
    @PostMapping("/oficinas")
    public ResponseEntity<?> crearOficina(@RequestBody Oficina oficina, BindingResult result){
        Oficina oficinaNew = null;
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
            oficinaNew = oficinaService.save(oficina);
        }catch(DataAccessException e){
            response.put("mensaje", "error al realizar el insert en la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        response.put("mensaje", "La oficina ha sido creada con exito");
        response.put("oficina", oficinaNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/oficinas/{id}")
    public ResponseEntity<?> actualizarOficina(@RequestBody Oficina oficina, BindingResult result, @PathVariable Long id){
        Oficina oficinaActual = oficinaService.findById(id);
        Oficina oficinaUpdate = null;
        
        Map<String, Object> response = new HashMap<>();
        
        if(result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());
            
            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        
        if(oficinaActual == null){
            response.put("mensaje", "Error no se pudo editar, la oficina ID: ".concat(id.toString().concat(" no existe en la base de datos.!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        
        try{
            oficinaActual.setDescripcion(oficina.getDescripcion());
            
            oficinaUpdate = oficinaService.save(oficinaActual);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al actualizar la oficina en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "La oficina ha sido creada con exito.!");
        response.put("oficina", oficinaUpdate);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/oficinas/{id}")
    public ResponseEntity<?> eliminarOficina(@PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        
        try{
            oficinaService.delete(id);
        }catch(DataAccessException e){
            response.put("mensaje", "Error al eliminar la oficina de la base de datos.!");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        response.put("mensaje", "La oficina ha sido eliminada con exito.!");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
     
}

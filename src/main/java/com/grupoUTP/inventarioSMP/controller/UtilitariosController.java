package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.entity.Categoria;
import com.grupoUTP.inventarioSMP.service.ICategoriaService;
import com.grupoUTP.inventarioSMP.service.IEstadoService;
import com.grupoUTP.inventarioSMP.service.IOficinaService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
}

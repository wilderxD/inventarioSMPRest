package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.entity.Asignado;
import com.grupoUTP.inventarioSMP.service.IAsignadoService;
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
    
    
    
}


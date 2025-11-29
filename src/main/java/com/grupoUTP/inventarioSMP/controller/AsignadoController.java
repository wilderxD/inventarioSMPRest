package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.service.IAsignadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AsignadoController {
    
    private final IAsignadoService asignadoService;
}

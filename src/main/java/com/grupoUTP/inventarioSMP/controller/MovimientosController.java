package com.grupoUTP.inventarioSMP.controller;

import com.grupoUTP.inventarioSMP.service.IEntradaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200", "*"})
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MovimientosController {
    
    private final IEntradaService entradaService;
    
}

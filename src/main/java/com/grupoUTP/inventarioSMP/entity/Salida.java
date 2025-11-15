package com.grupoUTP.inventarioSMP.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salidas")
public class Salida implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String factura;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id")  
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Equipo equipo;
    
    @Column(name = "create_at")
    private Date createAt;
    
    private Integer cantidad;
    
    private static final long serialVersionUID = 1L;
    
}

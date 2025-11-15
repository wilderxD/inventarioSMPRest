package com.grupoUTP.inventarioSMP.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "asignados")
public class Asignado implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String codigo;
    
    private String descripcion;
    
    @JsonIgnoreProperties(value = {"asignado", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "asignado", cascade = CascadeType.ALL)
    private List<Equipo> equipos;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oficina_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Oficina oficina;
    
    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;
    
    private static final long serialVersionUID = 1L;
}

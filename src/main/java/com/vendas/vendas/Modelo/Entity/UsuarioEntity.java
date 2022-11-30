package com.vendas.vendas.Modelo.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "USUARIO", schema = "VENDAS")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private StatusCadastroEnum status;

    @Column(nullable = true)
    private LocalDateTime dataCadastro;
    
}

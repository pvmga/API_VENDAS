package com.vendas.vendas.Modelo.Entity;

import com.vendas.vendas.Modelo.Enums.ClienteTipoPesssoaEnums;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "CLIENTE", schema = "VENDAS")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    //private Long id;
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 18)
    private String cpfCnpj;

    @Column(nullable = true, length = 30)
    @Enumerated(value = EnumType.STRING)
    private ClienteTipoPesssoaEnums tipoPessoa;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = true)
    private LocalDateTime dataCadastro;

    @Column(nullable = true, length = 30)
    @Enumerated(value = EnumType.STRING)
    private StatusCadastroEnum status;

}

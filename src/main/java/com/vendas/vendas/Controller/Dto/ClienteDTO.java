package com.vendas.vendas.Controller.Dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.vendas.vendas.Modelo.Enums.ClienteTipoPesssoaEnums;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private UUID id;
    private String nome;
    private String cpfCnpj;
    private ClienteTipoPesssoaEnums tipoPessoa;
    //private String tipoPessoa;
    private String telefone;
    private String email;
    private LocalDateTime dataCadastro;
    private StatusCadastroEnum status;
    //private String status;

}

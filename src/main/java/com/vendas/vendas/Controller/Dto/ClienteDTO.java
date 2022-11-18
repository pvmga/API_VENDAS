package com.vendas.vendas.Controller.Dto;

import java.time.LocalDateTime;

import com.vendas.vendas.Modelo.Enums.ClienteTipoPesssoaEnums;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteDTO {
    private String nome;
    private String cpfCnpj;
    private ClienteTipoPesssoaEnums tipoPessoa;
    private String telefone;
    private String email;
    private LocalDateTime dataCadastro;
    private StatusCadastroEnum status;

}

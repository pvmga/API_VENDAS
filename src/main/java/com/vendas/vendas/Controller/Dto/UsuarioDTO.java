package com.vendas.vendas.Controller.Dto;

import java.time.LocalDateTime;

import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    
    private String nome;
    private String email;
    private String senha;
    private StatusCadastroEnum status;
    private LocalDateTime dataCadastro;
}

package com.vendas.vendas.Service;

import com.vendas.vendas.Modelo.Entity.UsuarioEntity;

public interface UsuarioService {
    UsuarioEntity autenticar(String email, String senha);

    UsuarioEntity salvarUsuario(UsuarioEntity usuario);
    void validarEmail(String email);
}

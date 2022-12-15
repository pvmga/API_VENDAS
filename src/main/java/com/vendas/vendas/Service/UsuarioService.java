package com.vendas.vendas.Service;

import java.util.Optional;
import java.util.UUID;

import com.vendas.vendas.Modelo.Entity.UsuarioEntity;

public interface UsuarioService {
    UsuarioEntity autenticar(String email, String senha);

    UsuarioEntity salvarUsuario(UsuarioEntity usuario);
    void validarEmail(String email);

    Optional<UsuarioEntity> obterPorId(UUID id);
}

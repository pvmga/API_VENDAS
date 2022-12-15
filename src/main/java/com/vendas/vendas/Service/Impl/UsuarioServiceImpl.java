package com.vendas.vendas.Service.Impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.UsuarioEntity;
import com.vendas.vendas.Modelo.Repository.UsuarioRepository;
import com.vendas.vendas.Service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super();
        this.repository = repository;
    }

    @Override
    public UsuarioEntity autenticar(String email, String senha) {
        Optional<UsuarioEntity> usuario = repository.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new RegraNegocioException("Usuário não encontrado para o email informado.");
        }
        if (!usuario.get().getSenha().equals(senha)) {
            throw new RegraNegocioException("Senha inválida.");
        }

        return usuario.get();
    }

    @Override
    @Transactional
    public UsuarioEntity salvarUsuario(UsuarioEntity usuario) {
        validarEmail(usuario.getEmail());
        return repository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if (existe) {
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }
    }

    @Override
    public Optional<UsuarioEntity> obterPorId(UUID id) {
        return repository.findById(id);
    }
    
}

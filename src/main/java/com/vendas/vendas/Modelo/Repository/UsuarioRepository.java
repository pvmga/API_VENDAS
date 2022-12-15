package com.vendas.vendas.Modelo.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vendas.vendas.Modelo.Entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

    // Retorna se existe ou não.
    boolean existsByEmail(String email);

    // Retorna cadastro caso exista / Query Methods
    Optional<UsuarioEntity> findByEmail(String email);

    // Pesquisando por Email e Nome.
    //Optional<Usuario> findByEmailAndNome(String email, String nome);
    
}

package com.vendas.vendas.Modelo.Repository;

import java.util.Optional;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vendas.vendas.Modelo.Entity.UsuarioEntity;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;

//@ExtendWith( SpringExtension.class ) // Extend já está instanciado no @DataJpaTest
@ActiveProfiles("test") // irá procurar nosso application de test
@DataJpaTest // Será aberto transação e no fim dará rollback. Não precisaremos realizar deleteAll()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Test Integração
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;
    
    @Test
    public void deveVerificarAExistenciaDeUmEmail() {
        // cenário
        UsuarioEntity usuario = criarUsuario();
        //repository.save(usuario);
        entityManager.persist(usuario); // ao usar entityManager a classe passada por parametro não pode ter id se não lança um erro

        // ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        // verificação
        Assertions.assertThat(result).isTrue();

    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        // ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");

        // verificação
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUmUsuarioNaBaseDeDados() {
        UsuarioEntity usuarioEntity = criarUsuario();

        UsuarioEntity usuarioSalvo = repository.save(usuarioEntity);

        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmUsuarioPorEmail() {
        UsuarioEntity usuarioEntity = criarUsuario();
        entityManager.persist(usuarioEntity);

        Optional<UsuarioEntity> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat( result.isPresent()).isTrue();
    }
    
    @Test
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {
        Optional<UsuarioEntity> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat( result.isPresent()).isFalse();
    }

    public static UsuarioEntity criarUsuario() {
        return UsuarioEntity
                .builder()
                .nome("usuario")
                .email("usuario@email.com")
                .senha("senha")
                .status(StatusCadastroEnum.ATIVO).build();
    }
}

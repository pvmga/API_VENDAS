package com.vendas.vendas.Modelo.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vendas.vendas.Modelo.Entity.ClienteEntity;
import com.vendas.vendas.Modelo.Enums.ClienteTipoPesssoaEnums;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;

@ExtendWith( SpringExtension.class )
@ActiveProfiles("test") // irá procurar nosso application de test
@DataJpaTest // Será aberto transação e no fim dará rollback. Não precisaremos realizar deleteAll()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Test Integração
public class ClienteRepositoryTest {

    @Autowired
    ClienteRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveRetornarTrueQuandoExistirClienteCadastrado() {
        // cenário
        ClienteEntity cliente = criarCliente();
        //repository.save(usuario);
        entityManager.persist(cliente);

        // ação / execução
        boolean result = repository.existsByCpfCnpj("0");

        // verificação
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverUmClienteCadastrado() {
        boolean result = repository.existsByCpfCnpj("0");

        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void deveBuscarUmClienteCpfCnpj() {
        // cenário
        ClienteEntity cliente = criarCliente();
        entityManager.persist(cliente);

        // verificacao
        Optional<ClienteEntity> result = repository.findByCpfCnpj("0");

        Assertions.assertThat( result.isPresent() ).isTrue();
    }

    @Test
    public void deveRetornarVazioQuandoNaoExistirClienteCpfCnpj() {

        // verificacao
        Optional<ClienteEntity> result = repository.findByCpfCnpj("0");

        Assertions.assertThat( result.isPresent() ).isFalse();
    }

    public static ClienteEntity criarCliente() {
        return ClienteEntity
                .builder()
                .nome("nome")
                .cpfCnpj("0")
                .tipoPessoa(ClienteTipoPesssoaEnums.PESSOA_FISICA)
                .telefone("0")
                .email("email@email.com")
                .dataCadastro(LocalDateTime.now(ZoneId.of("UTC")))
                .status(StatusCadastroEnum.ATIVO).build();
    }
    
}

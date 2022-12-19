package com.vendas.vendas.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.UsuarioEntity;
import com.vendas.vendas.Modelo.Repository.UsuarioRepository;
import com.vendas.vendas.Modelo.Repository.UsuarioRepositoryTest;
import com.vendas.vendas.Service.Impl.UsuarioServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
// Test Unitário
public class UsuarioServiceTest {
    
    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    UsuarioRepository repository;

    @Test
    public void deveSalvarUmUsuario() {
        // não faça nada quando chamar service.validarEmail.
        Mockito.doNothing().when(service).validarEmail(Mockito.anyString());

        UsuarioEntity usuario = UsuarioRepositoryTest.criarUsuario();
        // Quando chamar o método save passando qualquer usuário que seja, ele irá retornar o usuario.
        Mockito.when(repository.save(Mockito.any(UsuarioEntity.class))).thenReturn(usuario);

        // Realizei o Mock a cima, onde pedi para quando invocar o save, retornar o usuário criado na linha 33.
        UsuarioEntity usuarioSalvo = service.salvarUsuario(new UsuarioEntity());

        // Verificando
        Assertions.assertThat(usuarioSalvo).isNotNull();
        Assertions.assertThat(usuarioSalvo.getNome()).isEqualTo("usuario");
        Assertions.assertThat(usuarioSalvo.getEmail()).isEqualTo("usuario@email.com");
        Assertions.assertThat(usuarioSalvo.getSenha()).isEqualTo("senha");
    }

    @Test
    public void naoDeveSalvarUmUsuarioComEmailJaCadastrado() {
        String email = "email@email.com";
        UsuarioEntity usuario = UsuarioEntity.builder().email(email).build();

        // Jogue uma exceção ao chamar o validaremail
        Mockito.doThrow( RegraNegocioException.class ).when(service).validarEmail(email);

        //acao
        org.junit.jupiter.api.Assertions.assertThrows( RegraNegocioException.class, () -> service.salvarUsuario(usuario));

        // verificação
        Mockito.verify( repository, Mockito.never() ).save(usuario);
    }

}

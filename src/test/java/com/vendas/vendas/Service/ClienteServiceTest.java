package com.vendas.vendas.Service;

import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.ClienteEntity;
import com.vendas.vendas.Modelo.Enums.ClienteTipoPesssoaEnums;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.vendas.vendas.Modelo.Repository.ClienteRepository;
import com.vendas.vendas.Service.Impl.ClienteServiceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("teste")
// Teste Unitário
public class ClienteServiceTest {
    

     //@Autowired
    //UsuarioService service; // usamos até entrar o @SpyBean
    @SpyBean
    ClienteServiceImpl service;

    //@Autowired
    @MockBean
    ClienteRepository repository;

    /*@BeforeEach
    public void setUp() {
        //UsuarioRepository usuarioRepositoryMock = Mockito.mock(UsuarioRepository.class);
        //repository = Mockito.mock(UsuarioRepository.class); //Adicionando a anotation @MockBean não preciso utilizar esse trecho do código.

        //service = new UsuarioServiceImpl(repository); //usamos até implementar o @SpyBean

        //service = Mockito.spy(UsuarioServiceImpl.class); // Implementação padrão do @SpyBean, mas existe anotation para isso.
    }*/

    @Test
    public void deveRetornarErroQuandoNaoExistirDadosParaCpfCnpjInformado() {
        // cenário
        Mockito.when(repository.findByCpfCnpj(Mockito.anyString())).thenReturn(Optional.empty());

        // ação
        Throwable exception = Assertions.catchThrowable( () -> service.retornaDadosCliente("0"));

        Assertions.assertThat(exception)
            .isInstanceOf(RegraNegocioException.class)
            .hasMessage("Cliente não encontrado com o cpf/cnpj informado");
    }

    @Test
    public void deveRetornarDadosQuandoExistirCpfCnpjInformado() {
        String cpfCnpj = "0";
        
        ClienteEntity cliente = criarCliente();
        Mockito.when( repository.findByCpfCnpj(cpfCnpj) ).thenReturn(Optional.of(cliente));

        // acao
        ClienteEntity result = service.retornaDadosCliente(cpfCnpj);

        // Verificacação
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void deveSalvarUmCliente() {
        ClienteEntity cliente = criarCliente();

        Mockito.when(repository.save(Mockito.any(ClienteEntity.class))).thenReturn(cliente);

        ClienteEntity clienteSalvo = service.salvarCliente(cliente);

        // verificação
        Assertions.assertThat(clienteSalvo).isNotNull();
    }

    @Test
    public void naoDeveSalvarUmClienteComCpfCnpjJaCadastrado() {
        // cenário
        Mockito.when(repository.existsByCpfCnpj(Mockito.anyString())).thenReturn(true);

        // acao
        Throwable exception = Assertions.catchThrowable( () -> service.salvarCliente(criarCliente()) );

        // verificação
        Assertions.assertThat(exception)
                .isInstanceOf(RegraNegocioException.class)
                .hasMessage("Já existe um cliente cadastrado com este cpf/cnpj");
    }

    @Test 
    public void validarJaExisteCpfCnpj() {
        Mockito.when(repository.existsByCpfCnpj(Mockito.anyString())).thenReturn(false);

        service.validarJaExisteCpfCnpj("0");
    }

    public static ClienteEntity criarCliente() {
        return ClienteEntity.builder()
                .nome("nome")
                .cpfCnpj("0")
                .tipoPessoa(ClienteTipoPesssoaEnums.PESSOA_FISICA)
                .telefone("0")
                .email("email@email.com")
                .dataCadastro(LocalDateTime.now(ZoneId.of("UTC")))
                .status(StatusCadastroEnum.ATIVO).build();
    }

}

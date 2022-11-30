package com.vendas.vendas.Service.Impl;

import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.ClienteEntity;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;
import com.vendas.vendas.Modelo.Repository.ClienteRepository;
import com.vendas.vendas.Service.ClienteService;

import org.springframework.data.domain.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
public class ClienteServiceImpl implements ClienteService {


    @Autowired
    private ClienteRepository repository;

    @Override
    @Transactional
    public ClienteEntity salvarCliente(ClienteEntity cliente) {
        cliente.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        cliente.setStatus(StatusCadastroEnum.ATIVO); // PERSET DE QUE MEU CLIENTE SERÁ ATIVO AO CADASTRAR
        validarJaExisteCpfCnpj(cliente.getCpfCnpj());
        validarDadosCliente(cliente);
        return repository.save(cliente);
    }

    @Override
    @Transactional
    public ClienteEntity atualizarCliente(ClienteEntity cliente) {
        Objects.requireNonNull(cliente.getId()); // Meu id não poderá ser Null
        validarDadosCliente(cliente);
        return repository.save(cliente);
    }

    @Override
    @Transactional
    public void deletarCliente(ClienteEntity cliente) {
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }

    @Override
    public ClienteEntity retornaDadosCliente(String cpfCnpj) {
        Optional<ClienteEntity> cliente = repository.findByCpfCnpj(cpfCnpj);

        if (!cliente.isPresent()) {
            throw new RegraNegocioException("Cliente não encontrado com o cpf/cnpj informado");
        }

        return cliente.get();
    }

    @Override
    public void validarJaExisteCpfCnpj(String cpfCnpj) {
        boolean existe = repository.existsByCpfCnpj(cpfCnpj);
        //Optional<ClienteEntity> cliente = repository.existsByCpfCnpj(cpfCnpj);
        //if (cliente.isPresent()) {
        if (existe) {
            throw new RegraNegocioException("Já existe um cliente cadastrado com este cpf/cnpj");
        }
    }
    
    @Override
    public Optional<ClienteEntity> obterPorId(UUID id) {
        return repository.findById(id);
    }
    
    private void validarDadosCliente(ClienteEntity cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().equals("")) {
            throw new RegraNegocioException("Informe um Nome válido.");
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().toString().length() <= 9) {
            throw new RegraNegocioException("Informe um Número de Telefone válido.");
        }

        if (cliente.getEmail() == null || cliente.getEmail().toString().length() <= 15) {
            throw new RegraNegocioException("Informe um E-mail válido.");
        }
    }

    @Override
    public List<ClienteEntity> buscar(ClienteEntity clienteFiltro) {
        // Segundo parametro é opcional
        // withIgnoreCase -> Tanto faz se o usuário passou em caixa alta ou baixa.
        // withStringMatcher -> Forma que irá buscar as informações no banco de dados "Encontrar todos os lançamentos que contenha a letra A no meio por exemplo"
        Example<ClienteEntity> example = Example.of(clienteFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));


        return repository.findAll(example);
    }

}

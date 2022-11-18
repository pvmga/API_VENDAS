package com.vendas.vendas.Service.Impl;

import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.ClienteEntity;
import com.vendas.vendas.Modelo.Repository.ClienteRepository;
import com.vendas.vendas.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class ClienteServiceImpl implements ClienteService {


    @Autowired
    private ClienteRepository repository;

    @Override
    @Transactional
    public ClienteEntity salvarCliente(ClienteEntity cliente) {
        validarJaExisteCpfCnpj(cliente.getCpfCnpj());
        return repository.save(cliente);
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
}

package com.vendas.vendas.Service;

import java.util.Optional;
import java.util.UUID;

import com.vendas.vendas.Modelo.Entity.ClienteEntity;

public interface ClienteService {

    ClienteEntity salvarCliente(ClienteEntity cliente);
    ClienteEntity atualizarCliente(ClienteEntity cliente);
    void deletarCliente(ClienteEntity cliente);
    
    ClienteEntity retornaDadosCliente(String string);
    
    Optional<ClienteEntity> obterPorId(UUID id);
    
    void validarJaExisteCpfCnpj(String cpfCnpj);
}

package com.vendas.vendas.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.vendas.vendas.Modelo.Entity.ClienteEntity;

public interface ClienteService {

    ClienteEntity salvarCliente(ClienteEntity cliente);
    ClienteEntity atualizarCliente(ClienteEntity cliente);
    void deletarCliente(ClienteEntity cliente);
    
    ClienteEntity retornaDadosCliente(String string);
    
    Optional<ClienteEntity> obterPorId(UUID id);

    List<ClienteEntity> buscar(ClienteEntity clienteFiltro);
    
    void validarJaExisteCpfCnpj(String cpfCnpj);
}

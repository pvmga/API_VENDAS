package com.vendas.vendas.Service;

import com.vendas.vendas.Modelo.Entity.ClienteEntity;

public interface ClienteService {

    ClienteEntity retornaDadosCliente(String cpfCnpj);

    ClienteEntity salvarCliente(ClienteEntity cliente);

    void validarJaExisteCpfCnpj(String cpfCnpj);
}

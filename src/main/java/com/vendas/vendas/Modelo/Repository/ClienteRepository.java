package com.vendas.vendas.Modelo.Repository;

import com.vendas.vendas.Modelo.Entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, UUID> {
    boolean existsByCpfCnpj(String cpfCnpj);

    Optional<ClienteEntity> findByCpfCnpj(String cpfCnpj);

    //Optional<ClienteEntity> existsByCpfCnpj(String cpfCnpj);
}

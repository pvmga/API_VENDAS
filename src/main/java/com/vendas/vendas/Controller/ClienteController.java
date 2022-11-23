package com.vendas.vendas.Controller;

import com.vendas.vendas.Controller.Dto.AtualizaStatusDTO;
import com.vendas.vendas.Controller.Dto.ClienteDTO;
import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.ClienteEntity;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;
import com.vendas.vendas.Service.ClienteService;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping("/{cpfcnpj}/outro-dados-cliente")
    public ResponseEntity outroModoRetornaDadosCliente(@PathVariable(value = "cpfcnpj") String cpfcnpj) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.retornaDadosCliente(cpfcnpj));
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/dados-cliente")
    public ResponseEntity retornaDadosCliente(@RequestBody ClienteDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.retornaDadosCliente(dto.getCpfCnpj()));
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody ClienteDTO dto) {

        var cliente = new ClienteEntity();
        BeanUtils.copyProperties(dto, cliente);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarCliente(cliente));
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") UUID id, @RequestBody ClienteDTO dto) {
        return service.obterPorId(id).map( entity -> {

            try {
                var cliente = new ClienteEntity();
                BeanUtils.copyProperties(dto, cliente);
                cliente.setId(entity.getId()); // setando o id retornado no meu entity
                return ResponseEntity.status(HttpStatus.CREATED).body(service.atualizarCliente(cliente));
            }catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () -> new ResponseEntity("Cliente não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
        
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") UUID id) {
        return service.obterPorId(id).map( entity -> {
            service.deletarCliente(entity);
            return new ResponseEntity("Cliente removido com sucesso.", HttpStatus.OK);
            //return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet( () -> new ResponseEntity("Cliente não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}/atualizar-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") UUID id, @RequestBody AtualizaStatusDTO dto) {
        return service.obterPorId(id).map( entity -> {
            StatusCadastroEnum statusSelecionado = StatusCadastroEnum.valueOf(dto.getStatus());
            if (statusSelecionado == null) {
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido.");
            }
            try {
                entity.setStatus(statusSelecionado);
                service.atualizarCliente(entity);
                return ResponseEntity.ok(entity);
            }catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () -> new ResponseEntity("Cliente não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
    }
    
}

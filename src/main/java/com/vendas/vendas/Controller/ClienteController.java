package com.vendas.vendas.Controller;

import com.vendas.vendas.Controller.Dto.AtualizaStatusDTO;
import com.vendas.vendas.Controller.Dto.ClienteDTO;
import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.ClienteEntity;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;
import com.vendas.vendas.Service.ClienteService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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

    @GetMapping
    public ResponseEntity<?> buscar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "email", required = false) String email
            //@RequestParam(value = "id") UUID id
        ) {
            // Validação para verificar se realmente existe usuário, pois é obrigatório preencher.
            /*Optional<ClienteEntity> cliente = service.obterPorId(id);
            if (!cliente.isPresent()) {
                return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Id cliente não encontrado.");
            }*/

            ClienteEntity clienteFiltro = new ClienteEntity();
            clienteFiltro.setNome(nome);
            clienteFiltro.setEmail(email);

            List<ClienteEntity> clientes = service.buscar(clienteFiltro);

            return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{cpfcnpj}/outro-dados-cliente")
    public ResponseEntity<?> outroModoRetornaDadosCliente(@PathVariable(value = "cpfcnpj") String cpfcnpj) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.retornaDadosCliente(cpfcnpj));
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/dados-cliente")
    public ResponseEntity<?> retornaDadosCliente(@RequestBody ClienteDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.retornaDadosCliente(dto.getCpfCnpj()));
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody ClienteDTO dto) {

        var cliente = new ClienteEntity();
        BeanUtils.copyProperties(dto, cliente);

        cliente.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        cliente.setStatus(StatusCadastroEnum.ATIVO); // PERSET DE QUE MEU CLIENTE SERÁ ATIVO AO CADASTRAR

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarCliente(cliente));
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizar(@PathVariable("id") UUID id, @RequestBody ClienteDTO dto) {
        return service.obterPorId(id).map( entity -> {

            try {
                var cliente = new ClienteEntity();
                BeanUtils.copyProperties(dto, cliente);
                cliente.setId(entity.getId()); // setando o id retornado no meu entity
                return ResponseEntity.status(HttpStatus.CREATED).body(service.atualizarCliente(cliente));
            }catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet( () -> new ResponseEntity<>("Cliente não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
        
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") UUID id) {
        return service.obterPorId(id).map( entity -> {
            service.deletarCliente(entity);
            return new ResponseEntity<>("Cliente removido com sucesso.", HttpStatus.OK);
            //return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet( () -> new ResponseEntity<>("Cliente não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping("{id}/atualizar-status")
    public ResponseEntity<?> atualizarStatus(@PathVariable("id") UUID id, @RequestBody AtualizaStatusDTO dto) {
        return service.obterPorId(id).map( entity -> {
            if (dto.getStatus() == "") {
                //StatusCadastroEnum.values()
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status do Cliente, envie um status válido.");
            } else {
                try {
                    StatusCadastroEnum statusSelecionado = StatusCadastroEnum.valueOf(dto.getStatus());
                    entity.setStatus(statusSelecionado);
                    service.atualizarCliente(entity);
                    return ResponseEntity.ok(entity);
                }catch (RegraNegocioException e) {
                    return ResponseEntity.badRequest().body(e.getMessage());
                }
            }
        }).orElseGet( () -> new ResponseEntity<>("Cliente não encontrado para o Id informado.", HttpStatus.BAD_REQUEST));
    }
}

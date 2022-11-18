package com.vendas.vendas.Controller;

import com.vendas.vendas.Controller.Dto.ClienteDTO;
import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.ClienteEntity;
import com.vendas.vendas.Service.ClienteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;


@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteService service;

    public ClienteController(ClienteService service) {
        super();
        this.service = service;
    }

    @PostMapping("/dadosCliente")
    public ResponseEntity retornaDadosCliente(@RequestBody ClienteDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.retornaDadosCliente(dto.getCpfCnpj()));
        }catch (RegraNegocioException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody ClienteDTO dto) {
        /*ClienteEntity cliente = ClienteEntity.builder()
                .nome(dto.getNome())
                .cpfCnpj(dto.getCpfCnpj())
                .tipoPessoa(dto.getTipoPessoa())
                .telefone(dto.getTelefone())
                .email(dto.getEmail())
                .dataCadastro(LocalDateTime.now(ZoneId.of("UTC")))
                .status(dto.getStatus()).build();*/

        var cliente = new ClienteEntity();
        BeanUtils.copyProperties(dto, cliente);
        cliente.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));

        try {
            /*ClienteEntity clienteSalvo = service.salvarCliente(cliente);
            return new ResponseEntity(clienteSalvo, HttpStatus.CREATED);*/
            return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarCliente(cliente));
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

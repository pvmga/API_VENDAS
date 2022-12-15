package com.vendas.vendas.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vendas.vendas.Controller.Dto.UsuarioDTO;
import com.vendas.vendas.Exception.RegraNegocioException;
import com.vendas.vendas.Modelo.Entity.UsuarioEntity;
import com.vendas.vendas.Modelo.Enums.StatusCadastroEnum;
import com.vendas.vendas.Service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar ( @RequestBody UsuarioDTO dto ) {
        try {
            UsuarioEntity usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<?> salvar ( @RequestBody UsuarioDTO dto ) {
        UsuarioEntity usuario = new UsuarioEntity();
        BeanUtils.copyProperties(dto, usuario);

        usuario.setDataCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        usuario.setStatus(StatusCadastroEnum.ATIVO);

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.salvarUsuario(usuario));
        }catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

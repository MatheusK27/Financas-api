package com.matheus.financas.api.Controller;

import com.matheus.financas.api.dominio.usuario.DadosCadastroUsuario;
import com.matheus.financas.api.dominio.usuario.Usuario;
import com.matheus.financas.api.dominio.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder encoder;


    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroUsuario dados ){
      var usuario= new Usuario(dados);
      usuario.setSenha(encoder.encode(dados.senha()));
        repository.save(usuario);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping
    @Transactional
    public ResponseEntity deletar(@AuthenticationPrincipal Usuario usuario){
        repository.delete(usuario);
        return ResponseEntity.noContent().build();

    }

}

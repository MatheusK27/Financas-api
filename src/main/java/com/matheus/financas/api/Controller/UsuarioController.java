package com.matheus.financas.api.Controller;

import com.matheus.financas.api.dominio.DadosCadastroUsuario;
import com.matheus.financas.api.dominio.Usuario;
import com.matheus.financas.api.dominio.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

}

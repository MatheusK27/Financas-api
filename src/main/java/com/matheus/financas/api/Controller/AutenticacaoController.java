package com.matheus.financas.api.Controller;


import com.matheus.financas.api.dominio.login.DadosAutentificacaoLogin;
import com.matheus.financas.api.dominio.usuario.UsuarioRepository;
import com.matheus.financas.api.Security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity fazerLogin(@Valid @RequestBody DadosAutentificacaoLogin dados ) {
        var usuario = repository.findByEmail(dados.email());
        if (usuario == null) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }
        if (!encoder.matches(dados.senha(), usuario.getSenha())) {
            return ResponseEntity.status(401).body("Senha inválida");

        }

        var token = tokenService.gerarToken(usuario);

        return ResponseEntity.ok(token);
    }

}

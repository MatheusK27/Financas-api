package com.matheus.financas.api.Security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.matheus.financas.api.dominio.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private String secret= "123456";


    public String gerarToken(Usuario usuario){
        return  JWT.create()
                .withIssuer("financas-api")
                .withSubject(usuario.getEmail())
                .withExpiresAt(dataExpiracao())
                .sign(Algorithm.HMAC256(secret));

    }

    public String getSubject(String tokenJWT){
        return  JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("financas- api")
                .build()
                .verify(tokenJWT)
                .getSubject();
    }

    private Instant dataExpiracao(){
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }




}

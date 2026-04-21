package com.matheus.financas.api.Security;


import com.matheus.financas.api.dominio.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

   @Autowired
    private TokenService tokenService;
   @Autowired
    private UsuarioRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT= recuperarToken(request);
        if (tokenJWT!=null){
            try {


            var subject= tokenService.getSubject(tokenJWT);
            var usuario= repository.findByEmail(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario,null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (Exception e){
                System.out.println("Erro ao validar token: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);

    }

    private String recuperarToken(HttpServletRequest request){
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return  authorizationHeader.replace("Bearer ", "").trim();
        }
        return null;
    }


}

package com.matheus.financas.api.dominio.transacao.repository;

import com.matheus.financas.api.dominio.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository  extends JpaRepository<Usuario,Long> {
   Usuario findByEmail(String email);
}

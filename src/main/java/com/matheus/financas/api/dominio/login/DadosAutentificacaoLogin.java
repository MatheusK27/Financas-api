package com.matheus.financas.api.dominio.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutentificacaoLogin(@NotBlank @Email String email, @NotBlank String senha) {
}

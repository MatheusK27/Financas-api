package com.matheus.financas.api.dominio;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DadosAutentificacaoLogin(@NotBlank @Email String email, @NotBlank String senha) {
}

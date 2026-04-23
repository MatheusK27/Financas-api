package com.matheus.financas.api.dominio.transacao;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosCadastroTransacao(@NotBlank String descricao,
                                     @NotNull @DecimalMin("0.01")BigDecimal valor ,
                                     @NotNull LocalDate data,
                                     @NotNull TipoTransacao tipo,
                                     @NotNull CategoriaTransacao categoria
                                     ) {
}

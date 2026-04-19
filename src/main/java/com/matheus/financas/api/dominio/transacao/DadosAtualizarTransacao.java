package com.matheus.financas.api.dominio.transacao;

import com.matheus.financas.api.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record DadosAtualizarTransacao(String descricao,
                                      BigDecimal valor,
                                      LocalDate data,
                                      TipoTransacao tipo) {
}

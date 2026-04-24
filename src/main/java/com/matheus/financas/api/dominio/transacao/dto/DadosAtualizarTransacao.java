package com.matheus.financas.api.dominio.transacao.dto;

import com.matheus.financas.api.dominio.transacao.tipo.CategoriaTransacao;
import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosAtualizarTransacao(String descricao,
                                      BigDecimal valor,
                                      LocalDate data,
                                      TipoTransacao tipo,
                                      CategoriaTransacao categoria) {
}

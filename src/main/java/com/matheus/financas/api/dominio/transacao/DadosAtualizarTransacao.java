package com.matheus.financas.api.dominio.transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosAtualizarTransacao(String descricao,
                                      BigDecimal valor,
                                      LocalDate data,
                                      TipoTransacao tipo,
                                      CategoriaTransacao categoria) {
}

package com.matheus.financas.api.dominio.transacao;

import com.matheus.financas.api.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoTransacao(long id,
                                         String descricao,
                                         BigDecimal valor,
                                         LocalDate data,
                                         TipoTransacao tipo) {

    public DadosDetalhamentoTransacao(Transacao transacao) {
        this(transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getData(),
                transacao.getTipo());
}

}


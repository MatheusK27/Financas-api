package com.matheus.financas.api.dominio.transacao.dto;

import com.matheus.financas.api.dominio.transacao.tipo.CategoriaTransacao;
import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;
import com.matheus.financas.api.dominio.transacao.entidade.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DadosDetalhamentoTransacao(long id,
                                         String descricao,
                                         BigDecimal valor,
                                         LocalDate data,
                                         TipoTransacao tipo,
                                         CategoriaTransacao categoria) {

    public DadosDetalhamentoTransacao(Transacao transacao) {
        this(transacao.getId(),
                transacao.getDescricao(),
                transacao.getValor(),
                transacao.getData(),
                transacao.getTipo(),
                transacao.getCategoria());
}

}


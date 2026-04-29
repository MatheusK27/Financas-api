package com.matheus.financas.api.dominio.transacao.dto;


import com.matheus.financas.api.dominio.transacao.tipo.CategoriaTransacao;

import java.math.BigDecimal;

public record DadosResumoCategoria(CategoriaTransacao categoria, BigDecimal total ) {
}

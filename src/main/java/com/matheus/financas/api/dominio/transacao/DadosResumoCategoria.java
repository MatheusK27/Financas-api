package com.matheus.financas.api.dominio.transacao;


import java.math.BigDecimal;

public record DadosResumoCategoria(CategoriaTransacao categoria, BigDecimal total) {
}

package com.matheus.financas.api.dominio.transacao.dto;


import java.math.BigDecimal;

public record DadosDashboard(
        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        BigDecimal saldo,
        BigDecimal maiorDespesa,
        DadosMaiorDepesa dados,
        Long quantidadeTransacao
) {
}

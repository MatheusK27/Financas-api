package com.matheus.financas.api.dominio.transacao.dto;


import java.math.BigDecimal;

public record DadosDashboard(
        BigDecimal totalReceitas,
        BigDecimal totalDespesas,
        BigDecimal saldo,
        DadosMaiorDepesa dados,
        Long quantidadeTransacao
) {
}

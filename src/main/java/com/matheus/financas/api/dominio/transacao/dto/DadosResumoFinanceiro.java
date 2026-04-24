package com.matheus.financas.api.dominio.transacao.dto;

import java.math.BigDecimal;

public record DadosResumoFinanceiro(
        BigDecimal totalDespea,
        BigDecimal totalReceita,
        BigDecimal saldo

) {
}

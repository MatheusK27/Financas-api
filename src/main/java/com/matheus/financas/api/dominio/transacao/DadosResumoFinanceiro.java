package com.matheus.financas.api.dominio.transacao;

import java.math.BigDecimal;

public record DadosResumoFinanceiro(
        BigDecimal totalDespea,
        BigDecimal totalReceita,
        BigDecimal saldo

) {
}

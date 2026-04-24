package com.matheus.financas.api.dominio.transacao.dto;


import java.math.BigDecimal;

public record DadosMaiorDepesa(String descricao, BigDecimal valor) {
}

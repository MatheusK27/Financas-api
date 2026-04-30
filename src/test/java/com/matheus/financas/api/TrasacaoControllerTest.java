package com.matheus.financas.api;


import com.matheus.financas.api.Controller.TransacaoController;
import com.matheus.financas.api.Service.TransacaoService;
import com.matheus.financas.api.dominio.transacao.dto.DadosDashboard;
import com.matheus.financas.api.dominio.transacao.dto.DadosMaiorDepesa;
import com.matheus.financas.api.dominio.transacao.repository.TransacaoRepository;
import com.matheus.financas.api.dominio.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import com.matheus.financas.api.Security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacaoController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrasacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransacaoService service;

    @MockitoBean
    private TransacaoRepository repository;

    @MockitoBean
    private TokenService tokenService;

    @MockitoBean
    private UsuarioRepository usuarioRepository;

    @Test
    public void deveriaReturnarDashboard() throws Exception {
        var dadosDashborad= new DadosDashboard(
                new BigDecimal("5000.00"),
                new BigDecimal("2000.00"),
                new BigDecimal("3000.00"),
                new DadosMaiorDepesa("Aluguel", new BigDecimal("1200.00")),
                5L

        );

        when(service.dashboard(any())).thenReturn(dadosDashborad);

        mockMvc.perform(get("/transacoes/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalReceitas").value(5000.00))
                .andExpect(jsonPath("$.totalDespesas").value(2000.00))
                .andExpect(jsonPath("$.saldo").value(3000.00))
                .andExpect(jsonPath("$.dados.descricao").value("Aluguel"))
                .andExpect(jsonPath("$.dados.valor").value(1200.00))
                .andExpect(jsonPath("$.quantidadeTransacao").value(5));
    }

}

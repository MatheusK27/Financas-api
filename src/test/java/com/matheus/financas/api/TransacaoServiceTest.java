package com.matheus.financas.api;


import com.matheus.financas.api.Service.TransacaoService;
import com.matheus.financas.api.dominio.transacao.dto.DadosCadastroTransacao;
import com.matheus.financas.api.dominio.transacao.entidade.Transacao;
import com.matheus.financas.api.dominio.transacao.repository.TransacaoRepository;
import com.matheus.financas.api.dominio.transacao.tipo.CategoriaTransacao;
import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;
import com.matheus.financas.api.dominio.usuario.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {
    @Mock
    private TransacaoRepository repository;

    @InjectMocks
    private TransacaoService service;

    @Test
    void deveriaGerarDashboar(){
        var usuario= criarUsuario();

        when(repository.somarPorUsuarioETipo(usuario, TipoTransacao.RECEITA))
                .thenReturn(new BigDecimal("5000.00"));

        when(repository.somarPorUsuarioETipo(usuario, TipoTransacao.DESPESA))
                .thenReturn( new BigDecimal("2000.00"));

        when(repository.countByUsuario(usuario)).thenReturn(3L);

        var maiorTransacao= new Transacao(
                "Aluguel",
                new BigDecimal("1200.00"),
                LocalDate.now(),
                TipoTransacao.DESPESA,
                usuario);

        when(repository.buscarMaiorDespesa(usuario)).thenReturn(maiorTransacao);

        var resultado= service.dashboard(usuario);
        assertThat(resultado.totalReceitas()).isEqualByComparingTo("5000.00");
        assertThat(resultado.totalDespesas()).isEqualByComparingTo("2000.00");
        assertThat(resultado.saldo()).isEqualByComparingTo("3000.00");
        assertThat(resultado.quantidadeTransacao()).isEqualTo(3L);
        assertThat(resultado.dados().descricao()).isEqualTo("Aluguel");
        assertThat(resultado.dados().valor()).isEqualByComparingTo("1200.00");
    }

    private Usuario criarUsuario(){
        return  new Usuario("Nathalia", "nathalia@gmail.com", "123456");
    }









    }







}

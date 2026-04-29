package com.matheus.financas.api;


import com.matheus.financas.api.dominio.transacao.entidade.Transacao;
import com.matheus.financas.api.dominio.transacao.repository.TransacaoRepository;
import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;
import com.matheus.financas.api.dominio.usuario.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class TransacaoRepositoryTeste {
    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    public void DeveriaSomarDespesasDoMes(){
        var usuario=cadastrarUsuario();

        cadastrarTransacao(usuario, "Mercado", new BigDecimal("100.00"),
                LocalDate.of(2026,4,10), TipoTransacao.DESPESA);

        cadastrarTransacao(usuario, "Internet", new BigDecimal("200.00"),
                LocalDate.of(2026,4,10), TipoTransacao.DESPESA);

        var resultado= repository.somarPorUsuarioTipoEMes(usuario,TipoTransacao.DESPESA,
                4,2026);

        assertThat(resultado).isEqualByComparingTo(new BigDecimal("300.00"));

    }

    @Test
    public void deveriaBuscarMaiorDespesaDoUsuario(){
        var usuario=cadastrarUsuario();

        cadastrarTransacao(usuario,"Mercado", new BigDecimal("150.00"),
                LocalDate.of(2026,4,10), TipoTransacao.DESPESA);

        cadastrarTransacao(usuario, "Aluguel", new BigDecimal("1200.00"),
                LocalDate.of(2026, 4, 5), TipoTransacao.DESPESA);

        var resultadp= repository.maiorDepesa(usuario);

        assertThat(resultadp).isEqualByComparingTo(new BigDecimal("1200.00"));
    }

    private Usuario cadastrarUsuario(){
        var usuario = new Usuario("Matheus","Matheus@hotmail.com", "123456");

        em.persist(usuario);
        return usuario;
    }

    private void cadastrarTransacao(
            Usuario usuario,
            String descricao,
            BigDecimal valor,
            LocalDate data,
            TipoTransacao tipoTransacao
    ){
        var transacao= new Transacao(descricao,valor,data,tipoTransacao,usuario);
        em.persist(transacao);
    }


}

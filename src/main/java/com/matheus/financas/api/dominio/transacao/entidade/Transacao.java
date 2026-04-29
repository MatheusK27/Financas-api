package com.matheus.financas.api.dominio.transacao.entidade;


import com.matheus.financas.api.dominio.transacao.tipo.CategoriaTransacao;
import com.matheus.financas.api.dominio.transacao.dto.DadosAtualizarTransacao;
import com.matheus.financas.api.dominio.transacao.dto.DadosCadastroTransacao;
import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;
import com.matheus.financas.api.dominio.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "transacoes")
@Entity(name = "Transacao")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    @Enumerated(EnumType.STRING)
    private CategoriaTransacao categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Transacao(DadosCadastroTransacao dados, Usuario usuario) {
        this.usuario = usuario;
        this.data = dados.data();
        this.descricao = dados.descricao();
        this.tipo = dados.tipo();
        this.valor = dados.valor();
        this.categoria=dados.categoria();
    }

    public Transacao(String descricao, BigDecimal valor, LocalDate data, TipoTransacao tipoTransacao, Usuario usuario) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.tipo = tipoTransacao;
        this.usuario = usuario;

    }


    public void  atualizarTransacao(DadosAtualizarTransacao dados){
        if (dados.descricao()!=null){
            this.descricao = dados.descricao();
        }
        if (dados.valor()!=null){
            this.valor = dados.valor();

        }
        if (dados.data()!=null){
            this.data = dados.data();
        }
        if (dados.tipo()!=null){
            this.tipo = dados.tipo();
        }
        if (dados.categoria()!=null){
            this.categoria=dados.categoria();
        }
   }

}

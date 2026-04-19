package com.matheus.financas.api.dominio.transacao;


import com.matheus.financas.api.TipoTransacao;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Transacao(String descricao, BigDecimal valor, LocalDate data, TipoTransacao tipo, Usuario usuario) {
    this.descricao = descricao;
    this.valor = valor;
    this.data = data;
    this.tipo = tipo;
    this.usuario = usuario;

}

   public void atualizarTransacao(DadosAtualizarTransacao dados){
        if(descricao != null){
            this.descricao= descricao;
        }

        if(valor != null){
            this.valor = valor;

        }
        if(data != null){
            this.data = data;
        }
        if(tipo != null){
            this.tipo = tipo;
        }
   }
   
}

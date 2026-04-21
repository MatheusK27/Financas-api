package com.matheus.financas.api.dominio.transacao;

import com.matheus.financas.api.TipoTransacao;
import com.matheus.financas.api.dominio.usuario.Usuario;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findAllByUsuario(Usuario usuario);


    Optional<Transacao> findByIdAndUsuario(Long Id, Usuario usuario);

    List<Transacao>  findAllByUsuarioAndTipo(Usuario usuario, TipoTransacao tipoTransacao);

    @Query("""
        select coalesce(sum(t.valor), 0)
        from Transacao t
        where t.usuario = :usuario
        and t.tipo = :tipo
    """)
    BigDecimal somarPorUsuarioETipo(@Param("usuario")Usuario usuario, @Param("tipo")TipoTransacao tipo);

    @Query("""
               select coalesce(sum(t.valor), 0)
               from Transacao t
               where t.usuario = :usuario
               and t.tipo = :tipo
               and month(t.data) = :mes
               and year(t.data) = :ano
           """)
    BigDecimal somarPorUsuarioTipoEMes(@Param("usuario") Usuario ususario,
                                       @Param("tipo") TipoTransacao tipo,
                                       @Param("mes") int mes,
                                       @Param("ano") int ano);
}

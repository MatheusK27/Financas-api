package com.matheus.financas.api.dominio.transacao.repository;

import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;
import com.matheus.financas.api.dominio.transacao.entidade.Transacao;
import com.matheus.financas.api.dominio.usuario.Usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    Page<Transacao> findAllByUsuario(Usuario usuario, Pageable pageable);


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





    List<Transacao> findAllByUsuarioAndDataBetween(Usuario usuario, LocalDate dataInicio, LocalDate dataFim);

    List<Transacao> findAllByUsuarioAndDataBetweenAndTipo(Usuario usuario, LocalDate dataInicio, LocalDate dataFim, TipoTransacao tipo);

    @Query("""
            SELECT  t.categoria, SUM(t.valor)
            FROM Transacao t
            WHERE t.usuario= :usuario
            AND t.tipo = 'DESPESA' 
            AND t.data  BETWEEN :dataInicio AND :dataFim
            GROUP BY t.categoria                               
                        """)
    List<Object[]> resumoPorCategoria(@Param("usuario") Usuario usuario,
                                   @Param("dataInicio") LocalDate dataInicio,
                                   @Param("dataFim") LocalDate dataFim);

    @Query("""
           SELECT COALESCE(MAX(t.valor), 0) 
                      FROM Transacao t
                                 WHERE t.usuario= :usuario AND t.tipo= 'DESPESA'
           """)
    BigDecimal maiorDepesa(Usuario usuario);

    Long countByUsuario(Usuario usuario);

    @Query("""
    SELECT t
    FROM Transacao t
    WHERE t.usuario = :usuario AND t.tipo = 'DESPESA'
    ORDER BY t.valor DESC
    LIMIT 1
""")
    Transacao buscarMaiorDespesa(Usuario usuario);
}

package com.matheus.financas.api.Service;


import com.matheus.financas.api.dominio.transacao.dto.*;
import com.matheus.financas.api.dominio.transacao.entidade.Transacao;
import com.matheus.financas.api.dominio.transacao.repository.TransacaoRepository;
import com.matheus.financas.api.dominio.transacao.tipo.CategoriaTransacao;
import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;
import com.matheus.financas.api.dominio.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository repository;

    public DadosResumoFinanceiro resumoGeral( Usuario usuario){
        var despesas= repository.somarPorUsuarioETipo(usuario, TipoTransacao.DESPESA);
        var receitas= repository.somarPorUsuarioETipo(usuario, TipoTransacao.RECEITA);
        var saldo = receitas.subtract(despesas);

        return new DadosResumoFinanceiro(receitas,despesas,saldo);
    }

    public DadosResumoFinanceiro resumoGeralMes( Usuario usuario,
                                                int mes,
                                                int ano){
        var despesas= repository.somarPorUsuarioTipoEMes(usuario,TipoTransacao.DESPESA,mes,ano);
        var receitas= repository.somarPorUsuarioTipoEMes(usuario,TipoTransacao.RECEITA,mes,ano);
        var saldo = receitas.subtract(despesas);

        return new DadosResumoFinanceiro(receitas,despesas,saldo);


    }

    public List<DadosDetalhamentoTransacao> listarDespesas(Usuario usuario){
        var lista= repository.findAllByUsuarioAndTipo(usuario, TipoTransacao.DESPESA)
                .stream().map(DadosDetalhamentoTransacao:: new).toList();
        return lista;

    }

    public List<DadosDetalhamentoTransacao> listarReceitas(Usuario usuario){
        var lista= repository.findAllByUsuarioAndTipo(usuario, TipoTransacao.RECEITA)
                .stream().map(DadosDetalhamentoTransacao:: new).toList();
        return lista;
    }

    public DadosDetalhamentoTransacao detalharPorId(Long id,Usuario usuario){
        var detalhar= repository.findByIdAndUsuario(id, usuario).orElseThrow(()-> new EntityNotFoundException("Transação não encontrada"));
        return new  DadosDetalhamentoTransacao(detalhar);
    }

    public List<DadosDetalhamentoTransacao> filtrar(Usuario usuario, LocalDate dataInicio, LocalDate dataFim, TipoTransacao tipo){
        List<Transacao>transacaos;
        if(tipo != null){
            transacaos=repository.findAllByUsuarioAndDataBetweenAndTipo(usuario, dataInicio, dataFim, tipo);
        }else{
            transacaos=repository.findAllByUsuarioAndDataBetween(usuario, dataInicio, dataFim);
        }
        var lista= transacaos.stream().map(DadosDetalhamentoTransacao:: new).toList();

        return lista;
    }

    public List<DadosResumoCategoria> resumoPorCategoria(Usuario usuario,LocalDate dataInicio, LocalDate dataFim){
        var resultado=repository.resumoPorCategoria(usuario, dataInicio, dataFim);
        var lista=resultado.stream().map(obj-> new DadosResumoCategoria(
                (CategoriaTransacao) obj[0],
                (BigDecimal) obj[1]
        )).toList();
        return lista;

    }

    public DadosDashboard dashboard(Usuario usuario){
        var totalReceitas= repository.somarPorUsuarioETipo(usuario, TipoTransacao.RECEITA);
        var totalDespesas=repository.somarPorUsuarioETipo(usuario, TipoTransacao.DESPESA);
        var total= totalReceitas.subtract(totalDespesas);
        var quantidadeTransacao= repository.countByUsuario(usuario);
        var maiorDespesa= repository.buscarMaiorDespesa(usuario);
        DadosMaiorDepesa dadosMaiorDepesa= null;

        if(maiorDespesa !=null){
            dadosMaiorDepesa= new DadosMaiorDepesa(maiorDespesa.getDescricao(),maiorDespesa.getValor());

        }
        var dados= new DadosDashboard(totalReceitas,totalDespesas,total,dadosMaiorDepesa,quantidadeTransacao);
        return dados;

    }

}

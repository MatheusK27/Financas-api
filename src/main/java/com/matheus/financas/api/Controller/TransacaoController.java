package com.matheus.financas.api.Controller;


import com.matheus.financas.api.dominio.transacao.dto.*;
import com.matheus.financas.api.dominio.transacao.repository.TransacaoRepository;
import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;
import com.matheus.financas.api.dominio.transacao.entidade.Transacao;
import com.matheus.financas.api.dominio.transacao.tipo.CategoriaTransacao;
import com.matheus.financas.api.dominio.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;



@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;



    @PostMapping
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroTransacao dados,
                                        @AuthenticationPrincipal Usuario usuarioLogado){
        var usuario= usuarioLogado;
        var transacao = new Transacao(dados,usuario);
        transacaoRepository.save(transacao);
        return ResponseEntity.created(URI.create("/transacoes" + transacao.getId()))
                .body(new DadosDetalhamentoTransacao(transacao));
    }



    @Operation(summary = "Listar transações usuário logado")
    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoTransacao>> listar(@AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable) {

      var page= transacaoRepository.findAllByUsuario(usuarioLogado,pageable)
              .map(DadosDetalhamentoTransacao::new);
      return ResponseEntity.ok(page);

    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizar(@PathVariable Long id, @RequestBody @Valid DadosAtualizarTransacao dados,
                                    @AuthenticationPrincipal Usuario usuarioLogado){
        var transacao= transacaoRepository.findByIdAndUsuario(id, usuarioLogado).
                orElseThrow(() -> new RuntimeException("Transação não encontrada"));

        transacao.atualizarTransacao(dados);

        return ResponseEntity.ok(new DadosDetalhamentoTransacao(transacao));

    }

    @GetMapping("/despesas")
    public ResponseEntity<List<DadosDetalhamentoTransacao>> listarDespesas(@AuthenticationPrincipal Usuario usuarioLogado){
        var lista = transacaoRepository.findAllByUsuarioAndTipo(usuarioLogado, TipoTransacao.DESPESA)
                .stream()
                .map(DadosDetalhamentoTransacao::new)
                .toList();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/receitas")
    public ResponseEntity<List<DadosDetalhamentoTransacao>> listarReceitas(@AuthenticationPrincipal Usuario usuarioLogado){
        var lista = transacaoRepository.findAllByUsuarioAndTipo(usuarioLogado, TipoTransacao.RECEITA)
                .stream()
                .map(DadosDetalhamentoTransacao::new)
                .toList();

        return ResponseEntity.ok(lista);

    }

    @GetMapping("/resumo")
    public ResponseEntity<DadosResumoFinanceiro> resumoGeral(@AuthenticationPrincipal Usuario usuario){
        var totalDespesas= transacaoRepository.somarPorUsuarioETipo(usuario,TipoTransacao.DESPESA);
        var totalReceitas= transacaoRepository.somarPorUsuarioETipo(usuario,TipoTransacao.RECEITA);
        var saldo= totalReceitas.subtract(totalDespesas);

        return ResponseEntity.ok(
                new DadosResumoFinanceiro(totalReceitas,totalDespesas, saldo));
    }

    @Operation(summary = "Resumo financeiro mensal usuário logado")
    @GetMapping("/resumo/{ano}/{mes}")
    public ResponseEntity<DadosResumoFinanceiro>  resumoGeralMes(@AuthenticationPrincipal Usuario usuario,
                                                                 @PathVariable int mes,
                                                                 @PathVariable int ano){
        var totalDespesas= transacaoRepository.somarPorUsuarioTipoEMes(usuario, TipoTransacao.DESPESA,mes,ano);
        var totalReceitas= transacaoRepository.somarPorUsuarioTipoEMes(usuario,TipoTransacao.RECEITA,mes, ano);
        var saldo= totalReceitas.subtract(totalDespesas);

        return ResponseEntity.ok(
                new DadosResumoFinanceiro(totalReceitas, totalDespesas, saldo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTransacao> detalharPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado){
        var transacao= transacaoRepository.findByIdAndUsuario(id,usuarioLogado)
                .orElseThrow(()-> new EntityNotFoundException("Transação não encontrada"));
        return ResponseEntity.ok(new DadosDetalhamentoTransacao(transacao));


    }

    @Operation(summary = "Filtrar por data inicio e data fim ou por tipo despesas")
    @GetMapping("/filtro")
    public ResponseEntity<List<DadosDetalhamentoTransacao>> filtrar(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim,
            @RequestParam (required = false) TipoTransacao tipo){
        List<Transacao>transacaos;
        if(tipo!=null){
            transacaos= transacaoRepository.findAllByUsuarioAndDataBetweenAndTipo(usuarioLogado, dataInicio, dataFim, tipo);
        }else {
            transacaos= transacaoRepository.findAllByUsuarioAndDataBetween(usuarioLogado, dataInicio, dataFim);
        }
        var lista = transacaos.stream().map(DadosDetalhamentoTransacao::new).toList();
        return ResponseEntity.ok(lista);

    }

    @GetMapping("/resumo/categoria")
    public ResponseEntity<List<DadosResumoCategoria>> resumoPorCategoria(
            @AuthenticationPrincipal Usuario usuario,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

        var resultado = transacaoRepository.resumoPorCategoria(usuario, dataInicio, dataFim);

        var lista = resultado.stream()
                .map(obj -> new DadosResumoCategoria(
                        (CategoriaTransacao) obj[0],
                        (BigDecimal) obj[1]
                ))
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DadosDashboard> dashboard(@AuthenticationPrincipal Usuario usuario){
        var totalReceitas= transacaoRepository.somarPorUsuarioETipo(usuario,TipoTransacao.RECEITA);
        var totalDespesas= transacaoRepository.somarPorUsuarioETipo(usuario,TipoTransacao.DESPESA);

        var saldo= totalReceitas.subtract(totalDespesas);
        var quantidadeTransacoes= transacaoRepository.countByUsuario(usuario);
        var transacaoMaior= transacaoRepository.buscarMaiorDespesa(usuario);

        DadosMaiorDepesa maiorDespesa= null;
        if(transacaoMaior!=null){
            maiorDespesa= new DadosMaiorDepesa(transacaoMaior.getDescricao(),transacaoMaior.getValor());
        }


        var dados=  new DadosDashboard(totalReceitas,totalDespesas,saldo,maiorDespesa,quantidadeTransacoes);

        return ResponseEntity.ok(dados);

    }

        }








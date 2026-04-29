package com.matheus.financas.api.Controller;


import com.matheus.financas.api.Service.TransacaoService;
import com.matheus.financas.api.dominio.transacao.dto.*;
import com.matheus.financas.api.dominio.transacao.repository.TransacaoRepository;
import com.matheus.financas.api.dominio.transacao.tipo.TipoTransacao;
import com.matheus.financas.api.dominio.transacao.entidade.Transacao;
import com.matheus.financas.api.dominio.transacao.tipo.CategoriaTransacao;
import com.matheus.financas.api.dominio.usuario.Usuario;
import com.matheus.financas.api.dominio.usuario.UsuarioRepository;
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

    @Autowired
    private TransacaoService service;



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
    public ResponseEntity listarDespesas(@AuthenticationPrincipal Usuario usuarioLogado){
        return ResponseEntity.ok(service.listarDespesas(usuarioLogado));
    }

    @GetMapping("/receitas")
    public ResponseEntity<List<DadosDetalhamentoTransacao>> listarReceitas(@AuthenticationPrincipal Usuario usuarioLogado){
         return ResponseEntity.ok(service.listarReceitas(usuarioLogado));

    }

    @GetMapping("/resumo")
    public ResponseEntity<DadosResumoFinanceiro> resumoGeral(@AuthenticationPrincipal Usuario usuario){
      return ResponseEntity.ok(service.resumoGeral(usuario));
    }



    @Operation(summary = "Resumo financeiro mensal usuário logado")
    @GetMapping("/resumo/{ano}/{mes}")
    public ResponseEntity<DadosResumoFinanceiro>  resumoGeralMes(@AuthenticationPrincipal Usuario usuario,
                                                                 @PathVariable int mes,
                                                                 @PathVariable int ano){
       return ResponseEntity.ok(service.resumoGeralMes(usuario,mes,ano));
    }




    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTransacao> detalharPorId(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuarioLogado){
        return ResponseEntity.ok(service.detalharPorId(id,usuarioLogado));


    }

    @Operation(summary = "Filtrar por data inicio e data fim ou por tipo despesas")
    @GetMapping("/filtro")
    public ResponseEntity<List<DadosDetalhamentoTransacao>> filtrar(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim,
            @RequestParam (required = false) TipoTransacao tipo){
    return ResponseEntity.ok(service.filtrar(usuarioLogado,dataInicio,dataFim,tipo));

    }

    @GetMapping("/resumo/categoria")
    public ResponseEntity<List<DadosResumoCategoria>> resumoPorCategoria(
            @AuthenticationPrincipal Usuario usuario,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {
     return ResponseEntity.ok(service.resumoPorCategoria(usuario,dataInicio,dataFim));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DadosDashboard> dashboard(@AuthenticationPrincipal Usuario usuario){
    return ResponseEntity.ok(service.dashboard(usuario));
    }

        }








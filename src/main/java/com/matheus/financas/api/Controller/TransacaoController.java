package com.matheus.financas.api.Controller;


import com.matheus.financas.api.TipoTransacao;
import com.matheus.financas.api.dominio.transacao.*;
import com.matheus.financas.api.dominio.usuario.Usuario;
import com.matheus.financas.api.dominio.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PutExchange;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoTransacao>> listar(@AuthenticationPrincipal Usuario usuarioLogado) {

        var usuario = usuarioRepository.findByEmail(usuarioLogado.getUsername());

        var lista = transacaoRepository.findAllByUsuario(usuario)
                .stream()
                .map(DadosDetalhamentoTransacao::new)
                .toList();

        return ResponseEntity.ok(lista);

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


}


package com.tandre.controller.api;

import com.tandre.model.Tarefa;
import com.tandre.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaApiController {

    @Autowired
    private TarefaService tarefaService;

    // GEt - listar todas as tarefas
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodas(@RequestParam(required = false) Boolean concluida) {
        List<Tarefa> tarefas;
        if (concluida != null) {
            tarefas = tarefaService.listarPorStatus(concluida);
        } else {
            tarefas = tarefaService.listarTodas();
        }
        return ResponseEntity.ok(tarefas);
    }

    // buscar tarefa por id
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
        return tarefaService.buscarPorId(id)
                .map(tarefa -> ResponseEntity.ok(tarefa))
                .orElse(ResponseEntity.notFound().build());
    }

    // POST- criar nova tarefa
    @PostMapping
    public ResponseEntity<?> criar(@Valid @RequestBody Tarefa tarefa, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        Tarefa tarefaSalva = tarefaService.salvar(tarefa);
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaSalva);
    }

    // PUT - atualizar tarefa
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, 
                                       @Valid @RequestBody Tarefa tarefa, 
                                       BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        try {
            Tarefa tarefaAtualizada = tarefaService.atualizar(id, tarefa);
            return ResponseEntity.ok(tarefaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE -deletar tarefa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            tarefaService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH -alterar status
    @PatchMapping("/{id}/toggle-status")
    public ResponseEntity<Tarefa> alterarStatus(@PathVariable Long id) {
        try {
            Tarefa tarefa = tarefaService.alterarStatus(id);
            return ResponseEntity.ok(tarefa);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET -buscar por título
    @GetMapping("/buscar")
    public ResponseEntity<List<Tarefa>> buscarPorTitulo(@RequestParam String titulo) {
        List<Tarefa> tarefas = tarefaService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(tarefas);
    }
}


package com.tandre.controller;

import com.tandre.model.Tarefa;
import com.tandre.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    //  lista todas as tarefas
    @GetMapping
    public String listarTarefas(Model model, @RequestParam(required = false) Boolean concluida) {
        List<Tarefa> tarefas;
        if (concluida != null) {
            tarefas = tarefaService.listarPorStatus(concluida);
        } else {
            tarefas = tarefaService.listarTodas();
        }
        model.addAttribute("tarefas", tarefas);
        model.addAttribute("tarefa", new Tarefa());
        return "tarefas/listar";
    }

    // página de criação
    @GetMapping("/novo")
    public String formularioNovaTarefa(Model model) {
        model.addAttribute("tarefa", new Tarefa());
        return "tarefas/formulario";
    }

    // criação de tarefa
    @PostMapping
    public String criarTarefa(@Valid @ModelAttribute Tarefa tarefa, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "tarefas/formulario";
        }
        tarefaService.salvar(tarefa);
        redirectAttributes.addFlashAttribute("mensagem", "Tarefa criada com sucesso!");
        return "redirect:/tarefas";
    }

    // página de edição
    @GetMapping("/{id}/editar")
    public String formularioEditarTarefa(@PathVariable Long id, Model model) {
        Tarefa tarefa = tarefaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        model.addAttribute("tarefa", tarefa);
        return "tarefas/formulario";
    }

    // atualizar tarefa
    @PostMapping("/{id}")
    public String atualizarTarefa(@PathVariable Long id, 
                                  @Valid @ModelAttribute Tarefa tarefa,
                                  BindingResult result,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "tarefas/formulario";
        }
        tarefaService.atualizar(id, tarefa);
        redirectAttributes.addFlashAttribute("mensagem", "Tarefa atualizada com sucesso!");
        return "redirect:/tarefas";
    }

    // deletar tarefa
    @PostMapping("/{id}/deletar")
    public String deletarTarefa(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tarefaService.deletar(id);
        redirectAttributes.addFlashAttribute("mensagem", "Tarefa deletada com sucesso!");
        return "redirect:/tarefas";
    }

    // alterar status
    @PostMapping("/{id}/toggle-status")
    public String alterarStatus(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tarefaService.alterarStatus(id);
        redirectAttributes.addFlashAttribute("mensagem", "Status da tarefa alterado com sucesso!");
        return "redirect:/tarefas";
    }
}


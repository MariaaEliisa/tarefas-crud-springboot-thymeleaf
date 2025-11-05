package com.tandre.service;

import com.tandre.model.Tarefa;
import com.tandre.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> listarTodas() {
        return tarefaRepository.findAll();
    }

    public List<Tarefa> listarPorStatus(Boolean concluida) {
        return tarefaRepository.findByConcluida(concluida);
    }

    public List<Tarefa> buscarPorTitulo(String titulo) {
        return tarefaRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Optional<Tarefa> buscarPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    public Tarefa salvar(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizar(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));
        
        tarefa.setTitulo(tarefaAtualizada.getTitulo());
        tarefa.setDescricao(tarefaAtualizada.getDescricao());
        tarefa.setConcluida(tarefaAtualizada.getConcluida());
        
        return tarefaRepository.save(tarefa);
    }

    public void deletar(Long id) {
        if (!tarefaRepository.existsById(id)) {
            throw new RuntimeException("Tarefa não encontrada com id: " + id);
        }
        tarefaRepository.deleteById(id);
    }

    public Tarefa alterarStatus(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada com id: " + id));
        
        tarefa.setConcluida(!tarefa.getConcluida());
        return tarefaRepository.save(tarefa);
    }
}


package com.events.api.events.service;

import com.events.api.events.dto.SolucaoDTO;
import com.events.api.events.model.Solucao;
import com.events.api.events.model.Trabalho;
import com.events.api.events.model.Usuario;
import com.events.api.events.repository.SolucaoRepository;
import com.events.api.events.repository.TrabalhoRepository;
import com.events.api.events.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class SolucaoService {

    @Autowired
    private SolucaoRepository solucaoRepository;

    @Autowired
    private TrabalhoRepository trabalhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public SolucaoDTO submitSolucao(Long trabalhoId, MultipartFile file, String username) {
        Usuario aluno = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Trabalho trabalho = trabalhoRepository.findById(trabalhoId)
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado"));

        String fileName = fileStorageService.storeFile(file);

        Solucao solucao = Solucao.builder()
                .caminhoArquivo(fileName)
                .dataSubmissao(LocalDateTime.now())
                .aluno(aluno)
                .trabalho(trabalho)
                .build();

        solucaoRepository.save(solucao);

        return convertToDTO(solucao);
    }

    public List<SolucaoDTO> getSolucoesByTrabalho(Long trabalhoId) {
        Trabalho trabalho = trabalhoRepository.findById(trabalhoId)
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado"));

        return solucaoRepository.findByTrabalho(trabalho).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SolucaoDTO> getSolucoesByAluno(String username) {
        Usuario aluno = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return solucaoRepository.findByAluno(aluno).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SolucaoDTO avaliarSolucao(Long id, String comentario, Double nota, String username) {
        Solucao solucao = solucaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solução não encontrada"));

        if (!solucao.getTrabalho().getAutor().getUsername().equals(username)) {
            throw new RuntimeException("Avaliador não autorizado");
        }

        solucao.setComentarioAvaliacao(comentario);
        solucao.setNotaAvaliacao(nota);
        solucaoRepository.save(solucao);

        return convertToDTO(solucao);
    }

    private SolucaoDTO convertToDTO(Solucao solucao) {
        return SolucaoDTO.builder()
                .id(solucao.getId())
                .caminhoArquivo(solucao.getCaminhoArquivo())
                .dataSubmissao(solucao.getDataSubmissao())
                .comentarioAvaliacao(solucao.getComentarioAvaliacao())
                .notaAvaliacao(solucao.getNotaAvaliacao())
                .aluno(solucao.getAluno().getUsername())
                .trabalhoId(solucao.getTrabalho().getId())
                .build();
    }
}
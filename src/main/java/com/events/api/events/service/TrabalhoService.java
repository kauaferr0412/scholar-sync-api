package com.events.api.events.service;

import com.events.api.events.dto.SolucaoDTO;
import com.events.api.events.dto.TrabalhoDTO;
import com.events.api.events.model.Solucao;
import com.events.api.events.model.Trabalho;
import com.events.api.events.model.Usuario;
import com.events.api.events.repository.SolucaoRepository;
import com.events.api.events.repository.TrabalhoRepository;
import com.events.api.events.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrabalhoService {

    @Autowired
    private TrabalhoRepository trabalhoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SolucaoRepository solucaoRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public TrabalhoDTO createTrabalho(TrabalhoDTO trabalhoDTO, String username) {
        Usuario autor = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Trabalho trabalho = Trabalho.builder()
                .titulo(trabalhoDTO.getTitulo())
                .descricao(trabalhoDTO.getDescricao())
                .autor(autor)
                .build();

        trabalhoRepository.save(trabalho);

        return convertToDTO(trabalho);
    }

    public List<TrabalhoDTO> getTrabalhosByAutor(String username) {
        Usuario autor = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return trabalhoRepository.findByAutor(autor).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TrabalhoDTO convertToDTO(Trabalho trabalho) {
        return TrabalhoDTO.builder()
                .id(trabalho.getId())
                .titulo(trabalho.getTitulo())
                .descricao(trabalho.getDescricao())
                .autor(trabalho.getAutor().getUsername())
                .solucoes(Objects.nonNull(trabalho.getSolucoes()) && !trabalho.getSolucoes().isEmpty()
                        ? trabalho.getSolucoes().stream()
                        .map(solucao -> SolucaoDTO.builder()
                                .id(solucao.getId())
                                .caminhoArquivo(solucao.getCaminhoArquivo())
                                .dataSubmissao(solucao.getDataSubmissao())
                                .comentarioAvaliacao(solucao.getComentarioAvaliacao())
                                .notaAvaliacao(solucao.getNotaAvaliacao())
                                .aluno(solucao.getAluno().getUsername())
                                .trabalhoId(trabalho.getId())
                                .build())
                        .collect(Collectors.toSet())
                         : null)
                .build();
    }

    public Resource getFileBySolucaoId(Long solucaoId, String username) {
        Solucao solucao = solucaoRepository.findById(solucaoId)
                .orElseThrow(() -> new RuntimeException("Solução não encontrada"));

        if (!solucao.getTrabalho().getAutor().getUsername().equals(username)) {
            throw new RuntimeException("Acesso negado. O usuário não é o avaliador desse trabalho.");
        }

        return fileStorageService.loadFileAsResource(solucao.getCaminhoArquivo());
    }
}
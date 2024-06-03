package com.events.api.events.controller;


import com.events.api.events.dto.SolucaoDTO;
import com.events.api.events.dto.TrabalhoDTO;
import com.events.api.events.service.SolucaoService;
import com.events.api.events.service.TrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/trabalhos")
public class TrabalhoController {

    @Autowired
    private TrabalhoService trabalhoService;

    @Autowired
    private SolucaoService solucaoService;

    @PostMapping
    public TrabalhoDTO createTrabalho(@RequestBody TrabalhoDTO trabalhoDTO, Authentication authentication) {
        String username = authentication.getName();
        return trabalhoService.createTrabalho(trabalhoDTO, username);
    }

    @GetMapping
    public List<TrabalhoDTO> getTrabalhosDisponiveis(Authentication authentication) {
        String username = authentication.getName();
        return trabalhoService.getTrabalhosDisponiveisParaUsuario(username);
    }

    @GetMapping("/autor")
    public List<TrabalhoDTO> getTrabalhosByAutor(Authentication authentication) {
        String username = authentication.getName();
        return trabalhoService.getTrabalhosByAutor(username);
    }

    @PostMapping("/{trabalhoId}/solucoes")
    public SolucaoDTO submitSolucao(@PathVariable Long trabalhoId, @RequestParam("file") MultipartFile file, Authentication authentication) {
        String username = authentication.getName();
        return solucaoService.submitSolucao(trabalhoId, file, username);
    }

    @GetMapping("/{trabalhoId}/solucoes")
    public List<SolucaoDTO> getSolucoesByTrabalho(@PathVariable Long trabalhoId) {
        return solucaoService.getSolucoesByTrabalho(trabalhoId);
    }

    @PostMapping("/solucoes/{solucaoId}/avaliar")
    public SolucaoDTO avaliarSolucao(@PathVariable Long solucaoId, @RequestParam String comentario, @RequestParam int nota, Authentication authentication) {
        String username = authentication.getName();
        return solucaoService.avaliarSolucao(solucaoId, comentario, nota, username);
    }

    @GetMapping("/aluno/solucoes")
    public List<SolucaoDTO> getSolucoesByAluno(Authentication authentication) {
        String username = authentication.getName();
        return solucaoService.getSolucoesByAluno(username);
    }

    @GetMapping("/solucoes/{solucaoId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long solucaoId, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Resource resource = trabalhoService.getFileBySolucaoId(solucaoId, username);

        MediaType mediaType = MediaType.parseMediaType(determineContentType(resource));

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    private String determineContentType(Resource resource) {
        String filename = resource.getFilename();
        if (filename != null) {
            if (filename.endsWith(".pdf")) {
                return "application/pdf";
            } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                return "image/jpeg";
            } else if (filename.endsWith(".png")) {
                return "image/png";
            } else if (filename.endsWith(".gif")) {
                return "image/gif";
            }
        }
        return MediaType.APPLICATION_OCTET_STREAM_VALUE;
    }
}
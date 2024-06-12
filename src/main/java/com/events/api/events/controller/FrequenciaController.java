package com.events.api.events.controller;


import com.events.api.events.dto.FrequenciaDTO;
import com.events.api.events.service.FrequenciaService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/frequencias")
public class FrequenciaController {

    @Autowired
    private FrequenciaService frequenciaService;

    @PostMapping("/registrar")
    public ResponseEntity<FrequenciaDTO> registrarFrequencia(@RequestParam Long eventoId, @RequestParam Long alunoId, @AuthenticationPrincipal UserDetails userDetails) {
        String professorUsername = userDetails.getUsername();
        FrequenciaDTO frequencia = frequenciaService.registrarFrequencia(eventoId, alunoId, professorUsername);
        return ResponseEntity.ok(frequencia);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<FrequenciaDTO>> getFrequenciasByAluno(@PathVariable Long alunoId) {
        List<FrequenciaDTO> frequencias = frequenciaService.getFrequenciasByAluno(alunoId);
        return ResponseEntity.ok(frequencias);
    }

    @GetMapping("/evento/{eventoId}")
    public ResponseEntity<List<FrequenciaDTO>> getFrequenciasByEvento(@PathVariable Long eventoId) {
        List<FrequenciaDTO> frequencias = frequenciaService.getFrequenciasByEvento(eventoId);
        return ResponseEntity.ok(frequencias);
    }

    @GetMapping("/certificado")
    public ResponseEntity<byte[]> gerarCertificado(@RequestParam Long eventoId, @RequestParam Long alunoId) throws DocumentException, IOException {
        byte[] pdf = frequenciaService.gerarCertificado(eventoId, alunoId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificado.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
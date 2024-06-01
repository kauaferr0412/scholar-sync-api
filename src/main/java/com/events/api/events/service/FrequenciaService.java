package com.events.api.events.service;

import com.events.api.events.dto.FrequenciaDTO;
import com.events.api.events.dto.UsuarioDTO;
import com.events.api.events.model.Evento;
import com.events.api.events.model.Frequencia;
import com.events.api.events.model.Usuario;
import com.events.api.events.repository.FrequenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.events.api.events.repository.UsuarioRepository;
import com.events.api.events.repository.EventoRepository;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.itextpdf.text.*;

@Service
public class FrequenciaService {

    @Autowired
    private FrequenciaRepository frequenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public FrequenciaDTO registrarFrequencia(Long eventoId, Long alunoId, String professorUsername) {
        Evento evento = eventoRepository.findByIdWithParticipantes(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        Usuario professor = usuarioRepository.findByUsername(professorUsername)
                .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

        Optional<Usuario> usuarioParticipante = evento.getParticipantes().stream()
                .filter(usuario -> usuario.getId() == alunoId)
                .findFirst();

        if (!evento.getOrganizador().equals(professor)) {
            throw new RuntimeException("Professor não autorizado para registrar frequência neste evento");
        }

        Frequencia frequencia = Frequencia.builder()
                .evento(evento)
                .aluno(usuarioParticipante.get())
                .dataHoraRegistro(LocalDateTime.now())
                .build();
        frequenciaRepository.save(frequencia);

        return FrequenciaDTO
                .builder()
                .idEvento(evento.getId())
                .nomeParticipante(usuarioParticipante.get().getUsername())
                .dataHora(LocalDateTime.now()).build();
    }

    public List<FrequenciaDTO> getFrequenciasByAluno(Long alunoId) {
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        return frequenciaRepository.findByAluno(aluno).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<FrequenciaDTO> getFrequenciasByEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        return frequenciaRepository.findByEvento(evento).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public byte[] gerarCertificado(Long eventoId, Long alunoId) throws DocumentException {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Frequencia frequencia = frequenciaRepository.findByAlunoAndEvento(aluno, evento)
                .orElseThrow(() -> new RuntimeException("Frequência não registrada para este aluno e evento"));

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        Paragraph title = new Paragraph("Certificado de Participação");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        Paragraph content = new Paragraph("Certificamos que " + aluno.getNome() + " participou do evento " + evento.getTitulo() + " realizado em " + evento.getDataInicio() + ".");
        content.setAlignment(Element.ALIGN_CENTER);
        document.add(content);

        document.close();

        return out.toByteArray();
    }


    public FrequenciaDTO convertToDTO(Frequencia frequencia) {

        return FrequenciaDTO
                .builder()
                .id(frequencia.getId())
                .nomeParticipante(frequencia.getAluno().getUsername())
                .idEvento(frequencia.getEvento().getId())
                .dataHora(LocalDateTime.now()).build();
    }
}

package com.events.api.events.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CertificadoService {

//    public byte[] gerarCertificado(String nomeParticipante, String nomeEvento) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PdfWriter writer = new PdfWriter (outputStream);
//        PdfDocument pdfDocument = new PdfDocument(writer);
//        Document document = new Document(pdfDocument);
//
//        document.add(new Paragraph("Certificado de Participação"));
//        document.add(new Paragraph("Nome do Participante: " + nomeParticipante));
//        document.add(new Paragraph("Evento: " + nomeEvento));
//
//        document.close();
//        return outputStream.toByteArray();
//    }
}
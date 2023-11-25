package com.salomaotech.autoatendimento.model.impressao;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerarPdf {

    private final String pathDeSaidaDoArquivo;
    private final List conteudoDoCorpoDoDocumento = new ArrayList();
    private Document document;
    private int largura = 257;
    private int altura = 400;
    private final int margemEsquerda = 8;
    private final int margemDireita = 0;
    private final int margemTopo = 5;
    private final int margemRodape = 5;

    public GerarPdf(String pathDeSaidaDoArquivo) {

        this.pathDeSaidaDoArquivo = pathDeSaidaDoArquivo + ".pdf";

    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public String getPathDeSaidaDoArquivo() {

        return pathDeSaidaDoArquivo;

    }

    public void addConteudo(String conteudo) {

        conteudoDoCorpoDoDocumento.add(conteudo);

    }

    public void addConteudo(PdfPTable pdfPTable) {

        conteudoDoCorpoDoDocumento.add(pdfPTable);

    }

    public boolean gerar() {

        try {

            /* novo documento */
            document = new Document(new Rectangle(largura, altura), margemEsquerda, margemDireita, margemTopo, margemRodape);

            /* cria a pasta do arquivo caso não exista */
            criarPastaLocal(new File(this.pathDeSaidaDoArquivo).getParent());

            /* abre o documento */
            PdfWriter.getInstance(document, new FileOutputStream(pathDeSaidaDoArquivo));
            document.open();

            /* adiciona as linhas de conteúdo do corpo do documento */
            this.conteudoDoCorpoDoDocumento.forEach(linha -> {

                try {

                    switch (linha.getClass().getName()) {

                        case "java.lang.String":
                            document.add(new Paragraph((String) linha, new Font(Font.FontFamily.COURIER, 8, Font.NORMAL)));
                            break;

                        case "com.itextpdf.text.pdf.PdfPTable":
                            document.add((PdfPTable) linha);
                            break;

                    }

                } catch (DocumentException | java.lang.NullPointerException ex) {

                }

            });

            document.close();
            return new File(pathDeSaidaDoArquivo).exists();

        } catch (DocumentException | IOException | java.lang.NullPointerException ex) {

            return false;

        }

    }

    private void criarPastaLocal(String pathDestino) {

        try {

            new File(pathDestino).mkdirs();

        } catch (Exception ex) {

        }

    }

}

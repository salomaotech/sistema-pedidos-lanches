package com.salomaotech.autoatendimento.model.impressao;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.salomaotech.autoatendimento.model.entities.Pedido;
import com.salomaotech.autoatendimento.model.entities.PedidoItem;
import com.salomaotech.autoatendimento.util.ConverteNumeroParaMoedaBr;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

public class PedidoPdf {

    private final Pedido pedido;
    private GerarPdf gerarPdf;
    private final String pathDestino;

    public PedidoPdf(Pedido pedido) {

        this.pedido = pedido;
        String userDirectory = System.getProperty("user.dir");
        pathDestino = Paths.get(userDirectory, "uploads", "pedido", String.valueOf(pedido.getId())).toString();

    }

    private PdfPCell createCell(String conteudo, int alinhamento, int fonte) {

        PdfPCell cell = new PdfPCell(new Phrase(conteudo, new Font(Font.FontFamily.COURIER, 8, fonte)));
        cell.setBorderWidth(0);
        cell.setColspan(1);
        cell.setHorizontalAlignment(alinhamento);
        return cell;

    }

    private void imprimirPDF() {

        try {

            PDDocument document = PDDocument.load(new File(pathDestino + ".pdf"));
            PrinterJob job = PrinterJob.getPrinterJob();

            job.setPageable(new PDFPageable(document));
            job.print();
            document.close();

        } catch (IOException | PrinterException e) {

        }

    }

    public void gerar() {

        /* adiciona conteúdo ao gerador de PDF */
        gerarPdf = new GerarPdf(pathDestino);
        gerarPdf.addConteudo("");
        gerarPdf.addConteudo("==================================================");
        gerarPdf.addConteudo("Data: " + LocalDate.now());
        gerarPdf.addConteudo("Hora: " + LocalTime.now());
        gerarPdf.addConteudo("==================================================");
        gerarPdf.addConteudo("=================== PRODUTOS =====================");

        PdfPTable pdfPTable = new PdfPTable(2);

        try {

            /* dados do pedido */
            pdfPTable.setWidths(new int[]{3, 2});
            pdfPTable.setTotalWidth(250);
            pdfPTable.setLockedWidth(true);
            pdfPTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(createCell("Nome", Element.ALIGN_LEFT, Font.BOLD));
            pdfPTable.addCell(createCell("Valor", Element.ALIGN_LEFT, Font.BOLD));

            for (PedidoItem pedidoItem : pedido.getPedidoItemList()) {

                pdfPTable.addCell(createCell(String.valueOf(pedidoItem.getNome()), Element.ALIGN_LEFT, Font.NORMAL));
                pdfPTable.addCell(createCell(String.valueOf(pedidoItem.getValor()), Element.ALIGN_LEFT, Font.NORMAL));

            }

        } catch (DocumentException ex) {

        }

        gerarPdf.addConteudo(pdfPTable);
        gerarPdf.addConteudo("");
        gerarPdf.addConteudo("--------------------------------------------------");
        gerarPdf.addConteudo("Total: " + ConverteNumeroParaMoedaBr.converter(String.valueOf(pedido.getValor())));
        gerarPdf.addConteudo("==================================================");

        /* rodapé */
        gerarPdf.addConteudo("");
        gerarPdf.addConteudo("--------------------------------------------------");

        if (gerarPdf.gerar()) {

            imprimirPDF();

        }

    }

    public boolean excluir() {

        try {

            return new File(pathDestino + ".pdf").delete();

        } catch (Exception ex) {

            return false;

        }

    }

}

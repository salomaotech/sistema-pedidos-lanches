package com.salomaotech.autoatendimento.model.dto;

import com.salomaotech.autoatendimento.model.pagamento.PixGeraCobranca;
import com.salomaotech.autoatendimento.model.pagamento.PixGeraQRCode;
import com.salomaotech.autoatendimento.services.CarrinhoService;
import com.salomaotech.autoatendimento.services.UploadService;
import jakarta.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.Map;

public class PagamentoDTO {

    private final CarrinhoService carrinhoService;
    private String qrCode;
    private String txid;

    public PagamentoDTO(HttpSession session) {
        carrinhoService = new CarrinhoService(session);
        gerarOrdemDePagamento();
    }

    private void gerarOrdemDePagamento() {

        String cpf = "94271564656";
        String nome = "Gorbadoc Oldbuck";
        BigDecimal valorTotal = carrinhoService.valorTotal();
        String chave = "2dc162d6-8129-4a90-bbaa-6ce24ce3e913";

        /* dados da cobrança */
        Map cobrancaMap = (Map) new PixGeraCobranca(cpf, nome, valorTotal, chave).gerar();

        if (!cobrancaMap.isEmpty()) {

            Map locMap = (Map) cobrancaMap.get("loc");
            txid = (String) cobrancaMap.get("txid");

            PixGeraQRCode pixGeraQRCode = new PixGeraQRCode((int) locMap.get("id"));
            ByteArrayInputStream byteArrayInputStream = pixGeraQRCode.gerar();

            if (byteArrayInputStream != null) {

                qrCode = UploadService.storage(byteArrayInputStream, "qrcode.png");

            }

        }

    }

    public String getQrCode() {
        return qrCode;
    }

    public String getTxid() {
        return txid;
    }

}

package com.salomaotech.autoatendimento.model.pagamento;

import br.com.gerencianet.gnsdk.Gerencianet;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import static javax.xml.bind.DatatypeConverter.parseBase64Binary;

public class PixGeraQRCode {

    private final int idCobranca;

    /**
     * Gera um QRCode de uma cobrança
     *
     * @param idCobranca ID da cobrança
     */
    public PixGeraQRCode(int idCobranca) {
        this.idCobranca = idCobranca;
    }

    /**
     * Retorna os Bytes do QRCode que pode ser salvo em disco posteriormente
     *
     * @return ByteArrayInputStream do QRCode
     */
    public ByteArrayInputStream gerar() {

        ByteArrayInputStream byteArrayInputStream = null;
        Credenciais credenciais = new Credenciais();

        /* dados da credêncial */
        HashMap<String, Object> options = new HashMap<>();
        options.put("client_id", credenciais.getClientId());
        options.put("client_secret", credenciais.getClientSecret());
        options.put("certificate", credenciais.getCertificate());
        options.put("sandbox", credenciais.isSandbox());

        /* parâmetros da cobrança */
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(idCobranca));

        try {

            Gerencianet gn = new Gerencianet(options);
            Map<String, Object> response = gn.call("pixGenerateQRCode", params, new HashMap<>());

            if (!response.isEmpty()) {

                byteArrayInputStream = new ByteArrayInputStream(parseBase64Binary(((String) response.get("imagemQrcode")).split(",")[1]));

            }

        } catch (Exception e) {

        }

        return byteArrayInputStream;

    }

}

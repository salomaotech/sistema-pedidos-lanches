package com.salomaotech.autoatendimento.model.pagamento;

import br.com.gerencianet.gnsdk.Gerencianet;
import java.util.HashMap;
import java.util.Map;

public class PixCriaChave {

    private String erro = "";

    /**
     * Se a resposta for null possívelmente já atingiu o limite de chaves PIX
     *
     * @return Chave PIX aleatória
     */
    public String criar() {

        Credenciais credenciais = new Credenciais();
        String chave = null;

        /* dados da credêncial */
        HashMap<String, Object> options = new HashMap<>();
        options.put("client_id", credenciais.getClientId());
        options.put("client_secret", credenciais.getClientSecret());
        options.put("certificate", credenciais.getCertificate());
        options.put("sandbox", credenciais.isSandbox());

        try {

            Gerencianet gn = new Gerencianet(options);
            Map<String, Object> response = gn.call("pixCreateEvp", new HashMap<>(), new HashMap<>());

            /* valida a resposta */
            if (!response.isEmpty()) {

                chave = String.valueOf(response.get("chave"));

            }

        } catch (Exception e) {

            erro = e.getMessage();

        }

        return chave;

    }

    /**
     *
     * @return Mensagem informando porque não criou a chave PIX
     */
    public String getErro() {
        return erro;
    }

}

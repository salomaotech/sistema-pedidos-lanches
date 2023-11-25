package com.salomaotech.autoatendimento.model.pagamento;

import br.com.gerencianet.gnsdk.Gerencianet;
import java.util.HashMap;
import java.util.Map;

public class PixConsultaPagamento {

    public static boolean consultar(String txid) {

        boolean isPago;
        Credenciais credenciais = new Credenciais();

        /* dados da credêncial */
        HashMap<String, Object> options = new HashMap<>();
        options.put("client_id", credenciais.getClientId());
        options.put("client_secret", credenciais.getClientSecret());
        options.put("certificate", credenciais.getCertificate());
        options.put("sandbox", credenciais.isSandbox());

        HashMap<String, String> params = new HashMap<>();
        params.put("txid", txid);

        try {

            Gerencianet gn = new Gerencianet(options);
            Map<String, Object> response = gn.call("pixDetailCharge", params, new HashMap<>());
            isPago = response.get("status").equals("CONCLUIDA");

        } catch (Exception e) {

            isPago = false;

        }

        return isPago;

    }

}

package com.salomaotech.autoatendimento.model.pagamento;

import br.com.gerencianet.gnsdk.Gerencianet;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class PixGeraCobranca {

    private final String cpf;
    private final String nome;
    private final BigDecimal valor;
    private final String chavePix;
    private String erro = "";

    /**
     * Gera uma cobrança PIX
     *
     * @param cpf CPF do cliente
     * @param nome Nome do cliente
     * @param valor Valor total
     * @param chavePix Chave PIX
     */
    public PixGeraCobranca(String cpf, String nome, BigDecimal valor, String chavePix) {
        this.cpf = cpf;
        this.nome = nome;
        this.valor = valor;
        this.chavePix = chavePix;
    }

    public Map<String, Object> gerar() {

        Credenciais credenciais = new Credenciais();

        /* dados da credêncial */
        HashMap<String, Object> options = new HashMap<>();
        options.put("client_id", credenciais.getClientId());
        options.put("client_secret", credenciais.getClientSecret());
        options.put("certificate", credenciais.getCertificate());
        options.put("sandbox", credenciais.isSandbox());

        Map<String, Object> body = new HashMap<>();
        body.put("calendario", new JSONObject().put("expiracao", 3600));
        body.put("devedor", new JSONObject().put("cpf", cpf).put("nome", nome));
        body.put("valor", new JSONObject().put("original", valor.toString()));
        body.put("chave", chavePix);

        try {

            Gerencianet gn = new Gerencianet(options);
            Map<String, Object> response = gn.call("pixCreateImmediateCharge", new HashMap<>(), body);
            return response;

        } catch (Exception e) {

            erro = e.getMessage();
            return new HashMap();

        }

    }

    /**
     * Retorna o erro gerado ao tentar gerar uma cobrança via PIX
     *
     * @return Mensagem de erro
     */
    public String getErro() {
        return erro;
    }

}

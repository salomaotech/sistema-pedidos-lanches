package com.salomaotech.autoatendimento.model.pagamento;

import java.io.IOException;
import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Credenciais {

    private final String clientId;
    private final String clientSecret;
    private final String certificate;
    private final boolean sandbox;
    private final boolean debug;

    public Credenciais() {

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream credenciaisFile = classLoader.getResourceAsStream("credenciais.json");
        JSONTokener tokener = new JSONTokener(credenciaisFile);
        JSONObject credenciais = new JSONObject(tokener);

        try {

            credenciaisFile.close();

        } catch (IOException e) {

        }

        this.clientId = credenciais.getString("client_id");
        this.clientSecret = credenciais.getString("client_secret");
        this.certificate = credenciais.getString("certificate");
        this.sandbox = credenciais.getBoolean("sandbox");
        this.debug = credenciais.getBoolean("debug");

    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCertificate() {
        return certificate;
    }

    public boolean isSandbox() {
        return sandbox;
    }

    public boolean isDebug() {
        return debug;
    }

}

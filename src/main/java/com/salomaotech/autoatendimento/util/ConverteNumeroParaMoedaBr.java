package com.salomaotech.autoatendimento.util;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class ConverteNumeroParaMoedaBr {

    /**
     * Converte um número para moeda brasileira no formato R$ 00,00
     *
     * @param numero Número a ser convertido
     * @return String no formato R$ 00,00
     */
    public static String converter(String numero) {

        try {

            BigDecimal valor = BigDecimais.formatarParaBigDecimal(numero);
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            return nf.format(valor);

        } catch (Exception ex) {

            return "";

        }

    }

}

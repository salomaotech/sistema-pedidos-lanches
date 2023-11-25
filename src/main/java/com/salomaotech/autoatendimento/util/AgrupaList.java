package com.salomaotech.autoatendimento.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AgrupaList {

    public static Map<String, Integer> agrupar(List<String> itens) {

        Map<String, Integer> itensMap = new LinkedHashMap<>();

        for (String item : itens) {

            if (itensMap.containsKey(item)) {

                itensMap.put(item, itensMap.get(item) + 1);

            } else {

                itensMap.put(item, 1);

            }

        }

        return itensMap;

    }

}

package com.theonova;

import lombok.Getter;

import java.text.Normalizer;
import java.util.Locale;

public enum CountryIso {
    ECUADOR("ECUADOR", "EC"),
    COLOMBIA("COLOMBIA", "CO"),
    PERU("PERU", "PE");

    private final String name;
    @Getter
    private final String iso;

    CountryIso(String name, String iso) {
        this.name = name;
        this.iso = iso;
    }

    public static String fromName(String name) {
        if (name == null) return null;
        String normalized = normalize(name);
        for (CountryIso countryIso : CountryIso.values()) {
            if (countryIso.name.equals(normalized)) {
                return countryIso.iso;
            }
        }
        return null;
    }

    static String normalize(String input) {
        String noAccents = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return noAccents.trim().toUpperCase(Locale.ROOT);
    }
}

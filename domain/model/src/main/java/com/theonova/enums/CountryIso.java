package com.theonova.enums;

import lombok.Getter;

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
        if (name == null) {
            return null;
        }
        for (CountryIso countryIso : CountryIso.values()) {
            if (countryIso.name.equalsIgnoreCase(name)) {
                return countryIso.iso;
            }
        }
        return null;
    }
}

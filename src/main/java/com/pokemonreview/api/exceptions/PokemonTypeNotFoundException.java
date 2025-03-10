package com.pokemonreview.api.exceptions;

public class PokemonTypeNotFoundException extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public PokemonTypeNotFoundException(String message) {
        super(message);
    }
}

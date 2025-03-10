package com.pokemonreview.api.exceptions;

public class ReviewWithMoreThenFiveStart extends RuntimeException {
    private static final long serialVerisionUID = 1;

    public ReviewWithMoreThenFiveStart() {
        super("You cant only make review between 1 and 5 starts");
    }
}

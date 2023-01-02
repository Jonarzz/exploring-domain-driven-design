package io.github.jonarzz.ddd.tictactoe.model;

record Diagonal(Type type) implements GridVector {

    enum Type {
        MAIN, ANTIDIAGONAL
    }

    @Override
    public String humanReadable() {
        return switch (type) {
            case MAIN -> "main diagonal";
            case ANTIDIAGONAL -> "antidiagonal";
        };
    }
}

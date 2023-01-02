package io.github.jonarzz.ddd.tictactoe.model;

record Column(int index) implements GridVector {

    @Override
    public String humanReadable() {
        return "column " + index;
    }
}

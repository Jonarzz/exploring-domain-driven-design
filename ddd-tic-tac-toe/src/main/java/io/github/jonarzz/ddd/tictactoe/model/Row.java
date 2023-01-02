package io.github.jonarzz.ddd.tictactoe.model;

record Row(int index) implements GridVector {

    @Override
    public String humanReadable() {
        return "row " + index;
    }
}

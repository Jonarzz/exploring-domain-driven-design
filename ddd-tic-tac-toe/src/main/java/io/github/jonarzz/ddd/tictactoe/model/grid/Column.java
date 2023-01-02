package io.github.jonarzz.ddd.tictactoe.model.grid;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
record Column(int index) implements GridVector {

    @Override
    public String humanReadable() {
        return "column " + (index + 1);
    }
}

package io.github.jonarzz.ddd.tictactoe.model.grid;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
record Row(int index) implements GridVector {

    @Override
    public String humanReadable() {
        return "row " + (index + 1);
    }
}

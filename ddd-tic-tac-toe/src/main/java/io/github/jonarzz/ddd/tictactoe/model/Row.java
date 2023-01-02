package io.github.jonarzz.ddd.tictactoe.model;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
record Row(int index) implements GridVector {

    @Override
    public String humanReadable() {
        return "row " + index;
    }
}

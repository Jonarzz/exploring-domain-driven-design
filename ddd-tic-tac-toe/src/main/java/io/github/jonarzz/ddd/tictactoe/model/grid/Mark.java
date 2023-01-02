package io.github.jonarzz.ddd.tictactoe.model.grid;

import static java.lang.String.*;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
record Mark(char value) {

    @Override
    public String toString() {
        return valueOf(value);
    }
}

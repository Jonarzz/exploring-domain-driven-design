package io.github.jonarzz.ddd.tictactoe.model;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@Entity
record GridSquare(Position position, Mark mark) {

    static GridSquare empty(Position position) {
        return new GridSquare(position, null);
    }

    boolean isEmpty() {
        return mark == null;
    }
}

package io.github.jonarzz.ddd.tictactoe.model.grid;

import static lombok.AccessLevel.*;

import lombok.*;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
public record Position(int row, int column) {

    @AllArgsConstructor(access = PRIVATE)
    public enum BasicDirection {
        UPPER_LEFT(0, 0),  UPPER_CENTER(0, 1), UPPER_RIGHT(0, 2),
        CENTER_LEFT(1, 0), CENTER(1, 1),       CENTER_RIGHT(1, 2),
        LOWER_LEFT(2, 0),  LOWER_CENTER(2, 1), LOWER_RIGHT(2, 2);

        final int row;
        final int column;
    }

    public static Position from(BasicDirection direction) {
        return new Position(direction.row, direction.column);
    }
}

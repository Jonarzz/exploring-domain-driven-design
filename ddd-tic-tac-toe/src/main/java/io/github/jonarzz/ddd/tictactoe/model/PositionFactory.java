package io.github.jonarzz.ddd.tictactoe.model;

import static lombok.AccessLevel.*;

import lombok.*;

public class PositionFactory {

    @AllArgsConstructor(access = PRIVATE)
    public enum BasicSquarePosition {
        UPPER_LEFT(0, 0),  UPPER_CENTER(0, 1), UPPER_RIGHT(0, 2),
        CENTER_LEFT(1, 0), CENTER(1, 1),       CENTER_RIGHT(1, 2),
        LOWER_LEFT(2, 0),  LOWER_CENTER(2, 1), LOWER_RIGHT(2, 2);

        final int row;
        final int column;
    }

    public Position basic(BasicSquarePosition squarePosition) {
        return withCoordinates(squarePosition.row, squarePosition.column);
    }

    public Position withCoordinates(int row, int column) {
        return new Position(row, column);
    }
}

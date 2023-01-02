package io.github.jonarzz.ddd.tictactoe.model.grid;

import static java.util.Arrays.*;

import java.util.*;

class WinningColumnSpecification implements WinningVectorSpecification {

    @Override
    public Optional<GridVector> calculate(Mark[][] gridMarks) {
        var gridSize = gridMarks.length;
        for (int col = 0; col < gridSize; col++) {
            var first = gridMarks[0][col];
            if (first != null) {
                var column = col;
                if (stream(gridMarks).map(marksRow -> marksRow[column])
                                     .allMatch(first::equals)) {
                    return Optional.of(new Column(column));
                }
            }
        }
        return Optional.empty();
    }
}

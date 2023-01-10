package io.github.jonarzz.ddd.tictactoe.model.grid;

import static java.util.Arrays.*;

import java.util.*;

class WinningColumnPolicy implements WinningVectorPolicy {

    @Override
    public Optional<GridVector> calculate(Mark[][] gridMarks) {
        var gridSize = gridMarks.length;
        for (int column = 0; column < gridSize; column++) {
            var first = gridMarks[0][column];
            if (first != null) {
                var col = column;
                if (stream(gridMarks).map(marksRow -> marksRow[col])
                                     .allMatch(first::equals)) {
                    return Optional.of(new Column(column));
                }
            }
        }
        return Optional.empty();
    }
}

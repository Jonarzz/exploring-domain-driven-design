package io.github.jonarzz.ddd.tictactoe.model.grid;

import static java.util.Arrays.*;

import java.util.*;

class WinningRowSpecification implements WinningVectorSpecification {

    @Override
    public Optional<GridVector> calculate(Mark[][] gridMarks) {
        var gridSize = gridMarks.length;
        for (int row = 0; row < gridSize; row++) {
            var rowMarks = gridMarks[row];
            var first = rowMarks[0];
            if (first != null && stream(rowMarks).allMatch(first::equals)) {
                return Optional.of(new Row(row));
            }
        }
        return Optional.empty();
    }
}

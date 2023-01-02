package io.github.jonarzz.ddd.tictactoe.model.grid;

import static java.lang.System.*;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

import java.util.*;
import java.util.stream.*;

import io.github.jonarzz.ddd.tictactoe.infrastructure.result.*;

@AggregateRoot
public class Grid {

    final Mark[][] gridMarks;

    int numberOfPlacedMarks;

    Grid(Mark[][] gridMarks) {
        this.gridMarks = gridMarks;
    }

    public Validated<Void> place(Position position, char mark) {
        if (isOutOfBounds(position)) {
            return Validated.invalid("Square out of grid bounds");
        }
        if (isTaken(position)) {
            return Validated.invalid("Square filled in already");
        }
        add(position, new Mark(mark));
        return Validated.valid(null);
    }

    public boolean isFull() {
        var gridSize = gridMarks.length;
        return numberOfPlacedMarks == gridSize * gridSize;
    }

    public Optional<GridVector> getWinningVector() {
        return Stream.of(new WinningRowSpecification(),
                         new WinningColumnSpecification(),
                         new WinningDiagonalSpecification())
                     .map(spec -> spec.calculate(gridMarks))
                     .flatMap(Optional::stream)
                     .findFirst();
    }

    private boolean isOutOfBounds(Position position) {
        var row = position.row();
        var column = position.column();
        return row < 0 || row >= gridMarks.length
               || column < 0 || column >= gridMarks.length;
    }

    private boolean isTaken(Position position) {
        var row = position.row();
        var column = position.column();
        return gridMarks[row][column] != null;
    }

    private void add(Position position, Mark mark) {
        var row = position.row();
        var column = position.column();
        gridMarks[row][column] = mark;
        ++numberOfPlacedMarks;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        var gridSize = gridMarks.length;
        for (var marksRow : gridMarks) {
            for (int column = 0; column < gridSize; column++) {
                var mark = marksRow[column];
                builder.append(" ");
                if (mark == null) {
                    builder.append(" ");
                } else {
                    builder.append(mark);
                }
                builder.append(" ");
                if (column != gridSize - 1) {
                    builder.append("|");
                }
            }
            builder.append(lineSeparator());
        }
        return builder.toString();
    }
}

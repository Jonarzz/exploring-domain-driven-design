package io.github.jonarzz.ddd.tictactoe.model;

import static io.github.jonarzz.ddd.tictactoe.model.Diagonal.Type.*;
import static io.github.jonarzz.ddd.tictactoe.model.Validated.*;
import static java.lang.System.*;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

import java.util.*;

@AggregateRoot
class Grid {

    final int gridSize;
    final GridSquare[][] gridSquares;

    int numberOfPlacedMarks;

    Grid(int gridSize) {
        this.gridSize = gridSize;
        gridSquares = new GridSquare[gridSize][gridSize];
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                var position = new Position(row, column);
                gridSquares[row][column] = GridSquare.empty(position);
            }
        }
    }

    Validated<GridSquare> place(Position position, Mark mark) {
        if (isOutOfBounds(position)) {
            return invalid("Square out of grid bounds");
        }
        if (isTaken(position)) {
            return invalid("Square filled in already");
        }
        var gridSquare = new GridSquare(position, mark);
        add(gridSquare);
        return valid(gridSquare);
    }

    boolean isFull() {
        return numberOfPlacedMarks == gridSize * gridSize;
    }

    Optional<GridVector> getWinningVector() {
        return rowOrDiagonalWinningVector()
                .or(this::columnWinningVector);
    }

    private Optional<GridVector> rowOrDiagonalWinningVector() {
        var diagonalAllMatch = true;
        var antidiagonalAllMatch = true;
        for (int row = 0; row < gridSize; row++) {
            var rowFirstSquare = gridSquares[row][0];
            var rowAllMatch = !rowFirstSquare.isEmpty();
            for (int column = 0; column < gridSize; column++) {
                rowAllMatch &= marksEqual(rowFirstSquare, gridSquares[row][column]);
                if (row == column) {
                    diagonalAllMatch &= marksEqual(gridSquares[0][0],
                                                   gridSquares[row][column]);
                    antidiagonalAllMatch &= marksEqual(gridSquares[0][gridSize - 1],
                                                       gridSquares[gridSize - 1 - row][column]);
                }
            }
            if (rowAllMatch) {
                return Optional.of(new Row(row));
            }
        }
        if (diagonalAllMatch) {
            return Optional.of(new Diagonal(MAIN));
        }
        if (antidiagonalAllMatch) {
            return Optional.of(new Diagonal(ANTIDIAGONAL));
        }
        return Optional.empty();
    }

    private Optional<GridVector> columnWinningVector() {
        for (int column = 0; column < gridSize; column++) {
            var firstMark = gridSquares[0][column];
            if (!firstMark.isEmpty()) {
                var columnAllMatch = true;
                for (int row = 0; row < gridSize; row++) {
                    columnAllMatch &= marksEqual(firstMark, gridSquares[row][column]);
                }
                if (columnAllMatch) {
                    return Optional.of(new Column(column));
                }
            }
        }
        return Optional.empty();
    }

    private boolean isOutOfBounds(Position position) {
        var row = position.row();
        var column = position.column();
        return row < 0 || row >= gridSize
               || column < 0 || column >= gridSize;
    }

    private boolean isTaken(Position position) {
        var row = position.row();
        var column = position.column();
        return !gridSquares[row][column].isEmpty();
    }

    private void add(GridSquare square) {
        var position = square.position();
        var row = position.row();
        var column = position.column();
        gridSquares[row][column] = square;
        ++numberOfPlacedMarks;
    }

    private static boolean marksEqual(GridSquare first, GridSquare second) {
        if (first.isEmpty() || second.isEmpty()) {
            return false;
        }
        return first.mark()
                    .equals(second.mark());
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                var square = gridSquares[row][column];
                builder.append(" ");
                if (square.isEmpty()) {
                    builder.append(" ");
                } else {
                    builder.append(square.mark());
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

package io.github.jonarzz.ddd.tictactoe.model.grid;

import static io.github.jonarzz.ddd.tictactoe.model.grid.Diagonal.Type.*;

import java.util.*;

class WinningDiagonalSpecification implements WinningVectorSpecification {

    @Override
    public Optional<GridVector> calculate(Mark[][] gridMarks) {
        var gridSize = gridMarks.length;
        var firstDiagonal = gridMarks[0][0];
        var diagonalAllMatch = firstDiagonal != null;
        var firstAntidiagonal = gridMarks[gridSize - 1][0];
        var antidiagonalAllMatch = firstAntidiagonal != null;
        for (int i = 1; i < gridSize; i++) {
            diagonalAllMatch &= marksEqual(firstDiagonal, gridMarks[i][i]);
            antidiagonalAllMatch &= marksEqual(firstAntidiagonal, gridMarks[gridSize - 1 - i][i]);
        }
        if (diagonalAllMatch) {
            return Optional.of(new Diagonal(MAIN));
        }
        if (antidiagonalAllMatch) {
            return Optional.of(new Diagonal(ANTIDIAGONAL));
        }
        return Optional.empty();
    }

    private static boolean marksEqual(Mark first, Mark second) {
        if (first == null) {
            return false;
        }
        return first.equals(second);
    }
}

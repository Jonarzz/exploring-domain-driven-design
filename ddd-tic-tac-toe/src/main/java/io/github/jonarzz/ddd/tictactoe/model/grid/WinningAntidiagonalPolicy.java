package io.github.jonarzz.ddd.tictactoe.model.grid;

import static io.github.jonarzz.ddd.tictactoe.model.grid.Diagonal.Type.*;

class WinningAntidiagonalPolicy extends WinningDiagonalPolicy {

    WinningAntidiagonalPolicy() {
        super(ANTIDIAGONAL);
    }

    @Override
    Mark atIndex(Mark[][] gridMarks, int index) {
        return gridMarks[gridMarks.length - 1 - index][index];
    }
}

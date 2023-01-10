package io.github.jonarzz.ddd.tictactoe.model.grid;

import static io.github.jonarzz.ddd.tictactoe.model.grid.Diagonal.Type.*;

class WinningMainDiagonalPolicy extends WinningDiagonalPolicy {

    WinningMainDiagonalPolicy() {
        super(MAIN);
    }

    @Override
    Mark atIndex(Mark[][] gridMarks, int index) {
        return gridMarks[index][index];
    }
}

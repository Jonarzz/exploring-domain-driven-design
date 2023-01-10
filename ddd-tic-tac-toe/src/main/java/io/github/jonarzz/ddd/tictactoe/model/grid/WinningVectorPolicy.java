package io.github.jonarzz.ddd.tictactoe.model.grid;

import java.util.*;

interface WinningVectorPolicy {

    Optional<GridVector> calculate(Mark[][] gridMarks);

}

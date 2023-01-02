package io.github.jonarzz.ddd.tictactoe.model.grid;

import java.util.*;

interface WinningVectorSpecification {

    Optional<GridVector> calculate(Mark[][] gridMarks);

}

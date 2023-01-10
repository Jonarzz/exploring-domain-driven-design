package io.github.jonarzz.ddd.tictactoe.model.grid;

import lombok.*;

import java.util.*;

@RequiredArgsConstructor
abstract class WinningDiagonalPolicy implements WinningVectorPolicy {

    final Diagonal.Type type;

    @Override
    public final Optional<GridVector> calculate(Mark[][] gridMarks) {
        var first = atIndex(gridMarks, 0);
        if (first == null) {
            return Optional.empty();
        }
        for (int i = 1; i < gridMarks.length; i++) {
            if (!first.equals(atIndex(gridMarks, i))) {
                return Optional.empty();
            }
        }
        return Optional.of(new Diagonal(type));
    }

    abstract Mark atIndex(Mark[][] gridMarks, int index);
}

package io.github.jonarzz.ddd.tictactoe.model.game;

import lombok.*;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

import io.github.jonarzz.ddd.tictactoe.model.grid.*;

@ToString
@RequiredArgsConstructor
@ValueObject
class WinningMove extends ValidMove {

    final GridVector winningVector;

    @Override
    public boolean endsTheGame() {
        return true;
    }

    @Override
    public String message() {
        return "winning vector: " + winningVector.humanReadable();
    }
}

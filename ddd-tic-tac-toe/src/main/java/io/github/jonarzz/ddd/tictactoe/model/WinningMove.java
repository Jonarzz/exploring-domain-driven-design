package io.github.jonarzz.ddd.tictactoe.model;

import lombok.*;

@ToString
@RequiredArgsConstructor
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

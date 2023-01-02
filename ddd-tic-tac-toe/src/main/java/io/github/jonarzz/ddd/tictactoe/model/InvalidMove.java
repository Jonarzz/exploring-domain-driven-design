package io.github.jonarzz.ddd.tictactoe.model;

import lombok.*;

@ToString
@RequiredArgsConstructor
class InvalidMove implements MoveResult {

    @NonNull
    String reason;

    @Override
    public boolean valid() {
        return false;
    }

    @Override
    public String message() {
        return reason;
    }
}

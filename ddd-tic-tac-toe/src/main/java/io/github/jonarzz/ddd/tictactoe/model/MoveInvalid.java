package io.github.jonarzz.ddd.tictactoe.model;

import lombok.*;

import java.util.*;

@ToString
@RequiredArgsConstructor
class MoveInvalid implements MoveResult {

    @NonNull
    String reason;

    @Override
    public boolean valid() {
        return false;
    }

    @Override
    public Optional<String> message() {
        return Optional.of(reason);
    }
}

package io.github.jonarzz.ddd.tictactoe.model;

import lombok.*;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ToString
@RequiredArgsConstructor
@ValueObject
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

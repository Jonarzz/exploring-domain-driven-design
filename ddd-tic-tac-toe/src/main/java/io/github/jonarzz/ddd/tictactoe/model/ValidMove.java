package io.github.jonarzz.ddd.tictactoe.model;

import lombok.*;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ToString
@ValueObject
class ValidMove implements MoveResult {

    @Override
    public boolean valid() {
        return true;
    }

}

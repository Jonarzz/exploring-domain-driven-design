package io.github.jonarzz.ddd.tictactoe.model.game;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
public interface MoveResult {

    boolean valid();

    default boolean endsTheGame() {
        return false;
    }

    default String message() {
        return null;
    }

}

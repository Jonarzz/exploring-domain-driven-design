package io.github.jonarzz.ddd.tictactoe.model;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

import java.util.*;

@ValueObject
public interface MoveResult {

    boolean valid();

    default Optional<String> message() {
        return Optional.empty();
    }

}

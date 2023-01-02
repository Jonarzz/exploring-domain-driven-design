package io.github.jonarzz.ddd.tictactoe.model;

import static java.lang.String.*;

import lombok.*;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
@EqualsAndHashCode
class Mark {

    final char value;

    Mark(char value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return valueOf(value);
    }
}

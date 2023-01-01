package io.github.jonarzz.ddd.tictactoe.model;

import lombok.*;
import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
@AllArgsConstructor
class Position {

    int row;
    int column;

}

package io.github.jonarzz.ddd.tictactoe.model;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
public record Position(int row, int column) {

}

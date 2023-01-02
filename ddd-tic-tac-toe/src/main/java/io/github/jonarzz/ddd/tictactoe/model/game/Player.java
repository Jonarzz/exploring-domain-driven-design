package io.github.jonarzz.ddd.tictactoe.model.game;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
record Player(String name, char mark) {

}

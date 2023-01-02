package io.github.jonarzz.ddd.tictactoe.model;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
record Player(String name, Mark mark) {

}

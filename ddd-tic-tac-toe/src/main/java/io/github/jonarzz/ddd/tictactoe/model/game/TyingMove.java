package io.github.jonarzz.ddd.tictactoe.model.game;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@ValueObject
class TyingMove extends ValidMove {

    @Override
    public boolean endsTheGame() {
        return true;
    }

    @Override
    public String message() {
        return "tie";
    }
}

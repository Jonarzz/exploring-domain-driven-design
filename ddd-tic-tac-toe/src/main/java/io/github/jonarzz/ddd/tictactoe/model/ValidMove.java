package io.github.jonarzz.ddd.tictactoe.model;

import lombok.*;

@ToString
class ValidMove implements MoveResult {

    @Override
    public boolean valid() {
        return true;
    }

}

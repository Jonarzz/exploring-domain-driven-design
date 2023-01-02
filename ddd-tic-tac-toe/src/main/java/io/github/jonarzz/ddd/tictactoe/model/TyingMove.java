package io.github.jonarzz.ddd.tictactoe.model;

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

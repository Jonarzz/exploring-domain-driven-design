package io.github.jonarzz.ddd.tictactoe.model.game;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

import java.util.*;

import io.github.jonarzz.ddd.tictactoe.model.grid.*;

@AggregateRoot
public class Game {

    final Grid grid;
    final List<Player> players;

    int currentPlayerIndex;

    boolean tied = true;

    Game(Grid grid, List<Player> players) {
        this.grid = grid;
        this.players = players;
        currentPlayerIndex = 0;
    }

    public static GameBuilder withDefaultGridSize() {
        return withGridSize(3);
    }

    public static GameBuilder withGridSize(int gridSize) {
        return new GameBuilder(gridSize);
    }

    public MoveResult placeMarkOn(Position position) {
        var mark = players.get(currentPlayerIndex)
                          .mark();
        var validatedSquare = grid.place(position, mark);
        if (validatedSquare.isNotValid()) {
            return new InvalidMove(validatedSquare.invalidityReason());
        }
        var winningVector = grid.getWinningVector();
        if (winningVector.isPresent()) {
            tied = false;
            return new WinningMove(winningVector.get());
        }
        if (grid.isFull()) {
            return new TyingMove();
        }
        setNextPlayerIndex();
        return new ValidMove();
    }

    public boolean tied() {
        return tied;
    }

    public String currentPlayerName() {
        return players.get(currentPlayerIndex)
                      .name();
    }

    public String view() {
        return grid.toString();
    }

    private void setNextPlayerIndex() {
        if (currentPlayerIndex == players.size() - 1) {
            currentPlayerIndex = 0;
        } else {
            ++currentPlayerIndex;
        }
    }

}

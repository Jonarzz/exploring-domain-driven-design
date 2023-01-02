package io.github.jonarzz.ddd.tictactoe.model;

import java.util.*;

public class Game {

    final Grid grid;

    final List<Player> players;
    int currentPlayerIndex;

    boolean over;
    boolean tied = true;

    private Game(int gridSize, List<Player> players) {
        var minGridSize = 2;
        if (gridSize < minGridSize) {
            throw new IllegalStateException("Grid size should be at least " + minGridSize);
        }
        var minNumberOfPlayers = 2;
        if (players.size() < minNumberOfPlayers) {
            throw new IllegalStateException("At least " + minNumberOfPlayers
                                            + " players are required to play the game");
        }
        grid = new Grid(gridSize);
        this.players = List.copyOf(players);
        currentPlayerIndex = 0;
    }

    public static Builder withDefaultGridSize() {
        return new Builder(3);
    }

    public static Builder withGridSize(int gridSize) {
        return new Builder(gridSize);
    }

    public MoveResult placeMarkOn(Position position) {
        var currentPlayer = players.get(currentPlayerIndex);
        var mark = currentPlayer.mark();
        var validatedSquare = grid.place(position, mark);
        if (!validatedSquare.valid()) {
            return new InvalidMove(validatedSquare.invalidityReason());
        }
        var winningVector = grid.getWinningVector();
        if (winningVector.isPresent()) {
            over = true;
            tied = false;
            return new WinningMove(winningVector.get());
        }
        if (grid.isFull()) {
            over = true;
            return new TyingMove();
        }
        setNextPlayerIndex();
        return new ValidMove();
    }

    public boolean over() {
        return over;
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

    public static class Builder {

        final int gridSize;
        final List<Player> players = new ArrayList<>();

        private Builder(int gridSize) {
            this.gridSize = gridSize;
        }

        public Builder addPlayer(String name, char mark) {
            players.add(new Player(name, new Mark(mark)));
            return this;
        }

        public Game build() {
            return new Game(gridSize, players);
        }
    }
}

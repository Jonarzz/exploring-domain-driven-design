package io.github.jonarzz.ddd.tictactoe.model;

import java.util.*;

public class Game {

    final int gridSize;
    final List<Player> players;

    final Boolean[][] grid;
    boolean currentPlayer;
    int numberOfSquaresFilled;

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
        this.gridSize = gridSize;
        this.players = List.copyOf(players);
        grid = new Boolean[gridSize][gridSize];
    }

    public static Builder withDefaultGridSize() {
        return new Builder(3);
    }

    public static Builder withGridSize(int gridSize) {
        return new Builder(gridSize);
    }

    public MoveResult placeMarkOn(Position position) {
        if (isOutOfBounds(position)) {
            return new MoveInvalid("Square out of grid bounds");
        }
        var row = position.row();
        var column = position.column();
        if (grid[row][column] != null) {
            return new MoveInvalid("Square filled in already");
        }
        grid[row][column] = currentPlayer;
        ++numberOfSquaresFilled;
        var fullRow = true;
        var fullColumn = true;
        var fullDiagonal = true;
        var fullOtherDiagonal = true;
        for (int i = 0; i < gridSize; i++) {
            fullRow &= Objects.equals(currentPlayer, grid[row][i]);
            fullColumn &= Objects.equals(currentPlayer, grid[i][column]);
            fullDiagonal &= Objects.equals(currentPlayer, grid[i][i]);
            fullOtherDiagonal &= Objects.equals(currentPlayer, grid[gridSize - 1 - i][i]);
        }
        if (fullRow || fullColumn || fullDiagonal || fullOtherDiagonal) {
            over = true;
            tied = false;
        } else if (numberOfSquaresFilled == gridSize * gridSize) {
            over = true;
        }
        currentPlayer = !currentPlayer;
        return new MoveValid();
    }

    public boolean over() {
        return over;
    }

    public boolean tied() {
        return tied;
    }

    private boolean isOutOfBounds(Position position) {
        var row = position.row();
        var column = position.column();
        return row < 0|| row >= gridSize
               || column < 0 || column >= gridSize;
    }

    public static class Builder {

        final int gridSize;
        final List<Player> players = new ArrayList<>();

        private Builder(int gridSize) {
            this.gridSize = gridSize;
        }

        public Builder addPlayer() {
            players.add(new Player());
            return this;
        }

        public Game build() {
            return new Game(gridSize, players);
        }
    }
}

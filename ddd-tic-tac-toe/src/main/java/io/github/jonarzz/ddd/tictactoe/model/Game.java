package io.github.jonarzz.ddd.tictactoe.model;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

import java.util.*;

@Service
public class Game {

    int gridSize;
    List<Player> players;

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
    }

    public static Builder withDefaultGridSize() {
        return new Builder(3);
    }

    public static Builder withGridSize(int gridSize) {
        return new Builder(gridSize);
    }

    public MoveResult placeMarkOn(Position position) {
        return new MoveValid();
    }

    public boolean over() {
        return false;
    }

    public boolean tied() {
        return true;
    }

    public static class Builder {

        int gridSize;
        List<Player> players = new ArrayList<>();

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

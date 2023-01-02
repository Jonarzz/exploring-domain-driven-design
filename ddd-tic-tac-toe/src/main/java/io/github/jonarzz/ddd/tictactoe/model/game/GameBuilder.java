package io.github.jonarzz.ddd.tictactoe.model.game;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

import java.util.*;

import io.github.jonarzz.ddd.tictactoe.model.grid.*;

@Factory
public class GameBuilder {

    final int gridSize;
    final List<Player> players = new ArrayList<>();

    GameBuilder(int gridSize) {
        this.gridSize = gridSize;
    }

    public GameBuilder addPlayer(String name, char mark) {
        players.add(new Player(name, mark));
        return this;
    }

    public Game build() {
        var minNumberOfPlayers = 2;
        if (players.size() < minNumberOfPlayers) {
            throw new IllegalStateException("At least " + minNumberOfPlayers
                                            + " players are required to play the game");
        }
        var grid = new GridFactory()
                .create(gridSize);
        return new Game(grid, players);
    }
}

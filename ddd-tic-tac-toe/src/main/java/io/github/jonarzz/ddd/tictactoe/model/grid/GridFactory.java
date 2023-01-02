package io.github.jonarzz.ddd.tictactoe.model.grid;

import org.jqassistant.contrib.plugin.ddd.annotation.DDD.*;

@Factory
public class GridFactory {

    public Grid create(int gridSize) {
        var minGridSize = 2;
        if (gridSize < minGridSize) {
            throw new IllegalStateException("Grid size should be at least " + minGridSize);
        }
        return new Grid(new Mark[gridSize][gridSize]);
    }

}

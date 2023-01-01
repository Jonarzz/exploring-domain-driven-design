package io.github.jonarzz.ddd.tictactoe;

import static io.github.jonarzz.ddd.tictactoe.model.PositionFactory.BasicSquarePosition.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

import io.github.jonarzz.ddd.tictactoe.model.*;
import io.github.jonarzz.ddd.tictactoe.model.PositionFactory.*;

class GameTest {

    @ParameterizedTest(name = "{0}")
    @EnumSource(BasicSquarePosition.class)
    void firstMove(BasicSquarePosition squarePosition) {
        var game = createSimpleGame();
        var position = new PositionFactory()
                .basic(squarePosition);

        var moveResult = game.placeMarkOn(position);

        assertThat(moveResult)
                .returns(true, MoveResult::valid);
        assertThat(game.over())
                .as("Game over")
                .isFalse();
        assertThat(game.tied())
                .as("Game tied")
                .isTrue();
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(BasicSquarePosition.class)
    void twoMarksOnTheSamePosition(BasicSquarePosition squarePosition) {
        var game = createSimpleGame();
        var position = new PositionFactory()
                .basic(squarePosition);
        game.placeMarkOn(position);

        var secondMoveResult = game.placeMarkOn(position);

        assertThat(secondMoveResult)
                .returns(false, MoveResult::valid)
                .extracting(MoveResult::message, OPTIONAL)
                .hasValue("Square filled in already");
        assertThat(game.over())
                .as("Game over")
                .isFalse();
    }

    @ParameterizedTest(name = "position = {0}, {1}")
    @CsvSource({"-1, 0", "0, -1", "-1, -1", "3, 0", "0, 3", "3, 3"})
    void markOutsideOfGridBounds(int row, int column) {
        var game = createSimpleGame();
        var position = new PositionFactory()
                .withCoordinates(row, column);
        game.placeMarkOn(position);

        var secondMoveResult = game.placeMarkOn(position);

        assertThat(secondMoveResult)
                .returns(false, MoveResult::valid)
                .extracting(MoveResult::message, OPTIONAL)
                .hasValue("Square out of grid bounds");
        assertThat(game.over())
                .as("Game over")
                .isFalse();
    }

    @ParameterizedTest(name = "moves = {0}")
    @MethodSource("fullWonGameParams")
    void fullGameEndingWithWin(List<BasicSquarePosition> moves) {
        var game = createSimpleGame();
        var positionFactory = new PositionFactory();

        var results = moves.stream()
                           .map(move -> game.placeMarkOn(positionFactory.basic(move)))
                           .toList();

        assertThat(results)
                .as("All move results should be valid")
                .allSatisfy(MoveResult::valid);
        assertThat(game.over())
                .as("Game over")
                .isTrue();
        assertThat(game.tied())
                .as("Game tied")
                .isFalse();
    }

    static Stream<List<BasicSquarePosition>> fullWonGameParams() {
        return Stream.of(
                List.of(
                        // x | x | o
                        //   |   |
                        //   |   | o
                        UPPER_LEFT, LOWER_RIGHT, UPPER_CENTER, UPPER_RIGHT,
                        // x | x | o
                        //   | o | x
                        // o | x | o
                        CENTER_RIGHT, LOWER_LEFT, LOWER_CENTER, CENTER
                ),
                List.of(
                        //   |   | o
                        //   | x | o
                        // x |   |
                        CENTER, CENTER_RIGHT, LOWER_LEFT, UPPER_RIGHT,
                        // x |   | o
                        //   | x | o
                        // x | o | x
                        LOWER_RIGHT, LOWER_CENTER, UPPER_LEFT
                ),
                List.of(
                        // o | o |
                        // x |   |
                        // x |   |
                        LOWER_LEFT, UPPER_CENTER, CENTER_LEFT, UPPER_LEFT,
                        // o | o | x
                        // x | o |
                        // x | x | o
                        UPPER_RIGHT, CENTER, LOWER_CENTER, LOWER_RIGHT
                ),
                List.of(
                        //   |   |
                        // x | o | x
                        //   | o |
                        CENTER_RIGHT, LOWER_CENTER, CENTER_LEFT, CENTER,
                        // x | x |
                        // x | o | x
                        // o | o | o
                        UPPER_CENTER, LOWER_LEFT, UPPER_LEFT, LOWER_LEFT
                )
        );
    }

    @ParameterizedTest(name = "moves = {0}")
    @MethodSource("fullTiedGameParams")
    void fullGameEndingWithTie(List<BasicSquarePosition> moves) {
        var game = createSimpleGame();
        var positionFactory = new PositionFactory();

        var results = moves.stream()
                           .map(move -> game.placeMarkOn(positionFactory.basic(move)))
                           .toList();

        assertThat(results)
                .as("All move results should be valid")
                .allSatisfy(MoveResult::valid);
        assertThat(game.over())
                .as("Game over")
                .isTrue();
        assertThat(game.tied())
                .as("Game tied")
                .isTrue();
    }

    static Stream<List<BasicSquarePosition>> fullTiedGameParams() {
        return Stream.of(
                List.of(
                        // o |   |
                        // x | o |
                        // x |   |
                        UPPER_LEFT, LOWER_RIGHT, UPPER_CENTER, UPPER_RIGHT,
                        // o | x |
                        // x | o |
                        // x | o | x
                        CENTER_RIGHT, LOWER_LEFT, LOWER_CENTER, CENTER
                ),
                List.of(
                        //   |   | o
                        // x | x | o
                        //   |   |
                        CENTER, UPPER_RIGHT, CENTER_LEFT, CENTER_RIGHT,
                        // o | x | o
                        // x | x | o
                        //   | o | x
                        LOWER_RIGHT, UPPER_LEFT, UPPER_CENTER, LOWER_CENTER
                )
        );
    }

    static Game createSimpleGame() {
        return Game.withDefaultGridSize()
                   .addPlayer()
                   .addPlayer()
                   .build();
    }
}
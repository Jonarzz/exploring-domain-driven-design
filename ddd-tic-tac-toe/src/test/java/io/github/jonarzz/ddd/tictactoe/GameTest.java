package io.github.jonarzz.ddd.tictactoe;

import static io.github.jonarzz.ddd.tictactoe.model.grid.Position.BasicDirection.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;
import java.util.stream.*;

import io.github.jonarzz.ddd.tictactoe.model.game.*;
import io.github.jonarzz.ddd.tictactoe.model.grid.*;

class GameTest {

    @ParameterizedTest(name = "{0}")
    @EnumSource(Position.BasicDirection.class)
    void firstMove(Position.BasicDirection direction) {
        var game = createSimpleGame();
        var position = Position.from(direction);

        var moveResult = game.placeMarkOn(position);

        assertSoftly(softly -> {
            softly.assertThat(moveResult.valid())
                  .as("%s is valid", moveResult)
                  .isTrue();
            softly.assertThat(moveResult.endsTheGame())
                  .as("%s ends the game", moveResult)
                  .isFalse();
        });
    }

    @ParameterizedTest(name = "{0}")
    @EnumSource(Position.BasicDirection.class)
    void twoMarksOnTheSamePosition(Position.BasicDirection direction) {
        var game = createSimpleGame();
        var position = Position.from(direction);
        game.placeMarkOn(position);

        var secondMoveResult = game.placeMarkOn(position);

        assertSoftly(softly -> {
            softly.assertThat(secondMoveResult.valid())
                  .as("%s is valid", secondMoveResult)
                  .isFalse();
            softly.assertThat(secondMoveResult.message())
                  .as("%s message", secondMoveResult)
                  .isEqualTo("Square filled in already");
            softly.assertThat(secondMoveResult.endsTheGame())
                  .as("%s ends the game", secondMoveResult)
                  .isFalse();
        });
    }

    @ParameterizedTest(name = "position = {0}, {1}")
    @CsvSource({"-1, 0", "0, -1", "-1, -1", "3, 0", "0, 3", "3, 3"})
    void markOutsideOfGridBounds(int row, int column) {
        var game = createSimpleGame();
        var position = new Position(row, column);
        game.placeMarkOn(position);

        var secondMoveResult = game.placeMarkOn(position);

        assertSoftly(softly -> {
            softly.assertThat(secondMoveResult.valid())
                  .as("%s is valid", secondMoveResult)
                  .isFalse();
            softly.assertThat(secondMoveResult.message())
                  .as("%s message", secondMoveResult)
                  .isEqualTo("Square out of grid bounds");
            softly.assertThat(secondMoveResult.endsTheGame())
                  .as("%s ends the game", secondMoveResult)
                  .isFalse();
        });
    }

    @ParameterizedTest(name = "moves = {0}")
    @MethodSource("fullWonGameParams")
    void fullGameEndingWithWin(List<Position.BasicDirection> moves) {
        var game = createSimpleGame();

        var results = moves.stream()
                           .map(Position::from)
                           .map(game::placeMarkOn)
                           .toList();

        assertThat(results)
                .as("All move results should be valid")
                .allMatch(MoveResult::valid)
                .last()
                .as("Last move ends the game")
                .returns(true, MoveResult::endsTheGame);
        assertThat(game.tied())
                .as("Game tied")
                .isFalse();
    }

    static Stream<List<Position.BasicDirection>> fullWonGameParams() {
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
                        UPPER_CENTER, LOWER_LEFT, UPPER_LEFT, LOWER_RIGHT
                ),
                List.of(
                        //   | x |
                        //   | o | o
                        //   | x |
                        UPPER_CENTER, CENTER_RIGHT, LOWER_CENTER, CENTER,
                        //   | x | o
                        // x | o | o
                        // x | x | o
                        CENTER_LEFT, UPPER_RIGHT, LOWER_LEFT, LOWER_RIGHT
                )
        );
    }

    @ParameterizedTest(name = "moves = {0}")
    @MethodSource("fullTiedGameParams")
    void fullGameEndingWithTie(List<Position.BasicDirection> moves) {
        var game = createSimpleGame();

        var results = moves.stream()
                           .map(Position::from)
                           .map(game::placeMarkOn)
                           .toList();

        assertThat(results)
                .as("All move results should be valid")
                .allMatch(MoveResult::valid)
                .last()
                .as("Last move ends the game")
                .returns(true, MoveResult::endsTheGame);
        assertThat(game.tied())
                .as("Game tied")
                .isTrue();
    }

    static Stream<List<Position.BasicDirection>> fullTiedGameParams() {
        return Stream.of(
                List.of(
                        // o |   |
                        // x | o |
                        // x |   |
                        LOWER_LEFT, CENTER, CENTER_LEFT, UPPER_LEFT,
                        // o | x | x
                        // x | o | o
                        // x | o | x
                        LOWER_RIGHT, LOWER_CENTER, UPPER_CENTER, CENTER_RIGHT, UPPER_RIGHT
                ),
                List.of(
                        //   |   | o
                        // x | x | o
                        //   |   |
                        CENTER, UPPER_RIGHT, CENTER_LEFT, CENTER_RIGHT,
                        // o | x | o
                        // x | x | o
                        // o | o | x
                        LOWER_RIGHT, UPPER_LEFT, UPPER_CENTER, LOWER_CENTER, LOWER_LEFT
                )
        );
    }

    static Game createSimpleGame() {
        return Game.withDefaultGridSize()
                   .addPlayer("First", 'x')
                   .addPlayer("Second", 'o')
                   .build();
    }
}
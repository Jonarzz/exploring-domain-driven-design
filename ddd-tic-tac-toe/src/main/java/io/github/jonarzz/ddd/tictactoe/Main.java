package io.github.jonarzz.ddd.tictactoe;

import static io.github.jonarzz.ddd.tictactoe.model.grid.Position.BasicDirection.*;

import lombok.*;

import java.io.*;
import java.util.*;

import io.github.jonarzz.ddd.tictactoe.model.game.*;
import io.github.jonarzz.ddd.tictactoe.model.grid.*;

@CustomLog
public class Main {

    static InputStream inputStream = System.in;

    private static final Map<String, Position.BasicDirection> DIRECTION_INPUT_MAPPING = Map.of(
            "UL", UPPER_LEFT,
            "UC", UPPER_CENTER,
            "UR", UPPER_RIGHT,
            "CL", CENTER_LEFT,
            "CC", CENTER,
            "CR", CENTER_RIGHT,
            "LL", LOWER_LEFT,
            "LC", LOWER_CENTER,
            "LR", LOWER_RIGHT
    );

    private static final String POSITIONS_DESCRIPTION = """
            Positions:
            UL | UC | UR
            CL | CC | CR
            LL | LC | LR
            """;

    public static void main(String[] args) {
        try (var scanner = new Scanner(inputStream)) {
            var theGame = setUpTheGame(scanner);
            play(theGame, scanner);
        }
    }

    private static Game setUpTheGame(Scanner scanner) {
        var firstMark = 'o';
        log.info("Enter first player name (" + firstMark + ")");
        var firstPlayerName = scanner.nextLine();
        var secondMark = 'x';
        log.info("Enter second player name (" + secondMark + ")");
        var secondPlayerName = scanner.nextLine();
        return Game.withDefaultGridSize()
                   .addPlayer(firstPlayerName, firstMark)
                   .addPlayer(secondPlayerName, secondMark)
                   .build();
    }

    private static void play(Game game, Scanner scanner) {
        MoveResult result;
        do {
            Optional<Position> position;
            do {
                log.info(game.currentPlayerName() + ", enter position\n" + POSITIONS_DESCRIPTION);
                var positionInput = scanner.nextLine();
                position = positionFromInput(positionInput);
            } while (position.isEmpty());
            result = game.placeMarkOn(position.get());
            if (result.valid()) {
                log.info(game.view());
            } else {
                log.error(result.message());
            }
        } while (!result.valid() || !result.endsTheGame());
        log.info("Game over - " + result.message());
    }

    private static Optional<Position> positionFromInput(String input) {
        var direction = DIRECTION_INPUT_MAPPING.get(input.toUpperCase());
        if (direction == null) {
            log.error("Invalid position, see below:\n" + POSITIONS_DESCRIPTION);
            return Optional.empty();
        }
        return Optional.of(Position.from(direction));
    }
}
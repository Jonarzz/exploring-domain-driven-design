package io.github.jonarzz.ddd.tictactoe;

import static java.lang.Character.*;

import java.util.*;

import io.github.jonarzz.ddd.tictactoe.model.*;

public class Main {

    private static final String POSITIONS_DESCRIPTION = """
            Positions:
            00 | 01 | 02
            10 | 11 | 12
            20 | 21 | 22
            """;

    public static void main(String[] args) {
        var theGame = setUpTheGame();
        play(theGame);
    }

    private static Game setUpTheGame() {
        try (var scanner = new Scanner(System.in)) {
            var firstMark = 'o';
            System.out.println("Enter first player name (" + firstMark + ")");
            var firstPlayerName = scanner.nextLine();
            var secondMark = 'x';
            System.out.println("Enter second player name (" + secondMark + ")");
            var secondPlayerName = scanner.nextLine();
            return Game.withDefaultGridSize()
                       .addPlayer(firstPlayerName, firstMark)
                       .addPlayer(secondPlayerName, secondMark)
                       .build();
        }
    }

    private static void play(Game game) {
        try (var scanner = new Scanner(System.in)) {
            MoveResult result;
            do {
                Optional<Position> position;
                do {
                    System.out.println(game.currentPlayerName() + ", enter position\n" + POSITIONS_DESCRIPTION);
                    var positionInput = scanner.nextLine();
                    position = positionFromInput(positionInput);
                } while (position.isEmpty());
                result = game.placeMarkOn(position.get());
                if (result.valid()) {
                    System.out.println(game.view());
                } else {
                    System.err.println(result.message());
                }
            } while (!result.valid() || !result.endsTheGame());
            System.out.println("Game over - " + result.message());
        }
    }

    private static Optional<Position> positionFromInput(String input) {
        var errorMessage = "Invalid position, see below:\n" + POSITIONS_DESCRIPTION;
        if (input.length() != 2) {
            System.err.println(errorMessage);
            return Optional.empty();
        }
        var row = getNumericValue(input.charAt(0));
        var column = getNumericValue(input.charAt(1));
        if (row < 0 || column < 0) {
            System.err.println(errorMessage);
            return Optional.empty();
        }
        return Optional.of(new Position(row, column));
    }
}
package io.github.jonarzz.ddd.tictactoe;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.*;

class MainTest {

    // TODO rest of the tests

    @Test
    void invalidInput() throws Exception {
        var input = """
                Test
                Test 2
                XX
                """;

        try (var inputStream = new ByteArrayInputStream(input.getBytes());
             var outputStream = new ByteArrayOutputStream();
             var printStream = new PrintStream(outputStream)) {
            Main.inputStream = inputStream;
            System.setErr(printStream);

            try {
                Main.main(new String[0]);
            } catch (NoSuchElementException ignored) {
                // end of input
            }

            assertThat(outputStream.toString())
                    .contains("Invalid position");
        }
    }
}
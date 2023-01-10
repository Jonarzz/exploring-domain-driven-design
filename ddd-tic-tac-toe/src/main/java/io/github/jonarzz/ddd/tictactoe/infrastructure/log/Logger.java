package io.github.jonarzz.ddd.tictactoe.infrastructure.log;

public class Logger {

    private Logger() {

    }

    // used in lombok.config
    @SuppressWarnings("unused")
    public static Logger getLogger(String name) {
        return new Logger();
    }

    public void info(String message) {
        System.out.println(message);
    }

    public void error(String message) {
        System.err.println(message);
    }
}

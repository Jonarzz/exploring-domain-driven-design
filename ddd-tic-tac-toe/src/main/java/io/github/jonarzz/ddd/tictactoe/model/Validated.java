package io.github.jonarzz.ddd.tictactoe.model;

import lombok.*;

class Validated<T> {

    @Getter
    final boolean valid;
    @Getter
    String invalidityReason;
    T object;

    private Validated(String invalidityReason) {
        valid = false;
        this.invalidityReason = invalidityReason;
    }

    private Validated(T object) {
        valid = true;
        this.object = object;
    }

    static <T> Validated<T> invalid(String reason) {
        return new Validated<>(reason);
    }

    static <T> Validated<T> valid(T object) {
        return new Validated<>(object);
    }

    T getValidObject() {
        if (!valid()) {
            throw new IllegalStateException("Object not valid, reason: " + invalidityReason);
        }
        return object;
    }
}

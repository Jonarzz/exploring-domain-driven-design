package io.github.jonarzz.ddd.tictactoe.infrastructure.result;

import lombok.*;
import lombok.experimental.*;

@FieldDefaults(makeFinal = true)
public class Validated<T> {

    @Getter
    boolean valid;
    @Getter
    String invalidityReason;
    T object;

    private Validated(String invalidityReason) {
        valid = false;
        this.invalidityReason = invalidityReason;
        object = null;
    }

    private Validated(T object) {
        valid = true;
        invalidityReason = null;
        this.object = object;
    }

    public static <T> Validated<T> invalid(String reason) {
        return new Validated<>(reason);
    }

    public static <T> Validated<T> valid(T object) {
        return new Validated<>(object);
    }

    public T getValidObject() {
        if (!valid()) {
            throw new IllegalStateException("Object not valid, reason: " + invalidityReason);
        }
        return object;
    }
}

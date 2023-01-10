package io.github.jonarzz.ddd.tictactoe.infrastructure.result;

import lombok.experimental.*;

@FieldDefaults(makeFinal = true)
public class Validated<T> {

    boolean valid;
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

    public static Validated<Void> valid() {
        return valid(null);
    }

    public boolean isValid() {
        return valid;
    }

    public boolean isNotValid() {
        return !isValid();
    }

    public String invalidityReason() {
        return invalidityReason;
    }

    public T getValidObject() {
        if (isNotValid()) {
            throw new IllegalStateException("Object not valid, reason: " + invalidityReason);
        }
        return object;
    }
}

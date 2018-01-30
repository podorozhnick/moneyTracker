package com.podorozhnick.moneytracker.util;

import java.util.List;
import java.util.Objects;

@FunctionalInterface
public interface ThrowableConsumer<T, E extends Throwable> {

    void accept(T t) throws E;

    default ThrowableConsumer<T, E> andThen(ThrowableConsumer<? super T, E> after) {
        Objects.requireNonNull(after);
        return (T t) -> { accept(t); after.accept(t); };
    }

    static <T, E extends Throwable> ThrowableConsumer<T, E> combine(List<ThrowableConsumer<T, E>> consumers) {
        return consumers.stream().reduce(t -> {}, ThrowableConsumer::andThen);
    }
}

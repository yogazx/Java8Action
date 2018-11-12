package ch2.lambda;

@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);
}



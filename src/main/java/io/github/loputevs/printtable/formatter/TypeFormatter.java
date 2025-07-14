package io.github.loputevs.printtable.formatter;

import java.util.function.Function;

/**
 * Interface purpose - Map to String then format
 * <p>
 * Typealias to {@code Function<TYPE, String>}
 * <p>
 * Design by Composite pattern (<a href="https://refactoring.guru/design-patterns/composite">Refactoring guru Composite pattern</a>)
 *
 * @param <TYPE>
 */
@FunctionalInterface public interface TypeFormatter<TYPE> extends Function<TYPE, String> {
    
    /**
     * If formater didn't process value return this const for info parent formater that we need to try next formater.
     */
    /* public static final */
    String LETS_TRY_NEXT_FORMATER = "LETS_TRY_NEXT_FORMATER__PRINT_TABLE__SPECIAL_CONST";
}

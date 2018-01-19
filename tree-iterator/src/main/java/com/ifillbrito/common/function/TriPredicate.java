package com.ifillbrito.common.function;

/**
 * Created by gjib on 20.01.18.
 */
@FunctionalInterface
public interface TriPredicate<X, Y, Z>
{
    boolean test (X x, Y y, Z z);
}

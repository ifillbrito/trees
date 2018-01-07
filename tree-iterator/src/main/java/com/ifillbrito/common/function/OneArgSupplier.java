package com.ifillbrito.common.function;

/**
 * Created by gjib on 05.01.18.
 */
@FunctionalInterface
public interface OneArgSupplier<Argument, Result>
{
    Result get(Argument argument);
}

package com.ifillbrito.trees.iterator;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by gjib on 17.01.18.
 */
public interface EditOperation<Arg, R extends Condition> extends BaseOperation<R>
{
    R apply(Consumer<Arg> consumer);

    R replace(Function<Arg, ?> function);

    R remove();
}

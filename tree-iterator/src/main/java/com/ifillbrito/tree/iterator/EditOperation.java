package com.ifillbrito.tree.iterator;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by gjib on 17.01.18.
 */
public interface EditOperation<Arg, Cond extends NodeCondition> extends BaseOperation<Cond, EditOperation<Arg, Cond>>
{
    Cond apply(Consumer<Arg> consumer);

    Cond replace(Function<Arg, ?> function);

    Cond remove();
}

package com.github.ifillbrito.common.operation;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 07.01.18.
 */
public interface Operation<Argument, Result>
{
    Result modify(Consumer<Argument> consumer);

    Result replace(Function<Argument, ?> function);

    Result remove();

    Result skip();

    Result ignore();

    <ListOrSet extends Collection<Argument>> ListOrSet collect(Supplier<ListOrSet> collectionSupplier);

    <Key, Value extends Argument> Map<Key, Value> collect(
            Function<Argument, Key> keySupplier,
            Supplier<Map<Key, Value>> mapSupplier);

    <Key, ListOrSet extends Collection<Argument>> Map<Key, ListOrSet> group(
            Function<Argument, Key> keySupplier,
            Supplier<ListOrSet> collectionSupplier,
            Supplier<Map<Key, ListOrSet>> mapSupplier);
}

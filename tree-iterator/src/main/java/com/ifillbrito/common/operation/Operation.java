package com.ifillbrito.common.operation;

import com.ifillbrito.common.function.OneArgSupplier;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by gjib on 07.01.18.
 */
public interface Operation<Argument, Result>
{
    Result modify(Consumer<Argument> consumer);

    Result replace(OneArgSupplier<Argument, ?> supplier);

    Result remove();

    Result skipTree();

    Result skipItem();

    <ListOrSet extends Collection<Argument>> ListOrSet collect(Supplier<ListOrSet> collectionSupplier);

    <Key, Value extends Argument> Map<Key, Value> collect(
            OneArgSupplier<Argument, Key> keySupplier,
            Supplier<Map<Key, Value>> mapSupplier);

    <Key, ListOrSet extends Collection<Argument>> Map<Key, ListOrSet> group(
            OneArgSupplier<Argument, Key> keySupplier,
            Supplier<ListOrSet> collectionSupplier,
            Supplier<Map<Key, ListOrSet>> mapSupplier);
}

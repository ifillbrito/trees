package com.ifillbrito.tree.iterator;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 17.01.18.
 */
public interface SingleTypeMetaDataAwareTreeIterator<Root>
{
    <Item, Cond extends NodeCondition<Root, CollectOperation<Root, Cond>>> Cond collect(
            Collection<Item> collection
    );

    <Key, Cond extends NodeCondition<Root, CollectOperation<Root, Cond>>> Cond collect(
            Map<Key, Root> map,
            Function<Root, Key> keySupplier
    );

    <Key, Value, Cond extends NodeCondition<Root, CollectOperation<Root, Cond>>> Cond collect(
            Map<Key, Value> map,
            Function<Root, Key> keySupplier,
            Function<Root, Value> valueTransformer
    );

    <Key, Item, ListOrSet extends Collection<Item>, Cond extends NodeCondition<Root, CollectOperation<Root, Cond>>> Cond group(
            Map<Key, ListOrSet> map,
            Function<Root, Key> keySupplier,
            Supplier<ListOrSet> listSupplier
    );

    <Key, Item, ListOrSet extends Collection<Item>, Cond extends NodeCondition<Root, CollectOperation<Root, Cond>>> Cond group(
            Map<Key, ListOrSet> map,
            Function<Root, Key> keySupplier,
            Function<Root, Item> valueTransformer,
            Supplier<ListOrSet> listSupplier
    );

    <Cond extends NodeCondition<Root, BaseOperation<Cond, BaseOperation>>> Cond traverse();

    <Cond extends NodeCondition<Root, EditOperation<Root, Cond>>> Cond edit();

    void execute();
}

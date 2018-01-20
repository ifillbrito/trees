package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.node.NodeMeta;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 17.01.18.
 */
public interface TreeIterator<Root>
{
    <Item, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond collect(
            Collection<Item> collection
    );

    <Key, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond collect(
            Map<Key, Root> map,
            Function<Root, Key> keySupplier
    );

    <Key, Value, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond collect(
            Map<Key, Value> map,
            Function<Root, Key> keySupplier,
            Function<Root, Value> valueTransformer
    );

    <Key, Item, ListOrSet extends Collection<Item>, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond group(
            Map<Key, ListOrSet> map,
            Function<Root, Key> keySupplier,
            Supplier<ListOrSet> listSupplier
    );

    <Key, Item, ListOrSet extends Collection<Item>, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond group(
            Map<Key, ListOrSet> map,
            Function<Root, Key> keySupplier,
            Function<Root, Item> valueTransformer,
            Supplier<ListOrSet> listSupplier
    );

    <Cond extends Condition<Root, BaseOperation<Cond, BaseOperation>>> Cond traverse();

    <Cond extends Condition<Root, EditOperation<Root, Cond>>> Cond edit();

    TreeIterator<NodeMeta<Root>> wrapNodes();

    <T> TreeIterator<T> unwrapNodes(Class<T> type);

    void execute();
}

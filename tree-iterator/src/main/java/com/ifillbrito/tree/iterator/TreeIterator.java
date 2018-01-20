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
    <Item, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition collect(
            Collection<Item> collection
    );

    <Key, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition collect(
            Map<Key, Root> map,
            Function<Root, Key> keySupplier
    );

    <Key, Value, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition collect(
            Map<Key, Value> map,
            Function<Root, Key> keySupplier,
            Function<Root, Value> valueTransformer
    );

    <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition group(
            Map<Key, ListOrSet> map,
            Function<Root, Key> keySupplier,
            Supplier<ListOrSet> listSupplier
    );

    <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition group(
            Map<Key, ListOrSet> map,
            Function<Root, Key> keySupplier,
            Function<Root, Item> valueTransformer,
            Supplier<ListOrSet> listSupplier
    );

    <Precondition extends OperationPrecondition<Root, BaseOperation<Precondition, BaseOperation>>> Precondition iterate();

    <Precondition extends OperationPrecondition<Root, EditOperation<Root, Precondition>>> Precondition edit();

    TreeIterator<NodeMeta<Root>> resolveParents();

    <T> TreeIterator<T> use(Class<T> type);

    void execute();
}

package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.node.NodeWrapper;
import com.github.ifillbrito.tree.operation.CollectOperation;
import com.github.ifillbrito.tree.operation.EditOperation;
import com.github.ifillbrito.tree.operation.EditOperationPrecondition;
import com.github.ifillbrito.tree.operation.IterateOperation;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 17.01.18.
 */
public interface TreeIterator<Node>
{
    <Item, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(
            Collection<Item> collection
    );

    <Key, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(
            Map<Key, Node> map,
            Function<Node, Key> keySupplier
    );

    <Key, Value, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(
            Map<Key, Value> map,
            Function<Node, Key> keySupplier,
            Function<Node, Value> valueTransformer
    );

    <Key, Item, ListOrSet extends Collection<Item>, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition group(
            Map<Key, ListOrSet> map,
            Function<Node, Key> keySupplier,
            Supplier<ListOrSet> listSupplier
    );

    <Key, Item, ListOrSet extends Collection<Item>, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition group(
            Map<Key, ListOrSet> map,
            Function<Node, Key> keySupplier,
            Function<Node, Item> valueTransformer,
            Supplier<ListOrSet> listSupplier
    );

    <Precondition extends EditOperationPrecondition<Node, IterateOperation<Node, Precondition>>> Precondition iterate();

    <Precondition extends EditOperationPrecondition<Node, EditOperation<Node, Precondition>>> Precondition edit();

    TreeIterator<NodeWrapper<Node>> resolveParents();

    <T> TreeIterator<T> use(Class<T> type);

    void execute();
}

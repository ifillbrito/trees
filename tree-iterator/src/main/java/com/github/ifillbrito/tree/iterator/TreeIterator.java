package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.node.NodeWrapper;
import com.github.ifillbrito.tree.operation.CollectOperation;
import com.github.ifillbrito.tree.operation.EditOperation;
import com.github.ifillbrito.tree.operation.IterateOperation;
import com.github.ifillbrito.tree.operation.OperationPrecondition;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 17.01.18.
 */
public interface TreeIterator<Node>
{
    <Item, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(
            Collection<Item> collection
    );

    <Item, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(
            Collection<Item> collection, Function<Node, Item> valueTransformer
    );

    <Key, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(
            Map<Key, Node> map,
            Function<Node, Key> mapKeySupplier
    );

    <Key, Value, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(
            Map<Key, Value> map,
            Function<Node, Key> mapKeySupplier,
            Function<Node, Value> valueTransformer
    );

    <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition group(
            Map<Key, ListOrSet> map,
            Function<Node, Key> mapKeySupplier,
            Supplier<ListOrSet> collectionSupplier
    );

    <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition group(
            Map<Key, ListOrSet> map,
            Function<Node, Key> mapKeySupplier,
            Function<Node, Item> valueTransformer,
            Supplier<ListOrSet> collectionSupplier
    );

    <Precondition extends OperationPrecondition<Node, IterateOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, IterateOperation<Node, Precondition>, Precondition>>> Precondition iterate();

    <Precondition extends OperationPrecondition<Node, EditOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, EditOperation<Node, Precondition>, Precondition>>> Precondition edit();

    TreeIterator<NodeWrapper<Node>> resolveParents();

    <T> TreeIterator<T> use(Class<T> type);

    void execute();
}

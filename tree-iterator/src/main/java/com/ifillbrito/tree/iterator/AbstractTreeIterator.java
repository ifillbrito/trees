package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.node.NodeMeta;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 18.01.18.
 */
public class AbstractTreeIterator<Root> implements TreeIterator<Root>
{
    private Root root;

    public AbstractTreeIterator(Root root)
    {
        this.root = root;
    }


    @Override
    public <Item, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition collect(Collection<Item> collection)
    {
        return null;
    }

    @Override
    public <Key, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition collect(Map<Key, Root> map, Function<Root, Key> keySupplier)
    {
        return null;
    }

    @Override
    public <Key, Value, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition collect(Map<Key, Value> map, Function<Root, Key> keySupplier, Function<Root, Value> valueTransformer)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Root, Key> keySupplier, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Root, CollectOperation<Root, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Root, Key> keySupplier, Function<Root, Item> valueTransformer, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Precondition extends OperationPrecondition<Root, BaseOperation<Precondition, BaseOperation>>> Precondition iterate()
    {
        return null;
    }

    @Override
    public <Precondition extends OperationPrecondition<Root, EditOperation<Root, Precondition>>> Precondition edit()
    {
        return null;
    }

    @Override
    public TreeIterator<NodeMeta<Root>> resolveParents()
    {
        return null;
    }

    @Override
    public <T> TreeIterator<T> use(Class<T> type)
    {
        return null;
    }

    @Override
    public void execute()
    {

    }
}

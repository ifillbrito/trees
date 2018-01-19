package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.node.NodeWrapper;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 18.01.18.
 */
public class AbstractTreeIterator<Root> implements SingleTypeTreeIterator<Root>
{
    private Root root;

    public AbstractTreeIterator(Root root)
    {
        this.root = root;
    }


    @Override
    public <Item, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond collect(Collection<Item> collection)
    {
        return null;
    }

    @Override
    public <Key, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond collect(Map<Key, Root> map, Function<Root, Key> keySupplier)
    {
        return null;
    }

    @Override
    public <Key, Value, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond collect(Map<Key, Value> map, Function<Root, Key> keySupplier, Function<Root, Value> valueTransformer)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond group(Map<Key, ListOrSet> map, Function<Root, Key> keySupplier, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond group(Map<Key, ListOrSet> map, Function<Root, Key> keySupplier, Function<Root, Item> valueTransformer, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Cond extends Condition<Root, BaseOperation<Cond, BaseOperation>>> Cond traverse()
    {
        return null;
    }

    @Override
    public <Cond extends Condition<Root, EditOperation<Root, Cond>>> Cond edit()
    {
        return null;
    }

    @Override
    public SingleTypeTreeIterator<NodeWrapper<Root, Root>> resolveMetaData()
    {
        return null;
    }

    @Override
    public void execute()
    {

    }
}

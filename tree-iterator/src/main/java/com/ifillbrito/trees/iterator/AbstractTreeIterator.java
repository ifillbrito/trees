package com.ifillbrito.trees.iterator;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 18.01.18.
 */
public abstract class AbstractTreeIterator<Root> implements SingleTypeTreeIterator<Root>
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
    public <Key, Value, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond collect(Map<Key, Value> map, Function<Root, Key> keySupplier, Function<Root, Value> valueTransformer)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Cond extends Condition<Root, CollectOperation<Root, Cond>>> Cond collect(Map<Key, ListOrSet> map, Supplier<Key> keySupplier, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Cond extends Condition<Root, EditOperation<Root, Cond>>> Cond edit()
    {
        return null;
    }

    @Override
    public void execute()
    {

    }
}

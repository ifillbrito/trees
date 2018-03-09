package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.node.NodeWrapper;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 18.01.18.
 */
public abstract class AbstractTreeIterator<Node> implements TreeIterator<Node>
{
    public static final String EMPTY_PATH = "";
    private Node node;

    public AbstractTreeIterator(Node node)
    {
        this.node = node;
    }


    @Override
    public <Item, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(Collection<Item> collection)
    {
        return null;
    }

    @Override
    public <Key, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(Map<Key, Node> map, Function<Node, Key> keySupplier)
    {
        return null;
    }

    @Override
    public <Key, Value, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(Map<Key, Value> map, Function<Node, Key> keySupplier, Function<Node, Value> valueTransformer)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Node, Key> keySupplier, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Node, Key> keySupplier, Function<Node, Item> valueTransformer, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Precondition extends OperationPrecondition<Node, IterateOperation<Node, Precondition>>> Precondition iterate()
    {
        return null;
    }

    @Override
    public <Precondition extends OperationPrecondition<Node, EditOperation<Node, Precondition>>> Precondition edit()
    {
        return null;
    }

    @Override
    public TreeIterator<NodeWrapper<Node>> resolveParents()
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

    protected abstract Iterator getRootIterator(Node node);

    protected abstract void executeRecursionStep(Node node);

    protected String createPath(Node node, String parentPaht)
    {
        return EMPTY_PATH;
    }

    protected void executeRecursive(Iterator iterator)
    {

    }
}

package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.node.NodeWrapper;
import com.github.ifillbrito.tree.operation.*;
import com.github.ifillbrito.tree.operation.impl.EditOperationPreconditionImpl;
import com.github.ifillbrito.tree.utils.TreeNodeUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 18.01.18.
 */
public abstract class AbstractTreeIterator<Node> implements TreeIterator<Node>
{
    protected static final String EMPTY_PATH = "";
    private String currentPath = EMPTY_PATH;
    protected Node node;
    protected int editCounter, iterateCounter, collectCounter = 0;
    protected LinkedList<OperationArguments> operationArguments = new LinkedList<>();

    // data holders
    private Collection collection;
    private Map map;
    private Function mapKeyFunction;
    private Supplier collectionSupplier;

    public AbstractTreeIterator(Node node)
    {
        TreeNodeUtils.verifyNotNull(node);
        this.node = node;
    }


    @Override
    public <Item, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(Collection<Item> collection)
    {
        return null;
    }

    @Override
    public <Key, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(Map<Key, Node> map, Function<Node, Key> keySupplier)
    {
        return null;
    }

    @Override
    public <Key, Value, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition collect(Map<Key, Value> map, Function<Node, Key> keySupplier, Function<Node, Value> valueTransformer)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Node, Key> keySupplier, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends EditOperationPrecondition<Node, CollectOperation<Node, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Node, Key> keySupplier, Function<Node, Item> valueTransformer, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Precondition extends EditOperationPrecondition<Node, IterateOperation<Node, Precondition>>> Precondition iterate()
    {
        return null;
    }

    @Override
    public <Precondition extends EditOperationPrecondition<Node, EditOperation<Node, Precondition>>> Precondition edit()
    {
        OperationArguments<Node> arguments = new OperationArguments<>();
        operationArguments.add(arguments);
        String scope = OperationType.EDIT.getScopePrefix() + editCounter++;
        arguments.setScope(scope);
        arguments.setOperationType(OperationType.EDIT);
        return (Precondition) new EditOperationPreconditionImpl<Node, EditOperation<Node, Precondition>>(arguments, this);
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
        Iterator iterator = Collections.singletonList(node).iterator();
        executeRecursive(iterator);
        operationArguments.clear();
    }

    protected abstract void executeRecursionStep(Node node);

    protected String createPath(Node node, String parentPaht)
    {
        return EMPTY_PATH;
    }

    protected void executeRecursive(Iterator<Node> iterator)
    {
        if ( iterator == null ) return;

        while ( iterator.hasNext() )
        {
            Node object = iterator.next();
            if ( object == null ) continue;
            String tmpPath = currentPath;
            currentPath = createPath(object, currentPath);

            for ( OperationArguments operationArguments : this.operationArguments )
            {
                switch ( operationArguments.getOperation() )
                {
                    case MODIFY:
                        operationArguments.getConsumer().accept(object);
                        break;
                    case REPLACE:
//                        iterator.set(operationArguments.getFunction().apply(object));
                        break;
                    case REMOVE:
                        iterator.remove();
                        break;
                    case COLLECT_AS_LIST:
                        collection.add(object);
                        break;
                    case COLLECT_AS_MAP:
                        map.put(mapKeyFunction.apply(object), object);
                        break;
                    case GROUP:
                        map.putIfAbsent(mapKeyFunction.apply(object), collectionSupplier.get());
                        Collection collection = (Collection) map.get(mapKeyFunction.apply(object));
                        collection.add(object);
                        break;
                }
            }

            executeRecursionStep(object);
            currentPath = tmpPath;
        }
    }
}

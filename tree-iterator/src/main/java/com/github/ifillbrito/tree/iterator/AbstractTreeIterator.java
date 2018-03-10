package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.node.NodeWrapper;
import com.github.ifillbrito.tree.operation.*;
import com.github.ifillbrito.tree.operation.impl.OperationPreconditionImpl;
import com.github.ifillbrito.tree.utils.TreeNodeUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 18.01.18.
 */
public abstract class AbstractTreeIterator<Node> implements TreeIterator<Node>
{
    protected static final String PATH_SEPARATOR = "/";
    protected static final String EMPTY_PATH = "";
    private Node node;
    private int editCounter, iterateCounter, collectCounter = 0;
    private LinkedList<OperationArguments> operationArguments = new LinkedList<>();
    private Map<Node, NodeWrapper<Node>> nodeWrapperMap = new HashMap<>();

    // data holders
    private String currentPath = EMPTY_PATH;
    private Collection collection;
    private Class classType;
    private Map map;
    private Function mapKeyFunction;
    private Supplier collectionSupplier;

    public AbstractTreeIterator(Node node)
    {
        TreeNodeUtils.verifyNotNull(node);
        this.node = node;
        this.classType = node.getClass();
    }


    @Override
    public <Item, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(Collection<Item> collection)
    {
        return null;
    }

    @Override
    public <Key, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(Map<Key, Node> map, Function<Node, Key> keySupplier)
    {
        return null;
    }

    @Override
    public <Key, Value, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(Map<Key, Value> map, Function<Node, Key> keySupplier, Function<Node, Value> valueTransformer)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Node, Key> keySupplier, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Node, Key> keySupplier, Function<Node, Item> valueTransformer, Supplier<ListOrSet> listSupplier)
    {
        return null;
    }

    @Override
    public <Precondition extends OperationPrecondition<Node, IterateOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, IterateOperation<Node, Precondition>, Precondition>>> Precondition iterate()
    {
        return null;
    }

    @Override
    public <Precondition extends OperationPrecondition<Node, EditOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, EditOperation<Node, Precondition>, Precondition>>> Precondition edit()
    {
        OperationArguments arguments = new OperationArguments();
        operationArguments.add(arguments);
        String scope = OperationType.EDIT.getScopePrefix() + editCounter++;
        arguments.setScope(scope);
        arguments.setClassType(classType);
        arguments.setOperationType(OperationType.EDIT);
        return (Precondition) new OperationPreconditionImpl<Node, EditOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, EditOperation<Node, Precondition>, Precondition>>(arguments, this);
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
        executeRecursive(null, iterator);
        operationArguments.clear();
    }

    protected abstract void executeRecursionStep(Node node);

    protected String createPath(Node node, String path)
    {
        return EMPTY_PATH;
    }

    protected void executeRecursive(Node parent, Iterator<Node> childrenIterator)
    {
        if ( childrenIterator == null ) return;

        while ( childrenIterator.hasNext() )
        {
            Node object = childrenIterator.next();
            if ( object == null ) continue;
            String parentPath = currentPath;
            currentPath = createPath(object, currentPath);

            NodeWrapper<Node> wrapper = getOrCreateWrapper(parent, object);
            wrapper.setNode(object);
            wrapper.setParentPath(parentPath);
            wrapper.setCurrentPath(currentPath);

            for ( OperationArguments operationArguments : this.operationArguments )
            {
                if ( isTargetNodeWrapper(wrapper, operationArguments) ||
                        isTargetNode(parent, object, operationArguments) )
                {
                    continue;
                }

                switch ( operationArguments.getOperation() )
                {
                    case MODIFY:
                        operationArguments.getConsumer().accept(object);
                        break;
                    case REPLACE:
//                        iterator.set(operationArguments.getFunction().apply(object));
                        break;
                    case REMOVE:
                        childrenIterator.remove();
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
            currentPath = parentPath;
        }
    }

    private NodeWrapper<Node> getOrCreateWrapper(Node parent, Node object)
    {
        NodeWrapper<Node> parentWrapper = nodeWrapperMap.get(parent);
        if ( parentWrapper == null )
        {
            if ( parent == null )
            {
                parentWrapper = new NodeWrapper<>(object);
                nodeWrapperMap.put(object, parentWrapper);
            }
            else
            {
                parentWrapper = new NodeWrapper<>(parent);
                nodeWrapperMap.put(parent, parentWrapper);
            }
            return parentWrapper;
        }

        NodeWrapper<Node> wrapper = new NodeWrapper<>(object);
        wrapper.setParent(parentWrapper);
        nodeWrapperMap.put(object, wrapper);
        return wrapper;
    }

    private boolean isTargetNodeWrapper(NodeWrapper<Node> wrapper, OperationArguments operationArguments)
    {
        return operationArguments.isParentResolutionEnabled() && !operationArguments.testPrecondition(wrapper);
    }

    private boolean isTargetNode(Node parent, Node object, OperationArguments operationArguments)
    {
        return !operationArguments.isParentResolutionEnabled() && !operationArguments.testPrecondition(parent, object, currentPath);
    }
}

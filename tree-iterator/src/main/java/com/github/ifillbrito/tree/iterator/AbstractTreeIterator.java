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
    public static final ExecutionMode DEFAULT_EXECUTION_MODE = ExecutionMode.TOP_DOWN;
    protected static final String PATH_SEPARATOR = "/";
    protected static final String EMPTY_PATH = "";
    private Node node;
    private int editScopeCounter, iterateScopeCounter, collectScopeCounter = 0;
    private LinkedList<OperationDataHolder> operationArguments = new LinkedList<>();
    private LinkedList<OperationDataHolder> topDownOperationArguments = new LinkedList<>();
    private LinkedList<OperationDataHolder> bottomUpOperationArguments = new LinkedList<>();
    private Map<Node, NodeWrapper<Node>> nodeWrapperMap = new HashMap<>();
    private ExecutionMode executionMode = DEFAULT_EXECUTION_MODE;

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
        OperationDataHolder arguments = new OperationDataHolder();
        operationArguments.add(arguments);
        String scope = OperationType.EDIT.getScopePrefix() + editScopeCounter++;
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
    public TreeIterator<Node> setExecution(ExecutionMode executionMode)
    {
        this.executionMode = executionMode;
        return this;
    }

    @Override
    public void execute()
    {
        separateOperationArguments();
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

            executeOperations(topDownOperationArguments, parent, object, wrapper, childrenIterator);
            executeRecursionStep(object);
            currentPath = parentPath;
            executeOperations(bottomUpOperationArguments, parent, object, wrapper, childrenIterator);
        }
    }

    private void executeOperations(
            List<OperationDataHolder> operationDataHolderByExecutionMode,
            Node parent,
            Node object,
            NodeWrapper<Node> wrapper,
            Iterator<Node> childrenIterator
    )
    {
        for ( OperationDataHolder operationDataHolder : operationDataHolderByExecutionMode )
        {
            if ( isTargetNodeWrapper(wrapper, operationDataHolder) ||
                    isTargetNode(parent, object, operationDataHolder) )
            {
                continue;
            }

            if ( operationDataHolder.isParentResolutionEnabledForOperation() )
            {
                executeOperation(wrapper, operationDataHolder, childrenIterator);
            }
            else
            {
                executeOperation(object, operationDataHolder, childrenIterator);
            }
        }
    }

    private void executeOperation(Object object, OperationDataHolder operationDataHolder, Iterator<Node> childrenIterator)
    {
        switch ( operationDataHolder.getOperation() )
        {
            case MODIFY:
                operationDataHolder.getConsumer().accept(object);
                break;
            case REPLACE:
//                        iterator.set(operationDataHolder.getFunction().apply(object));
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
            return parentWrapper;
        }

        NodeWrapper<Node> wrapper = new NodeWrapper<>(object);
        wrapper.setParent(parentWrapper);
        nodeWrapperMap.put(object, wrapper);
        return wrapper;
    }

    private boolean isTargetNodeWrapper(NodeWrapper<Node> wrapper, OperationDataHolder operationDataHolder)
    {
        return operationDataHolder.isParentResolutionEnabledForPrecondition()
                && !operationDataHolder.testPrecondition(wrapper);
    }

    private boolean isTargetNode(Node parent, Node object, OperationDataHolder operationDataHolder)
    {
        return !operationDataHolder.isParentResolutionEnabledForPrecondition()
                && !operationDataHolder.testPrecondition(parent, object, currentPath);
    }

    private void separateOperationArguments()
    {
        Map<String, ExecutionMode> executionModeMap = new HashMap<>();
        for ( OperationDataHolder operationArgument : operationArguments )
        {
            ExecutionMode executionMode = operationArgument.getByScope(
                    OperationDataHolder::getExecutionMode,
                    this.executionMode,
                    executionModeMap
            );

            switch ( executionMode )
            {
                case TOP_DOWN:
                    topDownOperationArguments.add(operationArgument);
                    break;
                case BOTTOM_UP:
                    bottomUpOperationArguments.add(operationArgument);
                    break;
            }
        }
    }

}

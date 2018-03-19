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
    private int editScopeCounter, collectScopeCounter = 0;
    private LinkedList<OperationDataHolder> operationDataHolders = new LinkedList<>();
    private LinkedList<OperationDataHolder> topDownOperationDataHolders = new LinkedList<>();
    private LinkedList<OperationDataHolder> bottomUpOperationDataHolders = new LinkedList<>();
    private Map<Node, NodeWrapper<Node>> nodeWrapperMap = new HashMap<>();
    private ExecutionMode executionMode;

    // data holders
    private String currentPath = EMPTY_PATH;
    private Class classType;
    private Map map;
    private Collection collection;
    private Function mapKeyFunction;
    private Supplier collectionSupplier;
    private Function valueTransformer;

    // flags
    private boolean replaceOperationUsed = false;

    public AbstractTreeIterator(Node node)
    {
        TreeNodeUtils.verifyNotNull(node);
        this.node = node;
        this.classType = node.getClass();
        this.executionMode = DEFAULT_EXECUTION_MODE;
    }


    @Override
    public <Item, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(Collection<Item> collection)
    {
        return createOperationPrecondition(collection);
    }

    @Override
    public <Item, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(Collection<Item> collection, Function<Node, Item> valueTransformer)
    {
        this.valueTransformer = valueTransformer;
        return createOperationPrecondition(collection);
    }

    @Override
    public <Key, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(Map<Key, Node> map, Function<Node, Key> mapKeySupplier)
    {
        return createOperationPrecondition(map, mapKeySupplier);
    }

    @Override
    public <Key, Value, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition collect(Map<Key, Value> map, Function<Node, Key> mapKeySupplier, Function<Node, Value> valueTransformer)
    {
        this.valueTransformer = valueTransformer;
        return createOperationPrecondition(map, mapKeySupplier);
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Node, Key> mapKeySupplier, Supplier<ListOrSet> collectionSupplier)
    {
        return createOperationPrecondition(map, mapKeySupplier, collectionSupplier);
    }

    @Override
    public <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition group(Map<Key, ListOrSet> map, Function<Node, Key> mapKeySupplier, Function<Node, Item> valueTransformer, Supplier<ListOrSet> collectionSupplier)
    {
        this.valueTransformer = valueTransformer;
        return createOperationPrecondition(map, mapKeySupplier, collectionSupplier);
    }

    @Override
    public <Precondition extends OperationPrecondition<Node, IterateOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, IterateOperation<Node, Precondition>, Precondition>>> Precondition iterate()
    {
        OperationDataHolder operationDataHolder = new OperationDataHolder(OperationType.ITERATE, 0, classType);
        return (Precondition) new OperationPreconditionImpl<
                Node,
                IterateOperation<Node, Precondition>,
                OperationPrecondition<
                        NodeWrapper<Node>,
                        IterateOperation<Node, Precondition>,
                        Precondition>
                >(operationDataHolder, operationDataHolders, this);
    }

    @Override
    public <Precondition extends OperationPrecondition<Node, EditOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, EditOperation<Node, Precondition>, Precondition>>> Precondition edit()
    {
        OperationDataHolder operationDataHolder = new OperationDataHolder(OperationType.EDIT, editScopeCounter++, classType);
        return (Precondition) new OperationPreconditionImpl<
                Node,
                EditOperation<Node, Precondition>,
                OperationPrecondition<
                        NodeWrapper<Node>,
                        EditOperation<Node, Precondition>,
                        Precondition>
                >(operationDataHolder, operationDataHolders, this);
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
        executeOperationDataHoldersPreprocessing();
        List<Node> rootNode = new ArrayList<>(Collections.singletonList(node));
        executeRecursive(null, rootNode);
        operationDataHolders.clear();
    }

    protected abstract void executeRecursionStep(Node node);

    protected String createPath(Node node, String path)
    {
        return EMPTY_PATH;
    }

    protected void executeRecursive(Node parent, Collection<Node> children)
    {
        if ( children == null ) return;
        List<Node> clonedChildren = null;
        Iterator<Node> childrenIterator = children.iterator();
        boolean mustCloneChildren = replaceOperationUsed && !(children instanceof List);
        if ( mustCloneChildren )
        {
            clonedChildren = new ArrayList<>(children);
            childrenIterator = clonedChildren.listIterator();
        }
        else if ( children instanceof List )
        {
            childrenIterator = ((List) children).listIterator();
        }

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

            boolean skipTree = executeOperations(topDownOperationDataHolders, parent, object, wrapper, childrenIterator);
            if ( !skipTree )
            {
                executeRecursionStep(object);
                executeOperations(bottomUpOperationDataHolders, parent, object, wrapper, childrenIterator);
            }
            currentPath = parentPath;
        }

        if ( mustCloneChildren )
        {
            children.clear();
            children.addAll(clonedChildren);
        }

    }

    private boolean executeOperations(
            List<OperationDataHolder> operationDataHolderByExecutionMode,
            Node parent,
            Node object,
            NodeWrapper<Node> wrapper,
            Iterator<Node> childrenIterator)
    {
        boolean skipTree = false;
        for ( OperationDataHolder operationDataHolder : operationDataHolderByExecutionMode )
        {
            if ( isTreeNodeWrapperSkipped(wrapper, operationDataHolder) ||
                    isTreeNodeSkipped(parent, object, operationDataHolder) )
            {
                skipTree = true;
                break;
            }

            if ( (!isTargetNodeWrapper(wrapper, operationDataHolder) &&
                    !isTargetNode(parent, object, operationDataHolder)) )
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
        return skipTree;
    }

    private void executeOperation(Object object, OperationDataHolder operationDataHolder, Iterator<Node> childrenIterator)
    {
        switch ( operationDataHolder.getOperationType() )
        {
            case EDIT:
            {
                switch ( operationDataHolder.getOperation() )
                {
                    case MODIFY:
                        operationDataHolder.getConsumer().accept(object);
                        break;
                    case REPLACE:
                        ((ListIterator<Node>) childrenIterator).set((Node) operationDataHolder.getReplaceFunction().apply(object));
                        break;
                    case REMOVE:
                        childrenIterator.remove();
                        break;
                }
            }
            case ITERATE:
                break;
            case COLLECT_AS_LIST:
            {
                if ( valueTransformer != null )
                {
                    collection.add(valueTransformer.apply(object));
                }
                else
                {
                    collection.add(object);
                }
                break;
            }
            case COLLECT_AS_MAP:
            {
                if ( valueTransformer != null )
                {
                    map.put(mapKeyFunction.apply(object), valueTransformer.apply(object));
                }
                else
                {
                    map.put(mapKeyFunction.apply(object), object);
                }
                break;
            }
            case GROUP:
            {
                map.putIfAbsent(mapKeyFunction.apply(object), collectionSupplier.get());
                Collection collection = (Collection) map.get(mapKeyFunction.apply(object));
                if ( valueTransformer != null )
                {
                    collection.add(valueTransformer.apply(object));
                }
                else
                {
                    collection.add(object);
                }
            }
            break;
        }
    }

    private <Item, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition createOperationPrecondition(Collection<Item> collection)
    {
        OperationDataHolder operationDataHolderTemplate = new OperationDataHolder(OperationType.COLLECT_AS_LIST, collectScopeCounter++, classType);
        this.collection = collection;
        return createPreconditionForCollectOperation(operationDataHolderTemplate);
    }

    private <Key, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition createOperationPrecondition(Map<Key, ?> map, Function<Node, Key> mapKeySupplier)
    {
        OperationDataHolder operationDataHolderTemplate = new OperationDataHolder(OperationType.COLLECT_AS_MAP, collectScopeCounter++, classType);
        this.map = map;
        this.mapKeyFunction = mapKeySupplier;
        return createPreconditionForCollectOperation(operationDataHolderTemplate);
    }

    private <Key, Item, ListOrSet extends Collection<Item>, Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition createOperationPrecondition(Map<Key, ListOrSet> map, Function<Node, Key> mapKeySupplier, Supplier<ListOrSet> collectionSupplier)
    {
        OperationDataHolder operationDataHolderTemplate = new OperationDataHolder(OperationType.GROUP, collectScopeCounter++, classType);
        this.map = map;
        this.mapKeyFunction = mapKeySupplier;
        this.collectionSupplier = collectionSupplier;
        return createPreconditionForCollectOperation(operationDataHolderTemplate);
    }

    private <Precondition extends OperationPrecondition<Node, CollectOperation<Node, Precondition>, OperationPrecondition<NodeWrapper<Node>, CollectOperation<Node, Precondition>, Precondition>>> Precondition createPreconditionForCollectOperation(OperationDataHolder operationDataHolderTemplate)
    {
        return (Precondition) new OperationPreconditionImpl<
                Node,
                CollectOperation<Node, Precondition>,
                OperationPrecondition<
                        NodeWrapper<Node>,
                        CollectOperation<Node, Precondition>,
                        Precondition
                        >
                >(operationDataHolderTemplate, operationDataHolders, this);
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
                && isTargetNodeInternal(wrapper.getParent(), wrapper, wrapper.getCurrentPath(), operationDataHolder);
    }

    private boolean isTargetNode(Node parent, Node object, OperationDataHolder operationDataHolder)
    {
        return !operationDataHolder.isParentResolutionEnabledForPrecondition()
                && isTargetNodeInternal(parent, object, currentPath, operationDataHolder);
    }

    private boolean isTargetNodeInternal(Object parent, Object object, String currentPath, OperationDataHolder operationDataHolder)
    {
        return operationDataHolder.testPrecondition(parent, object, currentPath)
                && isItemFiltered(parent, object, operationDataHolder)
                && !isItemIgnored(parent, object, operationDataHolder);
    }

    private boolean isTreeNodeWrapperSkipped(NodeWrapper<Node> wrapper, OperationDataHolder operationDataHolder)
    {
        return operationDataHolder.isParentResolutionEnabledForPrecondition()
                && isTreeSkipped(wrapper.getParent(), wrapper, operationDataHolder);
    }

    private boolean isTreeNodeSkipped(Node parent, Node object, OperationDataHolder operationDataHolder)
    {
        return !operationDataHolder.isParentResolutionEnabledForPrecondition()
                && isTreeSkipped(parent, object, operationDataHolder);
    }

    private void executeOperationDataHoldersPreprocessing()
    {
        Map<String, ExecutionMode> executionModeMap = new HashMap<>();
        for ( OperationDataHolder operationDataHolder : operationDataHolders )
        {
            if ( Operation.REPLACE.equals(operationDataHolder.getOperation()) )
            {
                replaceOperationUsed = true;
            }

            if ( OperationType.ITERATE.equals(operationDataHolder.getOperationType()) )
            {
                if ( operationDataHolder.getExecutionMode() != null )
                {
                    this.executionMode = operationDataHolder.getExecutionMode();
                }
            }

            ExecutionMode executionMode = operationDataHolder.getByScope(
                    OperationDataHolder::getExecutionMode,
                    this.executionMode,
                    executionModeMap
            );

            switch ( executionMode )
            {
                case TOP_DOWN:
                    topDownOperationDataHolders.add(operationDataHolder);
                    break;
                case BOTTOM_UP:
                    bottomUpOperationDataHolders.add(operationDataHolder);
                    break;
            }
        }
    }

    private boolean isItemFiltered(Object parent, Object object, OperationDataHolder operationDataHolder)
    {
        return isRegisteredBeforeCurrentOperation(Operation.FILTER, parent, object, operationDataHolder, true);
    }

    private boolean isItemIgnored(Object parent, Object object, OperationDataHolder operationDataHolder)
    {
        return isRegisteredBeforeCurrentOperation(Operation.IGNORE, parent, object, operationDataHolder, false);
    }

    private boolean isTreeSkipped(Object parent, Object object, OperationDataHolder operationDataHolder)
    {
        return isRegisteredBeforeCurrentOperation(Operation.SKIP, parent, object, operationDataHolder, false);
    }

    private boolean isRegisteredBeforeCurrentOperation(Operation operation, Object parent, Object object, OperationDataHolder currentOperationDataHolder, boolean defaultValue)
    {
        boolean operationFound = false;
        for ( int i = operationDataHolders.indexOf(currentOperationDataHolder); i >= 0; i-- )
        {
            OperationDataHolder operationDataHolder = this.operationDataHolders.get(i);
            boolean operationMatches = operationDataHolder.getOperation().equals(operation);
            boolean scopeMatches = operationDataHolder.getScope().equals(currentOperationDataHolder.getScope());
            boolean globalScopeMatches = operationDataHolder.getScope().equals(OperationType.ITERATE.getScopePrefix() + "0");
            scopeMatches = scopeMatches || globalScopeMatches;
            if ( !operationFound && operationMatches && scopeMatches )
            {
                operationFound = true;
            }
            if ( operationMatches && scopeMatches && operationDataHolder.testPrecondition(parent, object, currentPath) )
            {
                return true;
            }
        }
        return !operationFound && defaultValue;
    }

}

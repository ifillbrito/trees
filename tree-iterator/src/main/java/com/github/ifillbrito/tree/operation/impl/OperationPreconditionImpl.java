package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.common.function.TriPredicate;
import com.github.ifillbrito.tree.iterator.TreeIterator;
import com.github.ifillbrito.tree.operation.*;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 09.03.18.
 */
public class OperationPreconditionImpl<Node, Op extends BaseOperation, Precondition extends OperationPrecondition> implements OperationPrecondition<Node, Op, Precondition>
{
    protected final OperationDataHolder operationDataHolderTemplate;
    protected final TreeIterator<Node> treeIterator;
    protected final List<OperationDataHolder> operationDataHolders;

    public OperationPreconditionImpl(OperationDataHolder operationDataHolderTemplate, List<OperationDataHolder> operationDataHolders, TreeIterator<Node> treeIterator)
    {
        this.operationDataHolderTemplate = operationDataHolderTemplate;
        this.treeIterator = treeIterator;
        this.operationDataHolders = operationDataHolders;
    }

    @Override
    public Op forAll()
    {
        OperationDataHolder operation = createOperationDataHolder(OperationPreconditionType.FOR_ALL);
        operation.setNodePredicate(node -> true);
        return (Op) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Op forAll(Predicate<Node> precondition)
    {
        OperationDataHolder operation = createOperationDataHolder(OperationPreconditionType.FOR_ALL);
        operation.setNodePredicate(precondition);
        return (Op) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Op forAll(BiPredicate<Node, String> precondition)
    {
        OperationDataHolder operation = createOperationDataHolder(OperationPreconditionType.FOR_ALL_BI_PREDICATE);
        operation.setNodeAndPathPredicate(precondition);
        return (Op) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Op forAll(TriPredicate<Node, Node, String> precondition)
    {
        OperationDataHolder operation = createOperationDataHolder(OperationPreconditionType.FOR_ALL_TRI_PREDICATE);
        operation.setParentAndNodeAndPathPredicate(precondition);
        return (Op) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Op forPath(String pathRegex)
    {
        OperationDataHolder operation = createOperationDataHolder(OperationPreconditionType.FOR_ALL_PATH_REGEX);
        operation.setPathRegex(pathRegex);
        return (Op) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Op forPath(Predicate<String> precondition)
    {
        OperationDataHolder operation = createOperationDataHolder(OperationPreconditionType.FOR_ALL_PATH_PREDICATE);
        operation.setPathPredicate(precondition);
        return (Op) OperationFactory.createOperation(operation, this);
    }

    @Override
    public OperationPrecondition<Node, Op, Precondition> topDownExecution()
    {
        OperationDataHolder operation = createOperationDataHolder(OperationPreconditionType.FOR_ALL);
        setDefaultOperationForExecutionMode(operation);
        operation.setExecutionMode(ExecutionMode.TOP_DOWN);
        return this;
    }

    @Override
    public OperationPrecondition<Node, Op, Precondition> bottomUpExecution()
    {
        OperationDataHolder operation = createOperationDataHolder(OperationPreconditionType.FOR_ALL);
        setDefaultOperationForExecutionMode(operation);
        operation.setExecutionMode(ExecutionMode.BOTTOM_UP);
        return this;
    }

    @Override
    public Precondition resolveParents()
    {
        operationDataHolderTemplate.enableParentResolution();
        return (Precondition) this;
    }

    @Override
    public TreeIterator<Node> end()
    {
        return treeIterator;
    }


    private OperationDataHolder createOperationDataHolder(OperationPreconditionType forAllBiPredicate)
    {
        OperationDataHolder operation = new OperationDataHolder(operationDataHolderTemplate);
        operationDataHolders.add(operation);
        operation.setPreconditionType(forAllBiPredicate);
        return operation;
    }

    private void setDefaultOperationForExecutionMode(OperationDataHolder operation)
    {
        operation.setNodePredicate(node -> true);
        operation.setOperation(Operation.FILTER);
    }
}

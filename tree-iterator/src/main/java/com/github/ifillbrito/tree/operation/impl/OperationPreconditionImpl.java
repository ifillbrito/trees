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
public class OperationPreconditionImpl<Node, Operation extends BaseOperation, Precondition extends OperationPrecondition> implements OperationPrecondition<Node, Operation, Precondition>
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
    public Operation forAll()
    {
        OperationDataHolder operation = new OperationDataHolder(operationDataHolderTemplate);
        operationDataHolders.add(operation);
        operation.setPreconditionType(OperationPreconditionType.FOR_ALL);
        operation.setNodePredicate(node -> true);
        return (Operation) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Operation forAll(Predicate<Node> precondition)
    {
        OperationDataHolder operation = new OperationDataHolder(operationDataHolderTemplate);
        operationDataHolders.add(operation);
        operation.setPreconditionType(OperationPreconditionType.FOR_ALL);
        operation.setNodePredicate(precondition);
        return (Operation) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Operation forAll(BiPredicate<Node, String> precondition)
    {
        OperationDataHolder operation = new OperationDataHolder(operationDataHolderTemplate);
        operationDataHolders.add(operation);
        operation.setPreconditionType(OperationPreconditionType.FOR_ALL_BI_PREDICATE);
        operation.setNodeAndPathPredicate(precondition);
        return (Operation) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Operation forAll(TriPredicate<Node, Node, String> precondition)
    {
        OperationDataHolder operation = new OperationDataHolder(operationDataHolderTemplate);
        operationDataHolders.add(operation);
        operation.setPreconditionType(OperationPreconditionType.FOR_ALL_TRI_PREDICATE);
        operation.setParentAndNodeAndPathPredicate(precondition);
        return (Operation) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Operation forPath(String pathRegex)
    {
        OperationDataHolder operation = new OperationDataHolder(operationDataHolderTemplate);
        operationDataHolders.add(operation);
        operation.setPreconditionType(OperationPreconditionType.FOR_ALL_PATH_REGEX);
        operation.setPathRegex(pathRegex);
        return (Operation) OperationFactory.createOperation(operation, this);
    }

    @Override
    public Operation forPath(Predicate<String> precondition)
    {
        OperationDataHolder operation = new OperationDataHolder(operationDataHolderTemplate);
        operationDataHolders.add(operation);
        operation.setPreconditionType(OperationPreconditionType.FOR_ALL_PATH_PREDICATE);
        operation.setPathPredicate(precondition);
        return (Operation) OperationFactory.createOperation(operation, this);
    }

    @Override
    public OperationPrecondition<Node, Operation, Precondition> topDownExecution()
    {
        operationDataHolderTemplate.setExecutionMode(ExecutionMode.TOP_DOWN);
        return this;
    }

    @Override
    public OperationPrecondition<Node, Operation, Precondition> bottomUpExecution()
    {
        operationDataHolderTemplate.setExecutionMode(ExecutionMode.BOTTOM_UP);
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
}

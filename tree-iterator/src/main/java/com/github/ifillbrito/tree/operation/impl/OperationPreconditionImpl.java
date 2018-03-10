package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.common.function.TriPredicate;
import com.github.ifillbrito.tree.iterator.TreeIterator;
import com.github.ifillbrito.tree.operation.*;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 09.03.18.
 */
public class OperationPreconditionImpl<Node, Operation extends BaseOperation, Precondition extends OperationPrecondition> implements OperationPrecondition<Node, Operation, Precondition>
{
    protected final OperationArguments arguments;
    protected final TreeIterator<Node> treeIterator;

    public OperationPreconditionImpl(OperationArguments arguments, TreeIterator<Node> treeIterator)
    {
        this.arguments = arguments;
        this.treeIterator = treeIterator;
    }

    @Override
    public Operation forAll()
    {
        arguments.setPreconditionType(OperationPreconditionType.FOR_ALL);
        arguments.setNodePredicate(node -> true);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public Operation forAll(Predicate<Node> precondition)
    {
        arguments.setPreconditionType(OperationPreconditionType.FOR_ALL);
        arguments.setNodePredicate(precondition);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public Operation forAll(BiPredicate<Node, String> precondition)
    {
        arguments.setPreconditionType(OperationPreconditionType.FOR_ALL_BI_PREDICATE);
        arguments.setNodeAndPathPredicate(precondition);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public Operation forAll(TriPredicate<Node, Node, String> precondition)
    {
        arguments.setPreconditionType(OperationPreconditionType.FOR_ALL_TRI_PREDICATE);
        arguments.setParentAndNodeAndPathPredicate(precondition);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public Operation forPath(String pathRegex)
    {
        arguments.setPreconditionType(OperationPreconditionType.FOR_ALL_PATH_REGEX);
        arguments.setPathRegex(pathRegex);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public Operation forPath(Predicate<String> precondition)
    {
        arguments.setPreconditionType(OperationPreconditionType.FOR_ALL_PATH_PREDICATE);
        arguments.setPathPredicate(precondition);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public OperationPrecondition<Node, Operation, Precondition> topDownExecution()
    {
        arguments.setExecutionMode(ExecutionMode.TOP_DOWN);
        treeIterator.setExecution(ExecutionMode.TOP_DOWN);
        return this;
    }

    @Override
    public OperationPrecondition<Node, Operation, Precondition> bottomUpExecution()
    {
        arguments.setExecutionMode(ExecutionMode.BOTTOM_UP);
        treeIterator.setExecution(ExecutionMode.BOTTOM_UP);
        return this;
    }

    @Override
    public Precondition resolveParents()
    {
        arguments.enableParentResolution();
        return (Precondition) this;
    }

    @Override
    public TreeIterator<Node> end()
    {
        return treeIterator;
    }
}

package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.common.function.TriPredicate;
import com.github.ifillbrito.tree.iterator.TreeIterator;
import com.github.ifillbrito.tree.operation.BaseOperation;
import com.github.ifillbrito.tree.operation.OperationArguments;
import com.github.ifillbrito.tree.operation.OperationFactory;
import com.github.ifillbrito.tree.operation.OperationPrecondition;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 09.03.18.
 */
public class OperationPreconditionImpl<Node, Operation extends BaseOperation, Precondition extends OperationPrecondition> implements OperationPrecondition<Node, Operation, Precondition>
{
    protected final OperationArguments<Node> arguments;
    protected final TreeIterator<Node> treeIterator;

    public OperationPreconditionImpl(OperationArguments<Node> arguments, TreeIterator<Node> treeIterator)
    {
        this.arguments = arguments;
        this.treeIterator = treeIterator;
    }

    @Override
    public Operation forAll()
    {
        arguments.setPrecondition(node -> true);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public Operation forAll(Predicate<Node> precondition)
    {
        arguments.setPrecondition(precondition);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public Operation forAll(BiPredicate<Node, String> precondition)
    {
        return null;
    }

    @Override
    public Operation forAll(TriPredicate<Node, Node, String> precondition)
    {
        return null;
    }

    @Override
    public Operation forPath(String pathRegex)
    {
        arguments.setPathRegex(pathRegex);
        return (Operation) OperationFactory.createOperation(arguments, this);
    }

    @Override
    public Operation forPath(Predicate<String> precondition)
    {
        return null;
    }

    @Override
    public OperationPrecondition<Node, Operation, Precondition> topDownExecution()
    {
        return null;
    }

    @Override
    public OperationPrecondition<Node, Operation, Precondition> bottomUpExecution()
    {
        return null;
    }

    @Override
    public Precondition resolveParents()
    {
        return (Precondition) this;
    }

    @Override
    public TreeIterator<Node> end()
    {
        return treeIterator;
    }
}

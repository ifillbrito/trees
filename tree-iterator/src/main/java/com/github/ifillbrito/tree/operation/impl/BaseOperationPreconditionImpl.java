package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.iterator.TreeIterator;
import com.github.ifillbrito.tree.operation.BaseOperation;
import com.github.ifillbrito.tree.operation.BaseOperationPrecondition;
import com.github.ifillbrito.tree.operation.OperationArguments;
import com.github.ifillbrito.tree.operation.OperationFactory;

import java.util.function.Predicate;

/**
 * Created by gjib on 09.03.18.
 */
public class BaseOperationPreconditionImpl<Node, Operation extends BaseOperation, Precondition extends BaseOperationPrecondition> implements BaseOperationPrecondition<Node, Operation, Precondition>
{
    protected final OperationArguments<Node> arguments;
    protected final TreeIterator<Node> treeIterator;

    public BaseOperationPreconditionImpl(OperationArguments<Node> arguments, TreeIterator<Node> treeIterator)
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

package com.ifillbrito.trees.iterator;

import com.ifillbrito.common.operation.Operation;
import com.ifillbrito.common.operation.OperationArguments;

import java.util.function.Predicate;

/**
 * Created by gjib on 05.01.18.
 */
public abstract class AbstractSingleTypeTreeIterator<Node>
        extends AbstractTreeIterator<Node, Node,SingleTypeTreeIterator<Node>>
        implements SingleTypeTreeIterator<Node>
{
    public AbstractSingleTypeTreeIterator(Node node)
    {
        super(node);
    }

    @Override
    public Operation<Node, SingleTypeTreeIterator<Node>> forall()
    {
        return this.forall(x -> true);
    }

    @Override
    public Operation<Node, SingleTypeTreeIterator<Node>> forall(Predicate<Node> precondition)
    {
        operationArguments.add(OperationArguments.create((Class<Node>) root.getClass(), precondition));
        return new TreeOperation<>(this, operationArguments);
    }

    @Override
    public Operation<Node, SingleTypeTreeIterator<Node>> forall(String pathRegex)
    {
        operationArguments.add(OperationArguments.create(root.getClass(), pathRegex));
        return new TreeOperation<>(this, operationArguments);
    }

    @Override
    public Operation<Node, SingleTypeTreeIterator<Node>> forall(Predicate<Node> precondition, String pathRegex)
    {
        operationArguments.add(OperationArguments.create((Class<Node>) root.getClass(), precondition, pathRegex));
        return new TreeOperation<>(this, operationArguments);
    }
}

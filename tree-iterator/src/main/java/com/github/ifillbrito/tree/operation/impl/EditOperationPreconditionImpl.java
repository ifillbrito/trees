package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.common.function.TriPredicate;
import com.github.ifillbrito.tree.iterator.TreeIterator;
import com.github.ifillbrito.tree.node.NodeWrapper;
import com.github.ifillbrito.tree.operation.BaseOperation;
import com.github.ifillbrito.tree.operation.EditOperationPrecondition;
import com.github.ifillbrito.tree.operation.OperationArguments;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 09.03.18.
 */
public class EditOperationPreconditionImpl<Node, Operation extends BaseOperation> extends BaseOperationPreconditionImpl<Node, Operation, EditOperationPrecondition<NodeWrapper<Node>, Operation>> implements EditOperationPrecondition<Node, Operation>
{
    public EditOperationPreconditionImpl(OperationArguments<Node> arguments, TreeIterator<Node> treeIterator)
    {
        super(arguments, treeIterator);
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
        return null;
    }

    @Override
    public Operation forPath(Predicate<String> precondition)
    {
        return null;
    }

    @Override
    public EditOperationPrecondition<Node, Operation> topDownExecution()
    {
        return null;
    }

    @Override
    public EditOperationPrecondition<Node, Operation> bottomUpExecution()
    {
        return null;
    }
}

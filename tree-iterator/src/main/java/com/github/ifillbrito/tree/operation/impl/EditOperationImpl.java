package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.node.NodeWrapper;
import com.github.ifillbrito.tree.operation.EditOperation;
import com.github.ifillbrito.tree.operation.Operation;
import com.github.ifillbrito.tree.operation.OperationDataHolder;
import com.github.ifillbrito.tree.operation.OperationPrecondition;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by gjib on 09.03.18.
 */
public class EditOperationImpl<Node, Precondition extends OperationPrecondition> extends BaseOperationImpl<Precondition, EditOperation<Node, Precondition>> implements EditOperation<Node, Precondition>
{
    public EditOperationImpl(OperationDataHolder arguments, OperationPrecondition precondition)
    {
        super(arguments, precondition);
    }

    @Override
    public Precondition apply(Consumer<Node> consumer)
    {
        arguments.setOperation(Operation.MODIFY);
        arguments.setConsumer(consumer);
        return (Precondition) precondition;
    }

    @Override
    public Precondition replace(Function<Node, ?> function)
    {
        return null;
    }

    @Override
    public Precondition remove()
    {
        return null;
    }

    @Override
    public EditOperation<NodeWrapper<Node>, Precondition> resolveParents()
    {
        arguments.enableParentResolutionForOperation();
        return (EditOperation<NodeWrapper<Node>, Precondition>) this;
    }
}

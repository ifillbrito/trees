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
    public EditOperationImpl(OperationDataHolder operationDataHolder, OperationPrecondition precondition)
    {
        super(operationDataHolder, precondition);
    }

    @Override
    public Precondition apply(Consumer<Node> consumer)
    {
        operationDataHolder.setOperation(Operation.MODIFY);
        operationDataHolder.setConsumer(consumer);
        return (Precondition) precondition;
    }

    @Override
    public Precondition replace(Function<Node, ?> replaceFunction)
    {
        operationDataHolder.setOperation(Operation.REPLACE);
        operationDataHolder.setReplaceFunction(replaceFunction);
        return (Precondition) precondition;
    }

    @Override
    public Precondition remove()
    {
        operationDataHolder.setOperation(Operation.REMOVE);
        return (Precondition) precondition;
    }

    @Override
    public EditOperation<NodeWrapper<Node>, Precondition> resolveParents()
    {
        operationDataHolder.enableParentResolutionForOperation();
        return (EditOperation<NodeWrapper<Node>, Precondition>) this;
    }
}

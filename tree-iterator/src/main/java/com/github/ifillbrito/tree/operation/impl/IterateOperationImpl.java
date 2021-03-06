package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.IterateOperation;
import com.github.ifillbrito.tree.operation.OperationDataHolder;
import com.github.ifillbrito.tree.operation.OperationPrecondition;

/**
 * Created by gjib on 09.03.18.
 */
public class IterateOperationImpl<Node, Precondition extends OperationPrecondition> extends BaseOperationImpl<Precondition, IterateOperation<Node, Precondition>> implements IterateOperation<Node, Precondition>
{
    public IterateOperationImpl(OperationDataHolder operationDataHolder, OperationPrecondition precondition)
    {
        super(operationDataHolder, precondition);
    }
}

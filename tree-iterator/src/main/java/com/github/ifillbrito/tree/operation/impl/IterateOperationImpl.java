package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.BaseOperationPrecondition;
import com.github.ifillbrito.tree.operation.IterateOperation;
import com.github.ifillbrito.tree.operation.OperationArguments;

/**
 * Created by gjib on 09.03.18.
 */
public class IterateOperationImpl<Node, Precondition extends BaseOperationPrecondition> extends BaseOperationImpl<Precondition, IterateOperation<Node, Precondition>> implements IterateOperation<Node, Precondition>
{
    public IterateOperationImpl(OperationArguments arguments, BaseOperationPrecondition precondition)
    {
        super(arguments, precondition);
    }
}

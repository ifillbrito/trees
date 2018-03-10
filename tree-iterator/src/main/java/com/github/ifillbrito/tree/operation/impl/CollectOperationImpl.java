package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.CollectOperation;
import com.github.ifillbrito.tree.operation.OperationArguments;
import com.github.ifillbrito.tree.operation.OperationPrecondition;

/**
 * Created by gjib on 09.03.18.
 */
public class CollectOperationImpl<Node, Precondition extends OperationPrecondition> extends BaseOperationImpl<Precondition, CollectOperation<Node, Precondition>> implements CollectOperation<Node, Precondition>
{
    public CollectOperationImpl(OperationArguments arguments, OperationPrecondition precondition)
    {
        super(arguments, precondition);
    }
}

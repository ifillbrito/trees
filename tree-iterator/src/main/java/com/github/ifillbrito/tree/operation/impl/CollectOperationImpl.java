package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.BaseOperationPrecondition;
import com.github.ifillbrito.tree.operation.CollectOperation;
import com.github.ifillbrito.tree.operation.OperationArguments;

/**
 * Created by gjib on 09.03.18.
 */
public class CollectOperationImpl<Node, Precondition extends BaseOperationPrecondition> extends BaseOperationImpl<Precondition, CollectOperation<Node, Precondition>> implements CollectOperation<Node, Precondition>
{
    public CollectOperationImpl(OperationArguments arguments, BaseOperationPrecondition precondition)
    {
        super(arguments, precondition);
    }
}

package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.BaseOperation;
import com.github.ifillbrito.tree.operation.OperationArguments;
import com.github.ifillbrito.tree.operation.OperationPrecondition;

/**
 * Created by gjib on 09.03.18.
 */
public class BaseOperationImpl<Precondition extends OperationPrecondition, Operation extends BaseOperation> implements BaseOperation<Precondition, Operation>
{
    protected final OperationArguments arguments;
    protected final OperationPrecondition precondition;

    public BaseOperationImpl(OperationArguments arguments, OperationPrecondition precondition)
    {
        this.arguments = arguments;
        this.precondition = precondition;
    }

    @Override
    public Precondition skipOne()
    {
        return null;
    }

    @Override
    public Precondition skipTree()
    {
        return null;
    }

    @Override
    public Precondition filter()
    {
        return null;
    }

    @Override
    public Operation take(int maxCount)
    {
        return null;
    }

    @Override
    public Operation take(int occurrenceFrom, int occurrenceTo)
    {
        return null;
    }

    @Override
    public Operation takeLast()
    {
        return null;
    }

    @Override
    public Operation takeLast(int maxCount)
    {
        return null;
    }

    @Override
    public Operation takeOccurrence(int occurrence)
    {
        return null;
    }
}

package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.BaseOperation;
import com.github.ifillbrito.tree.operation.BaseOperationPrecondition;
import com.github.ifillbrito.tree.operation.OperationArguments;

/**
 * Created by gjib on 09.03.18.
 */
public class BaseOperationImpl<Precondition extends BaseOperationPrecondition, Operation extends BaseOperation> implements BaseOperation<Precondition, Operation>
{
    protected final OperationArguments arguments;
    protected final BaseOperationPrecondition precondition;

    public BaseOperationImpl(OperationArguments arguments, BaseOperationPrecondition precondition)
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

    @Override
    public Operation bottomUpExecution()
    {
        return null;
    }

    @Override
    public Operation topDownExecution()
    {
        return null;
    }
}

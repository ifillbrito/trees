package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.BaseOperation;
import com.github.ifillbrito.tree.operation.Operation;
import com.github.ifillbrito.tree.operation.OperationDataHolder;
import com.github.ifillbrito.tree.operation.OperationPrecondition;

/**
 * Created by gjib on 09.03.18.
 */
public class BaseOperationImpl<Precondition extends OperationPrecondition, OperationType extends BaseOperation> implements BaseOperation<Precondition, OperationType>
{
    protected final OperationDataHolder operationDataHolder;
    protected final OperationPrecondition precondition;

    public BaseOperationImpl(OperationDataHolder operationDataHolder, OperationPrecondition precondition)
    {
        this.operationDataHolder = operationDataHolder;
        this.precondition = precondition;
    }

    @Override
    public Precondition ignore()
    {
        operationDataHolder.setOperation(Operation.IGNORE);
        return (Precondition) precondition;
    }

    @Override
    public Precondition filter()
    {
        operationDataHolder.setOperation(Operation.FILTER);
        return (Precondition) precondition;
    }

    @Override
    public Precondition skip()
    {
        return null;
    }

    @Override
    public OperationType take(int maxCount)
    {
        return null;
    }

    @Override
    public OperationType take(int occurrenceFrom, int occurrenceTo)
    {
        return null;
    }

    @Override
    public OperationType takeLast()
    {
        return null;
    }

    @Override
    public OperationType takeLast(int maxCount)
    {
        return null;
    }

    @Override
    public OperationType takeOccurrence(int occurrence)
    {
        return null;
    }
}

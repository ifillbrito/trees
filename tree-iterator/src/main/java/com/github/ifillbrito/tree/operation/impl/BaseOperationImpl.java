package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.*;

/**
 * Created by gjib on 09.03.18.
 */
public class BaseOperationImpl<Precondition extends OperationPrecondition, Op extends BaseOperation> implements BaseOperation<Precondition, Op>
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
        operationDataHolder.setOperation(Operation.SKIP);
        return (Precondition) precondition;
    }

    @Override
    public Op take(int maxCount)
    {
        operationDataHolder.setOperation(Operation.TAKE);
        return (Op) this;
    }

    @Override
    public Op take(int occurrenceFrom, int occurrenceTo)
    {
        operationDataHolder.setOperation(Operation.TAKE);
        return (Op) this;
    }

    @Override
    public Op takeLast()
    {
        return takeLast(1);
    }

    @Override
    public Op takeLast(int maxCount)
    {
        operationDataHolder.setExecutionMode(ExecutionMode.BOTTOM_UP);
        operationDataHolder.setOperation(Operation.TAKE);
        return (Op) this;
    }

    @Override
    public Op takeOccurrence(int occurrence)
    {
        return take(occurrence, occurrence);
    }
}

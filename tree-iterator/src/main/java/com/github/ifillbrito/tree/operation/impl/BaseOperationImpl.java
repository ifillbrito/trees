package com.github.ifillbrito.tree.operation.impl;

import com.github.ifillbrito.tree.operation.BaseOperation;
import com.github.ifillbrito.tree.operation.Operation;
import com.github.ifillbrito.tree.operation.OperationDataHolder;
import com.github.ifillbrito.tree.operation.OperationPrecondition;

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
        operationDataHolder.initializeTakeCounter();
        operationDataHolder.setTakeMaxCount(maxCount);
        return (Op) this;
    }

    @Override
    public Op take(int occurrenceFrom, int occurrenceTo)
    {
        operationDataHolder.initializeTakeCounter();
        operationDataHolder.setTakeOccurrences(occurrenceFrom, occurrenceTo);
        return (Op) this;
    }

    @Override
    public Op takeOccurrence(int occurrence)
    {
        return take(occurrence, occurrence);
    }
}

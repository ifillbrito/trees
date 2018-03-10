package com.github.ifillbrito.tree.operation;

/**
 * Created by gjib on 17.01.18.
 */
public interface BaseOperation<Precondition extends OperationPrecondition, Operation extends BaseOperation>
{
    // The following methods affect the recursive iteration within the corresponding scope
    Precondition skipOne();

    Precondition skipTree();

    Precondition filter();

    // The following methods only affect the next operation
    Operation take(int maxCount);

    Operation take(int occurrenceFrom, int occurrenceTo);

    Operation takeLast();

    Operation takeLast(int maxCount);

    Operation takeOccurrence(int occurrence);

    Operation bottomUpExecution();

    Operation topDownExecution();
}

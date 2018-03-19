package com.github.ifillbrito.tree.operation;

/**
 * Created by gjib on 17.01.18.
 */
public interface BaseOperation<Precondition extends OperationPrecondition, Operation extends BaseOperation>
{
    // The following methods affect the recursive iteration within the corresponding scope
    Precondition ignore();

    Precondition filter();

    Precondition skip();

    // The following methods only affect the next operation
    Operation take(int maxCount);

    Operation take(int occurrenceFrom, int occurrenceTo);

    Operation takeOccurrence(int occurrence);
}

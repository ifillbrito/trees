package com.ifillbrito.tree.iterator;

/**
 * Created by gjib on 17.01.18.
 */
public interface BaseOperation<Precondition extends BaseOperationPrecondition, Operation extends BaseOperation>
{
    // The following methods affect the recursive iteration within the corresponding scope
    Precondition skip();

    Precondition skip(int maxCount);

    Precondition skip(int occurrenceFrom, int occurrenceTo);

    Precondition skipLast();

    Precondition skipLast(int maxCount);

    Precondition skipOccurrence(int occurrence);

    Precondition ignore();

    Precondition ignore(int maxCount);

    Precondition ignore(int occurrenceFrom, int occurrenceTo);

    Precondition ignoreLast();

    Precondition ignoreLast(int maxCount);

    Precondition ignoreOccurrence(int occurrence);

    Precondition filter();

    Precondition filter(int maxCount);

    Precondition filter(int occurrenceFrom, int occurrenceTo);

    Precondition filterLast();

    Precondition filterLast(int maxCount);

    Precondition filterOccurrence(int occurrence);

    // The following methods only affect the next operation
    Operation take(int maxCount);

    Operation take(int occurrenceFrom, int occurrenceTo);

    Operation takeLast();

    Operation takeLast(int maxCount);

    Operation takeOccurrence(int occurrence);

    Operation bottomUpExecution();

    Operation topDownExecution();
}

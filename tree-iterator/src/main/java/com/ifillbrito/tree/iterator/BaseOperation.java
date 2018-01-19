package com.ifillbrito.tree.iterator;

/**
 * Created by gjib on 17.01.18.
 */
public interface BaseOperation<Cond extends NodeCondition, Operation extends BaseOperation>
{
    // The following methods affect the recursive iteration within the corresponding scope
    Cond skip();

    Cond skip(int maxCount);

    Cond skip(int occurrenceFrom, int occurrenceTo);

    Cond skipLast();

    Cond skipLast(int maxCount);

    Cond skipOccurrence(int occurrence);

    Cond ignore();

    Cond ignore(int maxCount);

    Cond ignore(int occurrenceFrom, int occurrenceTo);

    Cond ignoreLast();

    Cond ignoreLast(int maxCount);

    Cond ignoreOccurrence(int occurrence);

    Cond filter();

    Cond filter(int maxCount);

    Cond filter(int occurrenceFrom, int occurrenceTo);

    Cond filterLast();

    Cond filterLast(int maxCount);

    Cond filterOccurrence(int occurrence);

    // The following methods only affect the next operation
    Operation take(int maxCount);

    Operation take(int occurrenceFrom, int occurrenceTo);

    Operation takeLast();

    Operation takeLast(int maxCount);

    Operation takeOccurrence(int occurrence);
}

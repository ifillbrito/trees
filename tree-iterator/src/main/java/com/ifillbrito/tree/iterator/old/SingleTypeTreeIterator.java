package com.ifillbrito.tree.iterator.old;

import com.ifillbrito.common.operation.Operation;

import java.util.function.Predicate;

/**
 * Created by gjib on 07.01.18.
 */
public interface SingleTypeTreeIterator<Root> extends TreeIterator<Root>
{
    Operation<Root, SingleTypeTreeIterator<Root>> when();

    Operation<Root, SingleTypeTreeIterator<Root>> when(Predicate<Root> precondition);

    Operation<Root, SingleTypeTreeIterator<Root>> when(String pathRegex);

    Operation<Root, SingleTypeTreeIterator<Root>> when(Predicate<Root> precondition, String pathRegex);
}

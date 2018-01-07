package com.ifillbrito.trees.iterator;

import com.ifillbrito.common.operation.Operation;

import java.util.function.Predicate;

/**
 * Created by gjib on 07.01.18.
 */
public interface SingleTypeTreeIterator<Root> extends TreeIterator<Root>
{
    Operation<Root, SingleTypeTreeIterator<Root>> forall();

    Operation<Root, SingleTypeTreeIterator<Root>> forall(Predicate<Root> precondition);

    Operation<Root, SingleTypeTreeIterator<Root>> forall(String pathRegex);

    Operation<Root, SingleTypeTreeIterator<Root>> forall(Predicate<Root> precondition, String pathRegex);
}

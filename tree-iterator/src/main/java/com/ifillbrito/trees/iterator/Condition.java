package com.ifillbrito.trees.iterator;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 18.01.18.
 */
public interface Condition<Root, Operation extends BaseOperation>
{
    Operation forall();

    Operation forall(Predicate<Root> precondition);

    Operation forall(BiPredicate<Root, String> precondition);

    Operation forPath(String pathRegex);

    SingleTypeTreeIterator<Root> end();
}

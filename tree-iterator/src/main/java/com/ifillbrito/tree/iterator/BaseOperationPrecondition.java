package com.ifillbrito.tree.iterator;

import java.util.function.Predicate;

/**
 * Created by gjib on 18.01.18.
 */
public interface BaseOperationPrecondition<Node, Operation extends BaseOperation>
{
    Operation forAll();

    Operation forAll(Predicate<Node> precondition);

    TreeIterator<Node> end();
}

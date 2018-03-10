package com.github.ifillbrito.tree.operation;

import com.github.ifillbrito.common.function.TriPredicate;
import com.github.ifillbrito.tree.iterator.TreeIterator;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 18.01.18.
 */
public interface OperationPrecondition<Node, Operation extends BaseOperation, Precondition extends OperationPrecondition>
{
    Operation forAll();

    Operation forAll(Predicate<Node> precondition);

    Operation forAll(BiPredicate<Node, String> precondition);

    Operation forAll(TriPredicate<Node, Node, String> precondition);

    Operation forPath(String pathRegex);

    Operation forPath(Predicate<String> precondition);

    OperationPrecondition<Node, Operation, Precondition> topDownExecution();

    OperationPrecondition<Node, Operation, Precondition> bottomUpExecution();

    Precondition resolveParents();

    TreeIterator<Node> end();
}

package com.ifillbrito.tree.iterator;

import com.ifillbrito.common.function.TriPredicate;
import com.ifillbrito.tree.node.NodeWrapper;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 18.01.18.
 */
public interface OperationPrecondition<Node, Operation extends BaseOperation>
        extends BaseOperationPrecondition<Node, Operation, OperationPrecondition<NodeWrapper<Node>, Operation>>
{
    Operation forAll(BiPredicate<Node, String> precondition);

    Operation forAll(TriPredicate<Node, Node, String> precondition);

    Operation forPath(String pathRegex);

    Operation forPath(Predicate<String> precondition);

    OperationPrecondition<Node, Operation> topDownExecution();

    OperationPrecondition<Node, Operation> bottomUpExecution();
}

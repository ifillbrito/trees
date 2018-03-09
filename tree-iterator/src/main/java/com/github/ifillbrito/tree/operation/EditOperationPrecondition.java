package com.github.ifillbrito.tree.operation;

import com.github.ifillbrito.common.function.TriPredicate;
import com.github.ifillbrito.tree.node.NodeWrapper;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 18.01.18.
 */
public interface EditOperationPrecondition<Node, Operation extends BaseOperation>
        extends BaseOperationPrecondition<Node, Operation, EditOperationPrecondition<NodeWrapper<Node>, Operation>>
{
    Operation forAll(BiPredicate<Node, String> precondition);

    Operation forAll(TriPredicate<Node, Node, String> precondition);

    Operation forPath(String pathRegex);

    Operation forPath(Predicate<String> precondition);

    EditOperationPrecondition<Node, Operation> topDownExecution();

    EditOperationPrecondition<Node, Operation> bottomUpExecution();
}

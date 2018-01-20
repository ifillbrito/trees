package com.ifillbrito.tree.iterator;

import com.ifillbrito.common.function.TriPredicate;
import com.ifillbrito.tree.node.NodeMeta;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

/**
 * Created by gjib on 18.01.18.
 */
public interface OperationPrecondition<Root, Operation extends BaseOperation> extends BaseOperationPrecondition<Root, Operation>
{
    Operation forAll(BiPredicate<Root, String> precondition);

    Operation forAll(TriPredicate<Root, Root, String> precondition);

    Operation forPath(String pathRegex);

    Operation forPath(Predicate<String> precondition);

    OperationPrecondition<Root, Operation> topDownExecution();

    OperationPrecondition<Root, Operation> bottomUpExecution();

    BaseOperationPrecondition<NodeMeta<Root>, Operation> resolveParents();
}

package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.node.NodeWrapper;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by gjib on 17.01.18.
 */
public interface EditOperation<Node, Precondition extends BaseOperationPrecondition>
        extends BaseOperation<Precondition, EditOperation<Node, Precondition>>
{
    Precondition apply(Consumer<Node> consumer);

    Precondition replace(Function<Node, ?> function);

    Precondition remove();

    EditOperation<NodeWrapper<Node>, Precondition> resolveParents();
}

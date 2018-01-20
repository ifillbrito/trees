package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.node.NodeMeta;

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

    EditOperation<NodeMeta<Node>, Precondition> resolveParents();
}

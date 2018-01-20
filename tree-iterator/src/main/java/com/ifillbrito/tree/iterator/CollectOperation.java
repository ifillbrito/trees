package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.node.NodeMeta;

/**
 * Created by gjib on 17.01.18.
 */
public interface CollectOperation<Node, Precondition extends BaseOperationPrecondition>
        extends BaseOperation<Precondition, CollectOperation<Node, Precondition>>
{
    CollectOperation<NodeMeta<Node>, Precondition> resolveParents();
}
